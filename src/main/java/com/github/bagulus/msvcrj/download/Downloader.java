package com.github.bagulus.msvcrj.download;

public interface Downloader {
    long download() throws DownloadFailedException;
}
