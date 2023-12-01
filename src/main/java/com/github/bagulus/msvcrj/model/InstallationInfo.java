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

package com.github.bagulus.msvcrj.model;

import com.github.bagulus.msvcrj.install.InstallationCheckHandler;
import com.github.bagulus.msvcrj.version.VersionGetter;
import java.util.Arrays;
import java.util.Objects;

public record InstallationInfo(
    String[] installationParameters,
    InstallationCheckHandler installationCheckHandler,
    VersionGetter versionGetter
) {

    public static final String[] INSTALLATION_PARAMETERS_2005 = new String[]{"/Q"};
    public static final String[] INSTALLATION_PARAMETERS_2008 = new String[]{"/q"};
    public static final String[] INSTALLATION_PARAMETERS_2010 = new String[]{"/q", "/norestart"};
    public static final String[] INSTALLATION_PARAMETERS_2012 = new String[]{"/install", "/quiet", "/norestart"};
    public static final String[] INSTALLATION_PARAMETERS_2013 = INSTALLATION_PARAMETERS_2012;
    public static final String[] INSTALLATION_PARAMETERS_2015PLUS = INSTALLATION_PARAMETERS_2012;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InstallationInfo that = (InstallationInfo) o;
        return Arrays.equals(installationParameters, that.installationParameters) && Objects.equals(
            installationCheckHandler, that.installationCheckHandler) && Objects.equals(versionGetter,
            that.versionGetter);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(installationCheckHandler, versionGetter);
        result = 31 * result + Arrays.hashCode(installationParameters);
        return result;
    }

    @Override
    public String toString() {
        return "InstallationInfo{" +
            "installationParameters=" + Arrays.toString(installationParameters) +
            ", installationCheckHandler=" + installationCheckHandler +
            ", versionGetter=" + versionGetter +
            '}';
    }
}
