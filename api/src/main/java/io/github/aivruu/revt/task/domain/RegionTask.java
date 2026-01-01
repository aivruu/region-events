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
