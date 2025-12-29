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

/**
 * A contract that defines functions to fetch WorldGuard regions easily.
 *
 * @since 2.0.0
 */
public interface RegionFetchService {
  /**
   * Finds a region with the specified ID at the specified world.
   * <p>
   * This function will first try to get the world's {@link World} reference to work with the {@link #findRegionByIdAt(String, World)}
   * function.
   *
   * @param region the region's id/name.
   * @param world the world's name.
   * @return the found region (a {@link ProtectedRegion} object) or {@code null} if not found, or world doesn't exist.
   * @see #findRegionByIdAt(String, World)
   * @since 2.0.0
   */
  default @Nullable ProtectedRegion findRegionByIdAt(final @NotNull String region, final @NotNull String world) {
    final World bukkitWorld = Bukkit.getWorld(world);
    return (bukkitWorld == null) ? null : this.findRegionByIdAt(region, bukkitWorld);
  }

  /**
   * Finds a region with the specified ID at the given world.
   *
   * @param region the region's id/name.
   * @param world the world to search in.
   * @return the found region (a {@link ProtectedRegion} object) or {@code null} if not found.
   * @since 2.0.0
   */
  @Nullable ProtectedRegion findRegionByIdAt(final @NotNull String region, final @NotNull World world);

  /**
   * Checks whether exists a region with the specified ID at the given world.
   *
   * @param region the region's id/name.
   * @param world the region's world.
   * @return {@code true} if exists, {@code false} otherwise depending on function's result.
   * @see #findRegionByIdAt(String, World)
   * @since 2.0.0
   */
  default boolean existsRegionWithIdAt(final @NotNull String region, final @NotNull World world) {
    return this.findRegionByIdAt(region, world) != null;
  }

  /**
   * Fetches all the existing regions at the specified world.
   *
   * @param world the world's name.
   * @return a {@link Map} containing the found regions, or empty if world doesn't exist or no regions exists.
   * @see #findRegionsAtWorld(String)
   * @since 2.0.0
   */
  default @NotNull Map<String, ProtectedRegion> findRegionsAtWorld(final @NotNull String world) {
    final World bukkitWorld = Bukkit.getWorld(world);
    return (bukkitWorld == null) ? Map.of() : this.findRegionsAtWorld(bukkitWorld);
  }

  /**
   * Fetches all the existing regions at the given world.
   *
   * @param world the world to search in.
   * @return a {@link Map} containing the found regions, or empty if no regions exists.
   * @since 2.0.0
   */
  @NotNull Map<String, ProtectedRegion> findRegionsAtWorld(final @NotNull World world);

  /**
   * Finds all regions at the specified coordinates at the specified world.
   * <p>
   * Result from function also can be dependent on {@link #findRegionsAt(World, int, int, int)} function.
   *
   * @param world the world to search in.
   * @param x the x coordinate.
   * @param y the y coordinate.
   * @param z the z coordinate.
   * @return a {@link Set} with the found regions, empty if the world doesn't or by inner-function's result.
   * @since 2.0.0
   */
  default @NotNull Set<@NotNull ProtectedRegion> findRegionsAt(final @NotNull String world, final int x, final int y, final int z) {
    final World bukkitWorld = Bukkit.getWorld(world);
    return (bukkitWorld == null) ? Set.of() : this.findRegionsAt(bukkitWorld, x, y, z);
  }

  /**
   * Finds all regions at the specified coordinates at the given world.
   *
   * @param world the world to search in.
   * @param x the x coordinate.
   * @param y the y coordinate.
   * @param z the z coordinate.
   * @return a {@link Set} with the found regions, empty if no regions exists.
   * @since 2.0.0
   */
  @NotNull Set<@NotNull ProtectedRegion> findRegionsAt(final @NotNull World world, final int x, final int y, final int z);
}
