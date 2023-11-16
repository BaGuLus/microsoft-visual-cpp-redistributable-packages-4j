package com.github.bagulus.msvcrj.installation;

import com.github.robtimus.os.windows.registry.RegistryException;
import com.github.robtimus.os.windows.registry.RegistryKey;

public record RegistryKeyExistsInstallationCheckHandler(RegistryKey registryKey)
        implements InstallationCheckHandler {

    @Override
    public boolean isInstalled() {
        try {
            return registryKey.exists();
        } catch (RegistryException e) {
            return false;
        }
    }
}
