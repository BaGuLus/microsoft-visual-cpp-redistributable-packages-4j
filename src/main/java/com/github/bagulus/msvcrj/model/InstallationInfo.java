package com.github.bagulus.msvcrj.model;

import com.github.bagulus.msvcrj.install.InstallationCheckHandler;
import com.github.bagulus.msvcrj.version.VersionCheckHandler;
import java.util.Arrays;
import java.util.Objects;

public record InstallationInfo(
    String[] installationParameters,
    InstallationCheckHandler installationCheckHandler,
    VersionCheckHandler versionCheckHandler
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
            installationCheckHandler, that.installationCheckHandler) && Objects.equals(versionCheckHandler,
            that.versionCheckHandler);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(installationCheckHandler, versionCheckHandler);
        result = 31 * result + Arrays.hashCode(installationParameters);
        return result;
    }

    @Override
    public String toString() {
        return "InstallationInfo{" +
            "installationParameters=" + Arrays.toString(installationParameters) +
            ", installationCheckHandler=" + installationCheckHandler +
            ", versionCheckHandler=" + versionCheckHandler +
            '}';
    }
}
