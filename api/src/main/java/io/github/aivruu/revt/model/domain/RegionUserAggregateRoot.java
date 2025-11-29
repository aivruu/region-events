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
import org.jetbrains.annotations.NotNull;

public final class RegionUserAggregateRoot extends AggregateRoot {
  private final RegionUserEntity entity;

  public RegionUserAggregateRoot(final @NotNull String id) {
    this(id, new ObjectOpenHashSet<>());
  }

  public RegionUserAggregateRoot(final @NotNull String id, final @NotNull ObjectSet<String> regions) {
    super(id);
    this.entity = new RegionUserEntity(id, regions);
  }

  public @NotNull ObjectSet<String> markedRegions() {
    return this.entity.markedRegions;
  }

  public boolean isInRegion(final @NotNull String region) {
    return this.entity.isInRegion(region);
  }

  public boolean mark(final @NotNull String newRegion) {
    return this.entity.markedRegions.add(newRegion);
  }

  public boolean unmark(final @NotNull String previousRegion) {
    return this.entity.markedRegions.remove(previousRegion);
  }

  public void unmarkAll() {
    this.entity.markedRegions.clear();
  }
}
