package com.github.bagulus.msvcrj.version;

import com.github.robtimus.os.windows.registry.RegistryException;
import com.github.robtimus.os.windows.registry.RegistryKey;

public class VersionRegistryVersionCheckHandler extends StringVersionCheckHandler {

    public VersionRegistryVersionCheckHandler(RegistryKey registryKey) throws VersionCheckFailedException {
        super(getVersionString(registryKey));
    }

    private static String getVersionString(RegistryKey registryKey) throws VersionCheckFailedException {
        try {
            return registryKey.getStringValue("Version");
        } catch (RegistryException e) {
            throw new VersionCheckFailedException("Unable to parse version from product name", e);
        }
    }
}
