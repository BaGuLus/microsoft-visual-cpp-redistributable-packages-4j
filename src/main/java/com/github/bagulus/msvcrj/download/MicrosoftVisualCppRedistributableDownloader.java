package com.github.bagulus.msvcrj.download;

import com.github.bagulus.msvcrj.model.MicrosoftVisualCppRedistributable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class MicrosoftVisualCppRedistributableDownloader implements Downloader {

    private final MicrosoftVisualCppRedistributable redistributable;
    private final Path absoluteDownloadFilepath;

    public MicrosoftVisualCppRedistributableDownloader(
            MicrosoftVisualCppRedistributable redistributable,
            Path downloadFileName,
            Path workingDirectory
    ) {
        this.redistributable = Objects.requireNonNull(redistributable);
        this.absoluteDownloadFilepath =
                Objects.requireNonNull(workingDirectory).resolve(Objects.requireNonNull(downloadFileName));
    }

    @Override
    public long download() throws DownloadFailedException {
        try (
                InputStream urlStream = redistributable.downloadInfo().downloadUri().toURL().openStream();
                ReadableByteChannel readableByteChannel = Channels.newChannel(urlStream);
                FileOutputStream fileOutputStream = new FileOutputStream(Files.createFile(absoluteDownloadFilepath).toFile())
        ) {
            FileChannel fileChannel = fileOutputStream.getChannel();
            return fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            throw new DownloadFailedException(e);
        }
    }
}
