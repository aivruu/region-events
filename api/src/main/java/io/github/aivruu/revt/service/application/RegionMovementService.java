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

public interface RegionMovementService {
  default boolean isInRegion(final @NotNull Player user, final @NotNull String region) {
    final RegionUserAggregateRoot regionUser = this.findUserRegion(user);
    return (regionUser != null) && regionUser.isInRegion(region);
  }

  @Nullable RegionUserAggregateRoot findUserRegion(final @NotNull Player user);

  void tick(final @NotNull Player user, final @NotNull MovementType movement);

  byte mark(final @NotNull Player user, final @NotNull String region, final @NotNull Location at, final @NotNull MovementType movement);

  byte unmark(final @NotNull Player user, final @NotNull String region, final @NotNull MovementType movement);

  byte unmarkAll(final @NotNull Player user, final @NotNull MovementType movement);
}
