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
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a user is entering a region.
 *
 * @since 2.0.0
 */
public final class RegionEnterEvent extends RegionEvent {
  private static final HandlerList HANDLER_LIST = new HandlerList();

  public RegionEnterEvent(final @NotNull RegionUserAggregateRoot region, final @NotNull String regionName, final @NotNull MovementType movement) {
    super(region, regionName, movement);
  }

  @Override
  public @NotNull HandlerList getHandlers() {
    return HANDLER_LIST;
  }

  public static @NotNull HandlerList getHandlerList() {
    return HANDLER_LIST;
  }
}
