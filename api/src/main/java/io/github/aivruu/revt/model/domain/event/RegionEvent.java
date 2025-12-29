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
package io.github.aivruu.revt.model.domain.event;

import io.github.aivruu.revt.model.domain.RegionUserAggregateRoot;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

/**
 * An event that encapsulates the data related (such as player and region involved) to a region event.
 *
 * @since 2.0.0
 */
public abstract class RegionEvent extends Event {
  protected final RegionUserAggregateRoot regionUser;
  protected final String regionName;
  protected final MovementType movement;

  protected RegionEvent(final @NotNull RegionUserAggregateRoot regionUser, final @NotNull String regionName) {
    this(regionUser, regionName, /* default */ MovementType.UNKNOWN);
  }

  protected RegionEvent(final @NotNull RegionUserAggregateRoot regionUser, final @NotNull String regionName, final @NotNull MovementType movement) {
    this.regionUser = regionUser;
    this.regionName = regionName;
    this.movement = movement;
  }

  /**
   * Returns the {@link RegionUserAggregateRoot} involved in this event.
   *
   * @return the event's {@link RegionUserAggregateRoot}
   * @since 2.0.0
   */
  public final @NotNull RegionUserAggregateRoot region() {
    return this.regionUser;
  }

  /**
   * Returns the name of the region involved in this event.
   *
   * @return the region's name.
   * @since 2.0.0
   */
  public final @NotNull String regionName() {
    return this.regionName;
  }

  /**
   * Returns the movement-type associated with this event.
   *
   * @return this event's {@link MovementType}.
   * @since 2.0.0
   */
  public final @NotNull MovementType movement() {
    return this.movement;
  }
}
