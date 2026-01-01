package io.github.aivruu.revt.task.application;

import io.github.aivruu.revt.RegionTrackingType;
import io.github.aivruu.revt.config.application.ConfigurationManager;
import io.github.aivruu.revt.config.domain.mapped.MainConfigurationModel;
import io.github.aivruu.revt.model.domain.event.MovementType;
import io.github.aivruu.revt.service.application.RegionMovementService;
import io.github.aivruu.revt.task.domain.RegionTask;
import io.github.aivruu.revt.util.application.PluginExecutor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ScheduledFuture;

public class MovementTrackingTask implements RegionTask {
  private final RegionMovementService regionMovementService;
  private final ConfigurationManager configurationManager;
  private @Nullable ScheduledFuture<?> backupTask;
  private @Nullable World trackedWorld = null;

  public MovementTrackingTask(final @NotNull RegionMovementService regionMovementService, final @NotNull ConfigurationManager configurationManager) {
    this.regionMovementService = regionMovementService;
    this.configurationManager = configurationManager;
  }

  @Override
  public boolean prepare() {
    final MainConfigurationModel config = this.configurationManager.config();
    return (config.tracking != RegionTrackingType.SINGLE_WORLD) || ((this.trackedWorld = Bukkit.getWorld(config.trackedWorld)) != null);
  }

  @Override
  public void start() {
    final MainConfigurationModel config = this.configurationManager.config();
    this.backupTask = PluginExecutor.runAtFixedRate(this, config.taskUpdateRate, config.timeUnitForUpdateRate);
  }

  @Override
  public void run() {
    final MainConfigurationModel config = this.configurationManager.config();
    switch (config.tracking) {
      case SINGLE_WORLD -> this.trackedWorld.getPlayers().forEach(player -> this.regionMovementService.tick(player, MovementType.MOVE));
      case GLOBAL -> Bukkit.getOnlinePlayers().forEach(player -> this.regionMovementService.tick(player, MovementType.MOVE));
      case MULTI_WORLD -> {
        World world;
        for (final String worldName : config.forTrackingWorlds) {
          world = Bukkit.getWorld(worldName);
          if (world == null) continue;

          for (final Player player : world.getPlayers()) {
            this.regionMovementService.tick(player, MovementType.MOVE);
          }
        }
      }
    }
  }

  @Override
  public void stop() {
    if ((this.backupTask == null) || this.backupTask.isCancelled()) return;

    this.backupTask.cancel(false);
  }
}
