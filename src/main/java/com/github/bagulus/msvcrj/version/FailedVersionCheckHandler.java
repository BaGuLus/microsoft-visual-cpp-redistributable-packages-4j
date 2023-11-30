package com.github.bagulus.msvcrj.version;

public class FailedVersionCheckHandler implements VersionCheckHandler {

    private final String message;

    public FailedVersionCheckHandler(String message) {
        this.message = message;
    }

    public FailedVersionCheckHandler(Throwable cause) {
        this(cause.getMessage());
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
        throw new UnsupportedOperationException("Cannot get the Major number with this handler");
    }

    @Override
    public String getVersion() {
        return "The version couldn't be determined. Cause: " + message;
    }
}
