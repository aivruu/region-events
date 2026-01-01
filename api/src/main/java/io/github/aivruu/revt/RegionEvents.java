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

import io.github.aivruu.revt.model.domain.RegionUserRepository;
import io.github.aivruu.revt.service.application.RegionFetchService;
import io.github.aivruu.revt.service.application.RegionMovementService;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the main access-point for the plugin's API.
 *
 * @since 2.0.0
 */
public interface RegionEvents {
  /**
   * Returns the type of tracking the plugin is using for region-movement related events.
   *
   * @throws IllegalStateException if the property has not been initialized yet.
   * @return the current {@link RegionTrackingType}.
   * @since 2.0.0
   */
  @NotNull RegionTrackingType trackingType();

  /**
   * Returns the repository used for the users' region-data persistence.
   *
   * @throws IllegalStateException if the repository has not been initialized yet.
   * @return the {@link RegionUserRepository} instance.
   * @since 2.0.0
   */
  @NotNull RegionUserRepository regionUserRepository();

  /**
   * Returns the service used for fetch WorldGuard regions for plugin's handling.
   *
   * @throws IllegalStateException if the service has not been initialized yet.
   * @return the {@link RegionFetchService} instance.
   * @since 2.0.0
   */
  @NotNull RegionFetchService regionFetchService();

  /**
   * Returns the service used to handle region-movements related operations.
   *
   * @throws IllegalStateException if the service has not been initialized yet.
   * @return the {@link RegionMovementService} instance.
   * @since 2.0.0
   */
  @NotNull RegionMovementService regionMovementService();
}
