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
package io.github.aivruu.revt.model.domain;

import io.github.aivruu.revt.aggregate.domain.AggregateRoot;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * An aggregate-root that provides a main access-point for user-regions data management.
 *
 * @since 2.0.0
 */
public final class RegionUserAggregateRoot extends AggregateRoot {
  private final RegionUserEntity entity;

  /**
   * Creates a new {@link RegionUserAggregateRoot} with the given ID, the regions-data is set to an empty-set.
   *
   * @param id the user's unique id.
   * @see RegionUserAggregateRoot#RegionUserAggregateRoot(String, ObjectSet)
   * @since 2.0.0
   */
  public RegionUserAggregateRoot(final @NotNull String id) {
    this(id, new ObjectOpenHashSet<>());
  }

  /**
   * Creates a new {@link RegionUserAggregateRoot} with the given ID and pre-existing region-data for the user.
   *
   * @param id the user's unique id.
   * @param regions the user's marked-regions.
   * @since 2.0.0
   */
  public RegionUserAggregateRoot(final @NotNull String id, final @NotNull ObjectSet<String> regions) {
    super(id);
    this.entity = new RegionUserEntity(id, regions);
  }

  /**
   * Wrapper for {@link RegionUserEntity#asPlayer()} method.
   *
   * @return the Bukkit's player reference for this user.
   * @see RegionUserEntity#asPlayer()
   * @since 2.0.0
   */
  public @NotNull Player player() {
    return this.entity.asPlayer();
  }

  /**
   * Returns the set of marked-regions for the user.
   *
   * @return the user's marked-regions.
   * @since 2.0.0
   */
  public @NotNull ObjectSet<String> markedRegions() {
    return this.entity.markedRegions;
  }

  /**
   * Wrapper for {@link RegionUserEntity#isInRegion(String)} method.
   *
   * @param region the region's name.
   * @return {@code true} if the user is in the region, {@code false} otherwise.
   * @see RegionUserEntity#isInRegion(String)
   * @since 2.0.0
   */
  public boolean isInRegion(final @NotNull String region) {
    return this.entity.isInRegion(region);
  }

  /**
   * Checks if the user has at least one marked-region.
   *
   * @return {@code true} if so, {@code false} otherwise.
   * @see ObjectSet#isEmpty()
   * @since 2.0.0
   */
  public boolean hasMarkedRegions() {
    return !this.entity.markedRegions.isEmpty();
  }

  /**
   * Marks a new region for the user, if not already marked.
   *
   * @param newRegion the region's name.
   * @return {@code true} if the region was marked, {@code false} if already was.
   * @since 2.0.0
   */
  public boolean mark(final @NotNull String newRegion) {
    return this.entity.markedRegions.add(newRegion);
  }

  /**
   * Removes a previously marked region for the user.
   *
   * @param previousRegion the region's name.
   * @return {@code true} if the region was unmarked, {@code false} if not found.
   * @since 2.0.0
   */
  public boolean unmark(final @NotNull String previousRegion) {
    return this.entity.markedRegions.remove(previousRegion);
  }

  /**
   * Clears the marked-regions set for the user.
   *
   * @since 2.0.0
   */
  public void unmarkAll() {
    this.entity.markedRegions.clear();
  }
}
