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

final class RegionUserEntity {
  final String id;
  final ObjectSet<String> markedRegions;

  RegionUserEntity(final @NotNull String id, final @NotNull ObjectSet<String> markedRegions) {
    this.id = id;
    this.markedRegions = ObjectSets.synchronize(markedRegions);
  }

  boolean isInRegion(final @NotNull String targetRegion) {
    return this.markedRegions.contains(targetRegion);
  }

  @SuppressWarnings("ConstantConditions")
  @NotNull Player asPlayer() {
    return Bukkit.getPlayer(UUID.fromString(this.id));
  }
}
