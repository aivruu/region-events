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
package io.github.aivruu.revt.config.domain.mapped;

import io.github.aivruu.revt.config.domain.ConfigurationContract;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

import java.util.concurrent.TimeUnit;

@ConfigSerializable
public final class MainConfigurationModel implements ConfigurationContract {
  @Comment("The number of threads to assign to the plugin's thread-pool.")
  public byte threadPoolSize = 2;

  @Comment("The name to set for the plugin's thread-pool.")
  public String threadPoolName = "pool-2-thread";

  @Comment("""
     Whether enable or not the debug-mode.
     
     This option enables a more-verbose logging for the plugin, useful for debugging purposes and troubleshooting.""")
  public boolean debug = true;

  @Comment("""
    The update-rate to set for the async-task.

    The plugin uses an async-task to check and monitor movements for any region event-firing instead using a listener
    for player's movements, this for a plugin's performance and avoid possible server lag-spikes if there are too many
    players.""")
  public byte taskUpdateRate = 1;

  @Comment("The time-unit to used for the update-rate.")
  public TimeUnit timeUnitForUpdateRate = TimeUnit.SECONDS;

  public String reloadSuccess = "<green>[RegionEvents]</green> The plugin was reloaded successfully!";

  public String reloadFailed = "<green>[RegionEvents]</green> <yellow>The plugin's configuration couldn't be reloaded.";
}
