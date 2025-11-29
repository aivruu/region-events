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

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import io.github.aivruu.revt.service.application.RegionFetchService;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;

public final class SimpleRegionFetchService implements RegionFetchService {
  private final RegionContainer regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();

  @Override
  public @Nullable ProtectedRegion findRegionByIdAt(final @NotNull String region, final @NotNull World world) {
    final RegionManager regionManager = this.regionContainer.get(BukkitAdapter.adapt(world));
    return (regionManager == null) ? null : regionManager.getRegion(region);
  }

  @Override
  public @NotNull Map<String, ProtectedRegion> findRegionsAtWorld(final @NotNull World world) {
    final RegionManager regionManager = this.regionContainer.get(BukkitAdapter.adapt(world));
    return (regionManager == null) ? Map.of() : regionManager.getRegions();
  }

  @Override
  public @NotNull Set<@NotNull ProtectedRegion> findRegionsAt(final @NotNull World world, final int x, final int y, final int z) {
    final RegionManager regionManager = this.regionContainer.get(BukkitAdapter.adapt(world));
    return (regionManager == null) ? Set.of() : regionManager.getApplicableRegions(new BlockVector3(x, y, z)).getRegions();
  }
}
