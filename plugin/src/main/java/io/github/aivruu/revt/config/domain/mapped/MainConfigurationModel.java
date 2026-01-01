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

import io.github.aivruu.revt.RegionTrackingType;
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
     The tracking-type to be performed to handle region-movement events.
     SINGLE_WORLD: The plugin will just track the world specified by the 'tracked-world' option.
     MULTI_WORLD: The plugin will track all the worlds specified in the 'for-tracking-worlds' option.
     GLOBAL: The plugin will track the movement for all the players connected to the server, regardless of the world they're in.""")
  public RegionTrackingType tracking = RegionTrackingType.SINGLE_WORLD;

  @Comment("The world to be tracked for region-movement events.")
  public String trackedWorld = "world";

  @Comment("Worlds that won't be tracked for region-movement events, this option is used only if 'track-single-world' is turned off.")
  public String[] forTrackingWorlds = {
     "world",
     "world_nether",
  };

  @Comment("""
    The update-rate to set for the async-task.

    Normally you'd want to keep this value as low as possible so the tracking for region-events is faster, but consequently it may
    affect the server's performance if there're many players distributed across multiple-worlds.
    
    If you want to keep a good tracking time without affecting the server's performance too much, consider set this value to 2 or 3,
    you can play with the values until you find a good balance. If you're using the 'track-single-world' option this advice can be ignored.""")
  public byte taskUpdateRate = 1;

  @Comment("The time-unit to used for the update-rate.")
  public TimeUnit timeUnitForUpdateRate = TimeUnit.SECONDS;

  public String reloadSuccess = "<green>[RegionEvents]</green> The plugin was reloaded successfully!";

  public String reloadFailed = "<green>[RegionEvents]</green> <yellow>The plugin's configuration couldn't be reloaded.";
}
