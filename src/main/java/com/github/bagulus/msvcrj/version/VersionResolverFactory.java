package com.github.bagulus.msvcrj.version;

import com.github.robtimus.os.windows.registry.RegistryKey;

public final class VersionResolverFactory {

    private VersionResolverFactory() {
        throw new AssertionError("Class not instantiable");
    }

    public static VersionResolver createStringRegistryVersionResolver(RegistryKey registryKey) {
        try {
            return new StringRegistryVersionResolver(registryKey);
        } catch (VersionCheckFailedException e) {
            return null;
        }
    }

    public static VersionResolver createDWordRegistryVersionResolver(RegistryKey registryKey) {
        try {
            return new DWordRegistryVersionResolver(registryKey);
        } catch (VersionCheckFailedException e) {
            return null;
        }
    }

    public static VersionResolver createProductNameRegistryVersionResolver(RegistryKey registryKey) {
        try {
            return new ProductNameRegistryVersionResolver(registryKey);
        } catch (VersionCheckFailedException e) {
            return null;
        }
    }
}
