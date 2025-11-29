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
package io.github.aivruu.revt.library.application;

import io.github.aivruu.revt.Constants;
import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnstableApiUsage")
public final class PluginLibraryLoader implements PluginLoader {
  @Override
  public void classloader(final @NotNull PluginClasspathBuilder classpathBuilder) {
    final MavenLibraryResolver libraryResolver = new MavenLibraryResolver();
    libraryResolver.addRepository(new RemoteRepository.Builder("central", "default", "https://repo1.maven.org/maven2/").build());

    libraryResolver.addDependency(new Dependency(new DefaultArtifact("org.spongepowered:configurate-yaml:" + Constants.CONFIGURATE_VERSION), null));
    classpathBuilder.addLibrary(libraryResolver);
  }
}
