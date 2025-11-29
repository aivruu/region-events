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

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jetbrains.annotations.NotNull;

public final class Debugger {
  private static final ComponentLogger LOGGER = ComponentLogger.logger("REvt-Debug");
  private static boolean enabled;

  private Debugger() {
    throw new UnsupportedOperationException("This class is for utility.");
  }

  public static boolean isEnabled() {
    return enabled;
  }

  public static void enable(final boolean enable) {
    if (enabled) return;

    enabled = enable;
  }

  public static void write(final @NotNull String message, final @NotNull Object @NotNull ... args) {
    if (enabled) LOGGER.info(message, args);
  }
}
