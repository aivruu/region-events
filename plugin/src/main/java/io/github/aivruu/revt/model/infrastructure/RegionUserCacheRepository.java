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
package io.github.aivruu.revt.model.infrastructure;

import io.github.aivruu.revt.model.domain.RegionUserAggregateRoot;
import io.github.aivruu.revt.model.domain.RegionUserRepository;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public final class RegionUserCacheRepository implements RegionUserRepository {
  private final Object2ObjectMap<String, RegionUserAggregateRoot> cache = Object2ObjectMaps.synchronize(new Object2ObjectOpenHashMap<>());

  @Override
  public @Nullable RegionUserAggregateRoot findSync(final @NotNull String id) {
    return this.cache.get(id);
  }

  @Override
  public <C extends Collection<RegionUserAggregateRoot>> @NotNull C findAllSync(final @Nullable Consumer<RegionUserAggregateRoot> postFetchAction) {
    final C values = (C) this.cache.values();
    if ((postFetchAction != null) && !values.isEmpty()) {
      values.forEach(postFetchAction);
    }
    return values;
  }

  @Override
  public <C extends Collection<RegionUserAggregateRoot>> @NotNull C findAllWithSync(final @NotNull Predicate<RegionUserAggregateRoot> filterCondition) {
    final C values = (C) this.cache.values();
    final List<RegionUserAggregateRoot> filteredValues = new ArrayList<>(values.size());
    for (final RegionUserAggregateRoot value : values) {
      if (filterCondition.test(value)) {
        filteredValues.add(value);
      }
    }
    return (C) filteredValues;
  }

  @Override
  public void saveSync(final @NotNull RegionUserAggregateRoot aggregateRoot) {
    this.cache.put(aggregateRoot.id(), aggregateRoot);
  }

  @Override
  public @Nullable RegionUserAggregateRoot deleteSyncAndReturn(final @NotNull String id) {
    return this.cache.remove(id);
  }

  @Override
  public void clearSync() {
    this.cache.clear();
  }
}
