package com.github.bagulus.jmsvcrp.installation;

public class DownloadFailedException extends Exception {
    public DownloadFailedException() {
        super();
    }

    public DownloadFailedException(String message) {
        super(message);
    }

    public DownloadFailedException(Throwable cause) {
        super(cause);
    }

    public DownloadFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
