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

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import io.github.aivruu.revt.REvtPlugin;
import io.github.aivruu.revt.model.domain.RegionUserAggregateRoot;
import io.github.aivruu.revt.model.domain.RegionUserRepository;
import io.github.aivruu.revt.model.domain.event.MovementType;
import io.github.aivruu.revt.model.domain.event.RegionEnterEvent;
import io.github.aivruu.revt.model.domain.event.RegionEnteredEvent;
import io.github.aivruu.revt.model.domain.event.RegionLeaveEvent;
import io.github.aivruu.revt.model.domain.event.RegionLeftEvent;
import io.github.aivruu.revt.service.application.RegionFetchService;
import io.github.aivruu.revt.service.application.RegionMovementService;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public final class SimpleRegionMovementService implements RegionMovementService {
  private final BukkitScheduler scheduler = Bukkit.getScheduler();
  private final PluginManager pluginManager = Bukkit.getPluginManager();
  private final RegionUserRepository regionUserRepository;
  private final RegionFetchService regionFetchService;
  private final REvtPlugin plugin;

  public SimpleRegionMovementService(
     final @NotNull RegionUserRepository regionUserRepository,
     final @NotNull RegionFetchService regionFetchService,
     final @NotNull REvtPlugin plugin) {
    this.regionUserRepository = regionUserRepository;
    this.regionFetchService = regionFetchService;
    this.plugin = plugin;
  }

  @Override
  public @Nullable RegionUserAggregateRoot findUserRegion(final @NotNull Player user) {
    return this.regionUserRepository.findSync(user.getUniqueId().toString());
  }

  @Override
  public void tick(final @NotNull Player user, final @NotNull MovementType movement) {
    final RegionUserAggregateRoot regionUser = this.regionUserRepository.findSync(user.getUniqueId().toString());
    if (regionUser == null) {
      return;
    }
    final ObjectSet<String> markedRegions = regionUser.markedRegions();
    if (markedRegions.isEmpty()) {
      return;
    }
    final Location at = user.getLocation();
    final Set<ProtectedRegion> regionsAtLocation = this.regionFetchService.findRegionsAt(at.getWorld(), at.getBlockX(), at.getBlockY(), at.getBlockZ());
    final ObjectSet<String> currentRegionIds = new ObjectOpenHashSet<>(regionsAtLocation.size());
    for (final ProtectedRegion r : regionsAtLocation) {
      currentRegionIds.add(r.getId());
    }
    final String[] markedSnapshot = markedRegions.toArray(String[]::new);
    for (final String regionId : markedSnapshot) {
      if (!currentRegionIds.contains(regionId)) {
        this.unapplyMark(regionUser, regionId, movement);
      }
    }
    for (final String regionId : currentRegionIds) {
      if (!regionUser.isInRegion(regionId)) {
        this.applyMark(regionUser, regionId, at, movement);
        break;
      }
    }
  }

  @Override
  public byte mark(final @NotNull Player user, final @NotNull String region, final @NotNull Location at, final @NotNull MovementType movement) {
    final RegionUserAggregateRoot regionUser = this.regionUserRepository.findSync(user.getUniqueId().toString());
    return (regionUser == null) ? RegionMovementService.NO_USER_INFO_STATUS : this.applyMark(regionUser, region, at, movement);
  }

  private byte applyMark(
     final @NotNull RegionUserAggregateRoot regionUser,
     final @NotNull String region,
     final @NotNull Location at,
     final @NotNull MovementType movement) {
    if (this.regionFetchService.existsRegionWithIdAt(region, at.getWorld())) return RegionMovementService.UNKNOWN_REGION_STATUS;

    this.scheduler.runTask(this.plugin, () -> this.pluginManager.callEvent(new RegionEnterEvent(regionUser, region, movement)));
    if (!regionUser.mark(region)) {
      return this.unapplyMark(regionUser, region, movement);
    }
    this.scheduler.runTaskLater(this.plugin, () -> this.pluginManager.callEvent(new RegionEnteredEvent(regionUser, region, movement)), 60L);
    return MARK_OPERATION_SUCCESSFUL;
  }

  @Override
  public byte unmark(final @NotNull Player user, final @NotNull String region, final @NotNull MovementType movement) {
    final RegionUserAggregateRoot regionUser = this.regionUserRepository.findSync(user.getUniqueId().toString());
    return (regionUser == null) ? RegionMovementService.NO_USER_INFO_STATUS : this.unapplyMark(regionUser, region, movement);
  }

  private byte unapplyMark(final @NotNull RegionUserAggregateRoot regionUser, final @NotNull String region, final @NotNull MovementType movement) {
    this.scheduler.runTask(this.plugin, () -> this.pluginManager.callEvent(new RegionLeaveEvent(regionUser, region, movement)));
    if (!regionUser.unmark(region)) {
      return RegionMovementService.UNMARK_OPERATION_FAILED;
    }
    this.scheduler.runTaskLater(this.plugin, () -> this.pluginManager.callEvent(new RegionLeftEvent(regionUser, region, movement)), 60L);
    return RegionMovementService.MARK_OPERATION_SUCCESSFUL;
  }

  @Override
  public boolean unmarkAll(final @NotNull Player user, final @NotNull MovementType movement) {
    final RegionUserAggregateRoot regionUser = this.regionUserRepository.findSync(user.getUniqueId().toString());
    if ((regionUser == null) || !regionUser.hasMarkedRegions()) {
      return false;
    }
    regionUser.markedRegions().clear();
    return true;
  }
}
