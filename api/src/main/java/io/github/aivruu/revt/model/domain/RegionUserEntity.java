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

import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Represents an entity to manage regions-data for a specific user.
 *
 * @since 2.0.0
 */
final class RegionUserEntity {
  final String id;
  final ObjectSet<String> markedRegions;

  RegionUserEntity(final @NotNull String id, final @NotNull ObjectSet<String> markedRegions) {
    this.id = id;
    this.markedRegions = ObjectSets.synchronize(markedRegions);
  }

  /**
   * Checks whether this user is inside the specified region.
   *
   * @param targetRegion the region-name for checking.
   * @return {@code true} if the user is in the region.
   * @see ObjectSet#contains(Object)
   * @since 2.0.0
   */
  boolean isInRegion(final @NotNull String targetRegion) {
    return this.markedRegions.contains(targetRegion);
  }

  /**
   * Returns Bukkit's player reference using this user's id.
   *
   * @return this user's {@link Player} instance.
   * @see Bukkit#getPlayer(UUID)
   * @see UUID#fromString(String)
   * @since 2.0.0
   */
  @SuppressWarnings("ConstantConditions") // The result shouldn't null when this function is invoked.
  @NotNull Player asPlayer() {
    return Bukkit.getPlayer(UUID.fromString(this.id));
  }
}
