package com.github.bagulus.msvcrj.version;

public class VersionCheckFailedException extends Exception {

    public VersionCheckFailedException() {
        super();
    }

    public VersionCheckFailedException(String message) {
        super(message);
    }

    public VersionCheckFailedException(Throwable cause) {
        super(cause);
    }

    public VersionCheckFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
