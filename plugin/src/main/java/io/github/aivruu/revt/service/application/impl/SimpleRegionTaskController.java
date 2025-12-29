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
package io.github.aivruu.revt.service.application.impl;

import io.github.aivruu.revt.config.application.ConfigurationManager;
import io.github.aivruu.revt.model.domain.event.MovementType;
import io.github.aivruu.revt.service.application.RegionMovementService;
import io.github.aivruu.revt.service.application.RegionTaskController;
import io.github.aivruu.revt.util.application.PluginExecutor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ScheduledFuture;

public final class SimpleRegionTaskController implements RegionTaskController {
  private final RegionMovementService regionMovementService;
  private final ConfigurationManager configurationManager;
  private @Nullable ScheduledFuture<?> task;

  public SimpleRegionTaskController(final @NotNull RegionMovementService regionMovementService, final @NotNull ConfigurationManager configurationManager) {
    this.regionMovementService = regionMovementService;
    this.configurationManager = configurationManager;
  }

  @Override
  public void start() {
    this.task = PluginExecutor.runAtFixedRate(() -> {
      for (final World world : Bukkit.getWorlds()) {
        for (final Player player : world.getPlayers()) {
          this.regionMovementService.tick(player, MovementType.MOVE);
        }
      }
    }, this.configurationManager.config().taskUpdateRate, this.configurationManager.config().timeUnitForUpdateRate);
  }

  @Override
  public void stop() {
    if ((this.task == null) || !this.task.isCancelled()) return;

    this.task.cancel(false);
  }
}
