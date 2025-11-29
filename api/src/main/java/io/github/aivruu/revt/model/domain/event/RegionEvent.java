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

public abstract class RegionEvent extends Event {
  protected final RegionUserAggregateRoot regionUser;
  protected final String regionName;
  protected MovementType movement = MovementType.UNKNOWN; // default

  protected RegionEvent(final @NotNull RegionUserAggregateRoot regionUser, final @NotNull String regionName) {
    this.regionUser = regionUser;
    this.regionName = regionName;
  }

  protected RegionEvent(final @NotNull RegionUserAggregateRoot regionUser, final @NotNull String regionName, final @NotNull MovementType movement) {
    this.regionUser = regionUser;
    this.regionName = regionName;
    this.movement = movement;
  }

  public final @NotNull RegionUserAggregateRoot region() {
    return this.regionUser;
  }

  public final @NotNull String regionName() {
    return this.regionName;
  }

  public final @NotNull MovementType movement() {
    return this.movement;
  }
}
