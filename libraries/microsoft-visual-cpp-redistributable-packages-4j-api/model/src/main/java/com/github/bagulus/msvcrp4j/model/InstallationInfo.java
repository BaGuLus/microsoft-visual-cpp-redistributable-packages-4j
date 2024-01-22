/*
 * MIT License
 *
 * Copyright (c) 2023 Matúš Černák
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.bagulus.msvcrp4j.model;

import com.github.bagulus.msvcrp4j.resolver.installation.InstallationResolver;
import com.github.bagulus.msvcrp4j.resolver.version.VersionResolver;
import com.vdurmont.semver4j.Semver;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class InstallationInfo {

    private final InstallationParameters installationParameters;
    private final InstallationResolver installationResolver;
    private final VersionResolver versionResolver;

    public InstallationInfo(
        InstallationParameters installationParameters,
        InstallationResolver installationResolver,
        VersionResolver versionResolver
    ) {
        this.installationParameters = installationParameters;
        this.installationResolver = installationResolver;
        this.versionResolver = versionResolver;
    }

    public String[] getInstallationParameters() {
        return installationParameters.getParameters();
    }

    public boolean isInstalled() {
        return installationResolver != null && installationResolver.isInstalled();
    }

    public Optional<Semver> getVersion() {
        if (versionResolver == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(versionResolver.getVersion());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InstallationInfo that = (InstallationInfo) o;
        return Objects.equals(installationParameters, that.installationParameters) && Objects.equals(
            installationResolver, that.installationResolver) && Objects.equals(versionResolver,
            that.versionResolver);
    }

    @Override
    public int hashCode() {
        return 31 * Objects.hash(installationResolver, versionResolver, installationParameters);
    }

    @Override
    public String toString() {
        return "InstallationInfo{" +
            "installationParameters=" + Arrays.toString(installationParameters.getParameters()) +
            ", installationCheckHandler=" + installationResolver +
            ", versionGetter=" + versionResolver +
            '}';
    }

    public enum InstallationParameters {
        Y2005("/Q"),
        Y2008("/q"),
        Y2010("/q", "/norestart"),
        Y2012("/install", "/quiet", "/norestart"),
        Y2013("/install", "/quiet", "/norestart"),
        Y2015PLUS("/install", "/quiet", "/norestart");

        final String[] parameters;

        InstallationParameters(String... parameters) {
            this.parameters = parameters;
        }

        public String[] getParameters() {
            return parameters;
        }
    }
}
