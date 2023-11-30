package com.github.bagulus.msvcrj.install;

import com.github.robtimus.os.windows.registry.RegistryException;
import com.github.robtimus.os.windows.registry.RegistryKey;

public record RegistryInstalledValueSetInstallationCheckHandler(RegistryKey registryKey)
    implements InstallationCheckHandler {

    @Override
    public boolean isInstalled() {
        try {
            return registryKey.getDWordValue("Installed") == 1;
        } catch (RegistryException e) {
            return false;
        }
    }
}
