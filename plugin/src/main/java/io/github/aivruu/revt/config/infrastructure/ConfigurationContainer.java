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

import io.github.aivruu.revt.util.application.Debugger;
import io.github.aivruu.revt.config.domain.ConfigurationContract;
import io.github.aivruu.revt.util.application.PluginExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public record ConfigurationContainer<C>(@NotNull C model, @NotNull HoconConfigurationLoader loader, @NotNull Class<C> modelClass) {
  public @NotNull CompletableFuture<@Nullable ConfigurationContainer<C>> reload() {
    return CompletableFuture.supplyAsync(() -> {
      try {
        final CommentedConfigurationNode commentedNode = this.loader.load();
        return new ConfigurationContainer<>(commentedNode.get(this.modelClass), this.loader, this.modelClass);
      } catch (final ConfigurateException exception) {
        Debugger.write("Unexpected error when reloading configuration.", exception);
        return null;
      }
    }, PluginExecutor.get());
  }

  public static <C extends ConfigurationContract> @Nullable ConfigurationContainer<C> of(
     final @NotNull Path directory,
     final @NotNull String fileName,
     final @NotNull Class<C> modelClass) {
    final Path path = directory.resolve(fileName + ".conf");
    final HoconConfigurationLoader loader = HoconConfigurationLoader.builder()
      .prettyPrinting(true)
      .defaultOptions(opts -> opts.shouldCopyDefaults(true))
      .path(path)
      .build();
    try {
      final CommentedConfigurationNode node = loader.load();
      final C config = node.get(modelClass);
      if (Files.notExists(path)) {
        node.set(modelClass, config);
        loader.save(node);
      }
      return new ConfigurationContainer<>(config, loader, modelClass);
    } catch (final ConfigurateException exception) {
      Debugger.write("Unexpected error when loading configuration {}.", fileName, exception);
      return null;
    }
  }
}
