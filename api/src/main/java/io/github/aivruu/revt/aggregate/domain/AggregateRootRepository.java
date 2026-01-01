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

/**
 * A contract that defines functions to manage aggregate-roots through repositories.
 *
 * @param <A> an object that extends from {@link AggregateRoot}.
 * @since 2.0.0
 */
public interface AggregateRootRepository<A extends AggregateRoot> {
  /**
   * Checks whether the aggregate for the given ID is cached.
   *
   * @param id the id of the aggregate to check.
   * @return {@code true} if the aggregate exists, {@code false} otherwise.
   * @see #findSync(String)
   * @since 2.0.0
   */
  default boolean existsSync(final @NotNull String id) {
    return this.findSync(id) != null;
  }

  /**
   * Searches and return (if found) the aggregate-root that corresponds with the given ID.
   *
   * @param id the aggregate's id.
   * @return the {@link A} object for that aggregate or {@code null} if not found.
   * @since 2.0.0
   */
  @Nullable A findSync(final @NotNull String id);

  /**
   * Returns all the aggregate-roots stored by the repository, no action is performed over the collection's values.
   *
   * @param <C> a collection-type that stores aggregate-roots.
   * @return a collection with all the aggregate-roots.
   * @see #findAllSync(Consumer)
   * @since 2.0.0
   */
  default <C extends Collection<A>> @NotNull C findAllSync() {
    return this.findAllSync(null);
  }

  /**
   * Returns all the aggregate-roots stored by the repository, and performing the given action over each fetched aggregate-root.
   *
   * @param <C> a collection-type that stores aggregate-roots.
   * @param postFetchAction the action to perform with each fetched aggregate-root, {@code null} if no action desired.
   * @return a collection with all the aggregate-roots.
   */
  <C extends Collection<A>> @NotNull C findAllSync(final @Nullable Consumer<A> postFetchAction);

  /**
   * Returns all the aggregate-roots that match with the given filter.
   *
   * @param <C> a collection-type that stores aggregate-roots.
   * @param filterCondition the filter to apply over each aggregate-root.
   * @return a collection with the aggregates that matched the filter.
   * @since 2.0.0
   */
  <C extends Collection<A>> @NotNull C findAllWithSync(final @NotNull Predicate<A> filterCondition);

  /**
   * Saves the given aggregate-root within this repository.
   *
   * @param aggregateRoot the aggregate-root to persist.
   * @since 2.0.0
   */
  void saveSync(final @NotNull A aggregateRoot);

  /**
   * Deletes the aggregate-root that corresponds with the given ID and returns a result.
   *
   * @param id the aggregate's id.
   * @return {@code true} if the aggregate was deleted, {@code false} otherwise.
   * @see #deleteSyncAndReturn(String)
   * @since 2.0.0
   */
  default boolean deleteSync(final @NotNull String id) {
    return this.deleteSyncAndReturn(id) != null;
  }

  /**
   * Deletes the aggregate-root that corresponds with the given ID and returns it (if found).
   *
   * @param id the aggregate's id.
   * @return the aggregate's {@link A} object or {@code null} if no aggregate-root with that ID was found.
   * @since 2.0.0
   */
  @Nullable A deleteSyncAndReturn(final @NotNull String id);

  /**
   * Removes all the aggregate-roots persisted by this repository.
   *
   * @since 2.0.0
   */
  void clearSync();
}
