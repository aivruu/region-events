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
