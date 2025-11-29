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
package io.github.aivruu.revt.listener.application;

import io.github.aivruu.revt.model.domain.RegionUserAggregateRoot;
import io.github.aivruu.revt.model.domain.RegionUserRepository;
import io.github.aivruu.revt.model.domain.event.MovementType;
import io.github.aivruu.revt.model.domain.event.RegionLeaveEvent;
import io.github.aivruu.revt.model.domain.event.RegionLeftEvent;
import io.github.aivruu.revt.service.application.RegionMovementService;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

public final class PlayerEventHandler implements Listener {
  private final PluginManager pluginManager = Bukkit.getPluginManager();
  private final RegionUserRepository regionUserRepository;
  private final RegionMovementService regionMovementService;

  public PlayerEventHandler(final @NotNull RegionUserRepository regionUserRepository, final @NotNull RegionMovementService regionMovementService) {
    this.regionUserRepository = regionUserRepository;
    this.regionMovementService = regionMovementService;
  }

  @EventHandler
  public void onLogin(final @NotNull PlayerLoginEvent event) {
    this.regionUserRepository.saveSync(new RegionUserAggregateRoot(event.getPlayer().getUniqueId().toString()));
  }

  @EventHandler
  public void onQuit(final @NotNull PlayerQuitEvent event) {
    this.fireEventsAndClearMarkedRegions(event.getPlayer());
  }

  private void fireEventsAndClearMarkedRegions(final @NotNull Player user) {
    final RegionUserAggregateRoot regionUser = this.regionUserRepository.deleteSyncAndReturn(user.getUniqueId().toString());
    if (regionUser == null) {
      return;
    }
    final ObjectSet<String> markedRegions = regionUser.markedRegions();
    for (final String region : markedRegions) {
      this.pluginManager.callEvent(new RegionLeaveEvent(regionUser, region, MovementType.DISCONNECT));
      this.pluginManager.callEvent(new RegionLeftEvent(regionUser, region, MovementType.DISCONNECT));
    }
    markedRegions.clear();
  }

  @EventHandler
  public void onKick(final @NotNull PlayerKickEvent event) {
    this.fireEventsAndClearMarkedRegions(event.getPlayer());
  }

  @EventHandler
  public void onJoin(final @NotNull PlayerJoinEvent event) {
    this.regionMovementService.tick(event.getPlayer(), MovementType.SPAWN);
  }

  @EventHandler
  public void onRespawn(final @NotNull PlayerRespawnEvent event) {
    this.regionMovementService.tick(event.getPlayer(), MovementType.RESPAWN);
  }

  @EventHandler
  public void onTeleport(final @NotNull PlayerTeleportEvent event) {
    this.regionMovementService.tick(event.getPlayer(), MovementType.TELEPORT);
  }
}
