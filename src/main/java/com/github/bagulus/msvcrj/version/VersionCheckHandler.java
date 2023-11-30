package com.github.bagulus.msvcrj.version;

public interface VersionCheckHandler {
    int getMajor();
    int getMinor();
    int getBuild();
    int getRBuild();
    String getVersion();
}
