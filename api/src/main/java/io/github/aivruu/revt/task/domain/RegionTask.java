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
package io.github.aivruu.revt.task.domain;

/**
 * Represents a task that operates over a single-region or multi-region context.
 *
 * @since 2.0.0
 */
public interface RegionTask extends Runnable {
  /**
   * Setups any parameter or pre-condition required before actually running the task.
   *
   * @return {@code true} if the preparation was successful, {@code false} otherwise.
   * @since 2.0.0
   */
  boolean prepare();

  /**
   * Starts the task execution.
   *
   * @since 2.0.0
   */
  void start();

  /**
   * Stops the task execution, this function will ensure any pending-operation is completed before actually stop the task.
   *
   * @since 2.0.0
   */
  void stop();
}
