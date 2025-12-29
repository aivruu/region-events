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

import io.github.aivruu.revt.model.domain.RegionUserAggregateRoot;
import io.github.aivruu.revt.model.domain.event.MovementType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A contract that defines functions to manage region-movements tracking for users.
 *
 * @since 2.0.0
 */
public interface RegionMovementService {
  byte NO_USER_INFO_STATUS = 0;
  /** No region with such name exists. */
  byte UNKNOWN_REGION_STATUS = 1;
  /** Failed to complete execution for {@link #unmark(Player, String, MovementType)} function. */
  byte UNMARK_OPERATION_FAILED = 2;
  /** The region was marked or unmarked correctly. */
  byte MARK_OPERATION_SUCCESSFUL = 3;

  /**
   * Checks whether the given user is at the specified region.
   *
   * @param user the user to check.
   * @param region the region's name.
   * @return {@code true} if the user's region-data exists, and he's in the region, {@code false} otherwise.
   * @see #findUserRegion(Player)
   * @since 2.0.0
   */
  default boolean isInRegion(final @NotNull Player user, final @NotNull String region) {
    final RegionUserAggregateRoot regionUser = this.findUserRegion(user);
    return (regionUser != null) && regionUser.isInRegion(region);
  }

  /**
   * Finds and return (if found) the user's region-data aggregate-root.
   *
   * @param user the user to check for.
   * @return a {@link RegionUserAggregateRoot} or {@code null} if no region-data exists for the user.
   * @since 2.0.0
   */
  @Nullable RegionUserAggregateRoot findUserRegion(final @NotNull Player user);

  /**
   * Handles region-movement for user with the given movement-type on current tick.
   *
   * @param user the user to handle.
   * @param movement the movement-type the user is performing or performed.
   * @since 2.0.0
   */
  void tick(final @NotNull Player user, final @NotNull MovementType movement);

  /**
   * Marks the user as standing at the specified region for this function.
   * <p>
   * If this region is already included in the user's marked-regions, the user will be un-marked from that region.
   *
   * @param user the user to mark.
   * @param region the region's name.
   * @param at the location where the user is at.
   * @param movement the movement-type the user performed at the region.
   * @return a status-code that indicates the result this function could've achieved.
   * <ul>
   * <li>{@link #NO_USER_INFO_STATUS} No information related with the user's marked-regions was found.</li>
   * <li>{@link #UNKNOWN_REGION_STATUS} The specified region does not exist.</li>
   * <li>{@link #MARK_OPERATION_SUCCESSFUL} The region was added to the user's marked-regions.</li>
   * </ul>
   * @see io.github.aivruu.revt.model.domain.RegionUserRepository#findSync(String)
   * @since 2.0.0
   */
  byte mark(final @NotNull Player user, final @NotNull String region, final @NotNull Location at, final @NotNull MovementType movement);

  /**
   * Un-marks the user from being at the specified region for this function.
   *
   * @param user the user to unmark.
   * @param region the region's name.
   * @param movement the movement-type the user performed.
   * @return a status-code that indicates the result this function could've achieved.
   * <ul>
   * <li>{@link #NO_USER_INFO_STATUS} No information related to the user's marked-regions was found.</li>
   * <li>{@link #UNMARK_OPERATION_FAILED} The region is not in the user's marked-regions.</li>
   * <li>{@link #MARK_OPERATION_SUCCESSFUL} The region was removed from the user's marked-regions.</li>
   * </ul>
   * @see io.github.aivruu.revt.model.domain.RegionUserRepository#findSync(String)
   * @since 2.0.0
   */
  byte unmark(final @NotNull Player user, final @NotNull String region, final @NotNull MovementType movement);

  /**
   * Un-marks all the regions the user is currently marked at.
   *
   * @param user the user to unmark.
   * @param movement the "reason" for this action.
   * @return {@code true} if the regions were cleared, {@code false} if no regions were marked for the user or if user's regions-information
   * doesn't exist.
   * @see io.github.aivruu.revt.model.domain.RegionUserRepository#findSync(String)
   * @see RegionUserAggregateRoot#hasMarkedRegions()
   * @since 2.0.0
   */
  boolean unmarkAll(final @NotNull Player user, final @NotNull MovementType movement);
}
