package com.github.bagulus.msvcrp4j.resolver.installation;

import com.github.robtimus.os.windows.registry.RegistryKey;

abstract class RegistryInstallationResolver implements InstallationResolver {

    private final RegistryKey registryKey;
    private Boolean isInstalled = null;

    protected RegistryInstallationResolver(RegistryKey registryKey) {
        this.registryKey = registryKey;
    }

    protected abstract boolean findIfInstalledInRegistry(RegistryKey registryKey);

    @Override
    public boolean isInstalled() {
        if (isInstalled == null) {
            isInstalled = findIfInstalledInRegistry(registryKey);
        }
        return isInstalled;
    }
}
