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
package io.github.aivruu.revt;

import org.jetbrains.annotations.Nullable;

/**
 * This class is used for {@link RegionEvents} API instances providing.
 *
 * @since 1.0.0
 */
public final class RegionEventsProvider {
  private static @Nullable RegionEvents instance;

  private RegionEventsProvider() {
    throw new UnsupportedOperationException("This class is for utility and cannot be instantiated.");
  }

  /**
   * Returns the current {@link RegionEvents} instance.
   *
   * @throws IllegalStateException if the API isn't initialized yet.
   * @return The {@link RegionEvents} instance.
   * @since 1.0.0
   */
  public static RegionEvents get() {
    if (instance == null) {
      throw new IllegalStateException("RegionEvents' API has not been initialized yet.");
    }
    return instance;
  }

  /**
   * Sets the given {@link RegionEvents} instance to the instance-field.
   *
   * @throws IllegalStateException if the API is already initialized.
   * @param impl a {@link RegionEvents} instance.
   * @since 1.0.0
   */
  public static void set(final RegionEvents impl) {
    if (instance != null) {
      throw new IllegalStateException("RegionEvents' API is already initialized.");
    }
    instance = impl;
  }
}
