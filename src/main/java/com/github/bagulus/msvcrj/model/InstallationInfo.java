package com.github.bagulus.msvcrj.model;

import com.github.bagulus.msvcrj.install.InstallationCheckHandler;
import com.github.bagulus.msvcrj.version.VersionCheckHandler;

public record InstallationInfo(
    String[] installationParameters,
    InstallationCheckHandler installationCheckHandler,
    VersionCheckHandler versionCheckHandler
) {

    public static final String[] INSTALLATION_PARAMETERS_2005 = new String[]{"/Q"};
    public static final String[] INSTALLATION_PARAMETERS_2008 = new String[]{"/q"};
    public static final String[] INSTALLATION_PARAMETERS_2010 = new String[]{"/q", "/norestart"};
    public static final String[] INSTALLATION_PARAMETERS_2012_PLUS = new String[]{"/install", "/quiet",
        "/norestart"};
}
