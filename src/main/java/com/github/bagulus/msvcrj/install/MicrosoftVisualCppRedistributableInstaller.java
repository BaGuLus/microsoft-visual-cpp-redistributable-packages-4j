package com.github.bagulus.msvcrj.install;


import com.github.bagulus.msvcrj.model.MicrosoftVisualCppRedistributable;
import java.io.IOException;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class MicrosoftVisualCppRedistributableInstaller implements Installer {

    private final MicrosoftVisualCppRedistributable redistributable;
    private final Path absoluteDownloadFilepath;


    public MicrosoftVisualCppRedistributableInstaller(
        MicrosoftVisualCppRedistributable redistributable,
        Path installationFileName,
        Path workingDirectory
    ) {
        this.redistributable = redistributable;
        this.absoluteDownloadFilepath =
            Objects.requireNonNull(workingDirectory).resolve(Objects.requireNonNull(installationFileName));
    }

    @Override
    public void install() throws InstallationFailedException {
        String[] command = Stream
            .concat(
                Arrays.stream(new String[]{String.valueOf(absoluteDownloadFilepath)}),
                Arrays.stream(redistributable.installationInfo().installationParameters()))
            .toArray(String[]::new);
        try {
            Process installProcess = new ProcessBuilder()
                .command(command)
                .start();
            int exitValue = installProcess.waitFor();
            if (exitValue != 0) {
                throw new InstallationFailedException(MessageFormat.format(
                    "Install process ended with exit value {0}", exitValue)
                );
            }
        } catch (InterruptedException | IOException e) {
            throw new InstallationFailedException(e);
        }
    }
}
