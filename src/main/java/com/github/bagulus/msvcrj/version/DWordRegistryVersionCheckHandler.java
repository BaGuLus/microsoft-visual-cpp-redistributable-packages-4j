package com.github.bagulus.msvcrj.version;

import com.github.robtimus.os.windows.registry.RegistryException;
import com.github.robtimus.os.windows.registry.RegistryKey;

abstract class DWordRegistryVersionCheckHandler implements VersionCheckHandler {

    protected int findValue(RegistryKey registryKey, String value) throws VersionCheckFailedException {
        try {
            return registryKey.getDWordValue(value);
        } catch (RegistryException e) {
            throw new VersionCheckFailedException("Unable to find value in registry", e);
        }
    }
}
