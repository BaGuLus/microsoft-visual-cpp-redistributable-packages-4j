package com.github.bagulus.jmsvcrp.installation;

public class InstallationFailedException extends Exception {
    public InstallationFailedException() {
        super();
    }

    public InstallationFailedException(String message) {
        super(message);
    }

    public InstallationFailedException(Throwable cause) {
        super(cause);
    }

    public InstallationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
