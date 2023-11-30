package com.github.bagulus.msvcrj.version;

import com.github.robtimus.os.windows.registry.RegistryException;
import com.github.robtimus.os.windows.registry.RegistryKey;

public class MultipleDWordRegistryVersionCheckHandler extends DWordRegistryVersionCheckHandler {

    private final int major;
    private final int minor;
    private final int build;
    private final int rbuild;

    public MultipleDWordRegistryVersionCheckHandler(RegistryKey registryKey) throws VersionCheckFailedException {
        this.major = findValue(registryKey, "Major");
        this.minor = findValue(registryKey, "Minor");
        this.build = findValue(registryKey, "Bld");
        this.rbuild = findValue(registryKey, "Rbld");
    }



    @Override
    public int getMajor() {
        return major;
    }

    @Override
    public int getMinor() {
        return minor;
    }

    @Override
    public int getBuild() {
        return build;
    }

    @Override
    public int getRBuild() {
        return rbuild;
    }

    @Override
    public String getVersion() {
        return major + "." + minor + "." + build + "." + rbuild;
    }
}
