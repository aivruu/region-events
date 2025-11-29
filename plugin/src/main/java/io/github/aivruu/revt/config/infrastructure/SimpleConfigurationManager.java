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
package io.github.aivruu.revt.config.infrastructure;

import io.github.aivruu.revt.config.application.ConfigurationManager;
import io.github.aivruu.revt.config.domain.mapped.MainConfigurationModel;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;

public final class SimpleConfigurationManager implements ConfigurationManager {
  private final ComponentLogger logger;
  private final Path directory;
  private ConfigurationContainer<MainConfigurationModel> container;

  public SimpleConfigurationManager(final @NotNull ComponentLogger logger, final @NotNull Path directory) {
    this.logger = logger;
    this.directory = directory;
  }

  @Override
  public boolean load() {
    return (this.container = ConfigurationContainer.of(this.directory, "config", MainConfigurationModel.class)) != null;
  }

  @Override
  public boolean reload() {
    if (this.container == null) return false;

    final AtomicBoolean reloaded = new AtomicBoolean();
    this.container.reload().whenComplete((none, exception) -> {
      if (exception == null) {
        reloaded.set(true);
        return;
      }
      this.logger.error("Unexpected error when handling async-computation for config-reload.", exception);
    });
    return reloaded.get();
  }

  @Override
  public @NotNull MainConfigurationModel config() {
    return this.container.model();
  }
}
