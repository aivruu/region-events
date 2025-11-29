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
package io.github.aivruu.revt.aggregate.domain;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface AggregateRootRepository<A extends AggregateRoot> {
  default boolean existsSync(final @NotNull String id) {
    return this.findByIdSync(id) != null;
  }

  @Nullable A findByIdSync(final @NotNull String id);

  default <C extends Collection<A>> @NotNull C findAllSync() {
    return this.findAllSync(null);
  }

  <C extends Collection<A>> @NotNull C findAllSync(final @Nullable Consumer<A> postFetchAction);

  <C extends Collection<A>> @NotNull C findAllWithSync(final @NotNull Predicate<A> filterCondition);

  void saveSync(final @NotNull A aggregateRoot);

  default boolean deleteSync(final @NotNull String id) {
    return this.deleteSyncAndReturn(id) != null;
  }

  @Nullable A deleteSyncAndReturn(final @NotNull String id);

  void clearSync();
}
