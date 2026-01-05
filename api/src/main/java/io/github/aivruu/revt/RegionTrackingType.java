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

/**
 * Represents the tracking-type the plugin can use to monitor movement between regions.
 *
 * @since 2.0.0
 */
public enum RegionTrackingType {
  /** All the server's players are tracked for region-movements. */
  GLOBAL,
  /** A single-world (specified in config) is monitored to track region-movements by the players. */
  SINGLE_WORLD,
  /** Multiple worlds are monitored for region-movements tracking. */
  MULTI_WORLD
}
