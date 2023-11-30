package com.github.bagulus.msvcrj.version;

import com.github.robtimus.os.windows.registry.RegistryKey;

public class SingleDWordRegistryVersionCheckHandler extends DWordRegistryVersionCheckHandler {

    private final int version;

    public SingleDWordRegistryVersionCheckHandler(RegistryKey registryKey) throws VersionCheckFailedException {
        this.version = findValue(registryKey, "Version");
    }

    @Override
    public int getMajor() {
        throw new UnsupportedOperationException("Cannot get the Major number with this handler");
    }

    @Override
    public int getMinor() {
        throw new UnsupportedOperationException("Cannot get the Minor number with this handler");
    }

    @Override
    public int getBuild() {
        throw new UnsupportedOperationException("Cannot get the Build number with this handler");
    }

    @Override
    public int getRBuild() {
        throw new UnsupportedOperationException("Cannot get the RBuild number with this handler");
    }

    @Override
    public String getVersion() {
        return String.valueOf(version);
    }
}
