package com.github.bagulus.msvcrj.install;

import com.github.robtimus.os.windows.registry.RegistryKey;

public final class InstallationResolverFactory {
    private InstallationResolverFactory() {
        throw new AssertionError("Class not instantiable");
    }

    public static InstallationResolver createRegistryInstalledValueSetInstallationResolver(RegistryKey registryKey) {
        return new RegistryInstalledValueSetInstallationResolver(registryKey);
    }

    public static InstallationResolver createRegistryKeyExistsInstallationResolver(RegistryKey registryKey) {
        return new RegistryKeyExistsInstallationResolver(registryKey);
    }
}
