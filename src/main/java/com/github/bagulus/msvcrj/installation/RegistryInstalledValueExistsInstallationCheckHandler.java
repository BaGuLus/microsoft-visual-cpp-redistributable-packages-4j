package com.github.bagulus.msvcrj.installation;

import com.github.robtimus.os.windows.registry.RegistryException;
import com.github.robtimus.os.windows.registry.RegistryKey;

public record RegistryInstalledValueExistsInstallationCheckHandler(RegistryKey registryKey)
        implements InstallationCheckHandler {

    @Override
    public boolean isInstalled() {
        try {
            return registryKey.findDWordValue("Installed").isPresent();
        } catch (RegistryException e) {
            return false;
        }
    }
}
