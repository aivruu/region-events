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
package io.github.aivruu.revt.util.application;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class PluginExecutor {
  public static final IllegalStateException NON_INITIALIZED_CACHED_EXCEPTION = new IllegalStateException("The field has not been initialized yet.");
  private static @Nullable ScheduledExecutorService executor;

  public static @NotNull ScheduledExecutorService get() {
    if (executor == null) throw NON_INITIALIZED_CACHED_EXCEPTION;

    return executor;
  }

  public static void buildExecutor(final int threads, final @NotNull String threadName) {
    if (executor != null) throw new IllegalStateException("The thread-pool is already initialized.");

    executor = Executors.newScheduledThreadPool(threads, task -> new Thread(task, threadName));
  }

  public static void runNow(final @NotNull Runnable task) {
    if (executor == null) throw NON_INITIALIZED_CACHED_EXCEPTION;

    executor.submit(task);
  }

  public static @NotNull Future<?> runAfter(final @NotNull Runnable task, final long delay, final @NotNull TimeUnit timeUnit) {
    if (executor == null) throw NON_INITIALIZED_CACHED_EXCEPTION;

    return executor.schedule(task, delay, timeUnit);
  }

  public static @NotNull ScheduledFuture<?> runAtFixedRate(final @NotNull Runnable task, final long period, final @NotNull TimeUnit timeUnit) {
    if (executor == null) throw NON_INITIALIZED_CACHED_EXCEPTION;

    return executor.scheduleAtFixedRate(task, 1L, period, timeUnit);
  }
}
