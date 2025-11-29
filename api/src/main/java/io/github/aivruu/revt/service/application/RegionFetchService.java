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
package io.github.aivruu.revt.service.application;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;

public interface RegionFetchService {
  default @Nullable ProtectedRegion findRegionByIdAt(final @NotNull String region, final @NotNull String world) {
    final World bukkitWorld = Bukkit.getWorld(world);
    return (bukkitWorld == null) ? null : this.findRegionByIdAt(region, bukkitWorld);
  }

  @Nullable ProtectedRegion findRegionByIdAt(final @NotNull String region, final @NotNull World world);

  default boolean existsRegionWithIdAt(final @NotNull String region, final @NotNull World world) {
    return this.findRegionByIdAt(region, world) != null;
  }

  default @NotNull Map<String, ProtectedRegion> findRegionsAtWorld(final @NotNull String world) {
    final World bukkitWorld = Bukkit.getWorld(world);
    return (bukkitWorld == null) ? Map.of() : this.findRegionsAtWorld(bukkitWorld);
  }

  @NotNull Map<String, ProtectedRegion> findRegionsAtWorld(final @NotNull World world);

  default @NotNull Set<@NotNull ProtectedRegion> findRegionsAt(final @NotNull String world, final int x, final int y, final int z) {
    final World bukkitWorld = Bukkit.getWorld(world);
    return (bukkitWorld == null) ? Set.of() : this.findRegionsAt(bukkitWorld, x, y, z);
  }

  @NotNull Set<@NotNull ProtectedRegion> findRegionsAt(final @NotNull World world, final int x, final int y, final int z);
}
