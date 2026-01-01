// This file is part of region-events, licensed under the GNU License.
//
// Copyright (c) 2024-2025 Alejandro
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see <https://www.gnu.org/licenses/>.
package io.github.aivruu.revt;

import io.github.aivruu.revt.config.application.ConfigurationManager;
import io.github.aivruu.revt.config.domain.mapped.MainConfigurationModel;
import io.github.aivruu.revt.config.infrastructure.SimpleConfigurationManager;
import io.github.aivruu.revt.listener.application.PlayerEventHandler;
import io.github.aivruu.revt.model.domain.RegionUserRepository;
import io.github.aivruu.revt.model.infrastructure.RegionUserCacheRepository;
import io.github.aivruu.revt.service.application.RegionFetchService;
import io.github.aivruu.revt.service.application.RegionMovementService;
import io.github.aivruu.revt.service.application.impl.SimpleRegionFetchService;
import io.github.aivruu.revt.service.application.impl.SimpleRegionMovementService;
import io.github.aivruu.revt.task.application.MovementTrackingTask;
import io.github.aivruu.revt.task.domain.RegionTask;
import io.github.aivruu.revt.util.application.PluginExecutor;
import io.github.aivruu.revt.util.application.Debugger;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class REvtPlugin extends JavaPlugin implements RegionEvents {
  private final ComponentLogger logger = super.getComponentLogger();
  private final ConfigurationManager configurationManager = new SimpleConfigurationManager(this.logger, super.getDataPath());
  private RegionTrackingType trackingType;
  private RegionUserRepository regionUserRepository;
  private RegionFetchService regionFetchService;
  private RegionMovementService regionMovementService;
  private RegionTask movementTrackingTask;

  @Override
  public @NotNull RegionTrackingType trackingType() {
    if (this.trackingType == null) throw PluginExecutor.NON_INITIALIZED_CACHED_EXCEPTION;

    return this.trackingType;
  }

  @Override
  public @NotNull RegionUserRepository regionUserRepository() {
    if (this.regionUserRepository == null) throw PluginExecutor.NON_INITIALIZED_CACHED_EXCEPTION;

    return this.regionUserRepository;
  }

  @Override
  public @NotNull RegionFetchService regionFetchService() {
    if (this.regionFetchService == null) throw PluginExecutor.NON_INITIALIZED_CACHED_EXCEPTION;

    return this.regionFetchService;
  }

  @Override
  public @NotNull RegionMovementService regionMovementService() {
    if (this.regionMovementService == null) throw PluginExecutor.NON_INITIALIZED_CACHED_EXCEPTION;

    return this.regionMovementService;
  }

  @Override
  public void onLoad() {
    if (!this.configurationManager.load()) throw new IllegalStateException("Failed to load the configuration-file, check by possible invalid syntax-errors.");

    final MainConfigurationModel config = this.configurationManager.config();
    Debugger.enable(config.debug);
    PluginExecutor.buildExecutor(config.threadPoolSize, config.threadPoolName);

    this.regionUserRepository = new RegionUserCacheRepository();
    this.regionFetchService = new SimpleRegionFetchService();
    this.regionMovementService = new SimpleRegionMovementService(this.regionUserRepository, this.regionFetchService, this);

    this.movementTrackingTask = new MovementTrackingTask(this.regionMovementService, this.configurationManager);
    if (!this.movementTrackingTask.prepare()) {
      throw new IllegalStateException("Failed to setup parameters for movement-tracking task, seems that specified world-to-track doesn't exist.");
    }
    this.trackingType = config.tracking;
  }

  @Override
  public void onEnable() {
    super.getServer().getPluginManager().registerEvents(new PlayerEventHandler(this.regionUserRepository, this.regionMovementService), this);

    this.movementTrackingTask.start();
    RegionEventsProvider.set(this);
  }

  public boolean reload() {
    if (!this.configurationManager.reload()) return false;

    final MainConfigurationModel config = this.configurationManager.config();
    this.trackingType = config.tracking;
    Debugger.enable(config.debug);

    this.movementTrackingTask.stop();
    if (!this.movementTrackingTask.prepare()) {
      Debugger.write("Failed to setup parameters for movement-tracking task, seems that specified world-to-track doesn't exist.");
      return false;
    }
    this.movementTrackingTask.start();
    return true;
  }

  @Override
  public void onDisable() {
    this.movementTrackingTask.stop();
    this.regionUserRepository.clearSync();
  }
}
