package com.github.bagulus.msvcrj.installation;


import com.github.bagulus.msvcrj.model.MicrosoftVisualCppRedistributable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;

public class MicrosoftVisualCppRedistributableInstaller {
    private final MicrosoftVisualCppRedistributable redistributable;
    private final Path downloadDirectory;
    private final Path installationFile;


    public MicrosoftVisualCppRedistributableInstaller(
            MicrosoftVisualCppRedistributable redistributable,
            Path downloadDirectory
    ) {
        this.redistributable = redistributable;
        this.downloadDirectory = downloadDirectory;
        this.installationFile = downloadDirectory.resolve(redistributable.getName() + ".exe");
    }

    public long download() throws DownloadFailedException {
        try (
                InputStream urlStream = redistributable.getDownloadUri().toURL().openStream();
                ReadableByteChannel readableByteChannel = Channels.newChannel(urlStream);
                FileOutputStream fileOutputStream = new FileOutputStream(Files.createFile(installationFile).toFile())
        ) {
            FileChannel fileChannel = fileOutputStream.getChannel();
            return fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            throw new DownloadFailedException(e);
        }
    }

    public void install() throws InstallationFailedException {
        try {
            Process installProcess = new ProcessBuilder()
                    .directory(downloadDirectory.toFile())
                    .command(String.valueOf(installationFile), "/q", "/norestart")
                    .start();
            installProcess.waitFor();
        } catch (InterruptedException | IOException e) {
            throw new InstallationFailedException(e);
        }
    }

    public MicrosoftVisualCppRedistributable getRedistributable() {
        return redistributable;
    }

    public Path getInstallationFile() {
        return installationFile;
    }
}
