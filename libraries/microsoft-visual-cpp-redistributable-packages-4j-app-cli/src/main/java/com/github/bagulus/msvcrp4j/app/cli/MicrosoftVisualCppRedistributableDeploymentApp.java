/*
 * MIT License
 *
 * Copyright (c) 2023 Matúš Černák
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.bagulus.msvcrp4j.app.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.github.bagulus.msvcrp4j.downloader.DownloadFailedException;
import com.github.bagulus.msvcrp4j.downloader.Downloader;
import com.github.bagulus.msvcrp4j.downloader.MicrosoftVisualCppRedistributableDownloader;
import com.github.bagulus.msvcrp4j.installer.InstallationFailedException;
import com.github.bagulus.msvcrp4j.installer.Installer;
import com.github.bagulus.msvcrp4j.installer.MicrosoftVisualCppRedistributableInstaller;
import com.github.bagulus.msvcrp4j.model.InstallationInfo;
import com.github.bagulus.msvcrp4j.model.MicrosoftVisualCppRedistributable;
import com.github.bagulus.msvcrp4j.model.ProcessorArchitecture;
import com.github.bagulus.msvcrp4j.model.Release;
import com.vdurmont.semver4j.Semver;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Optional;

/**
 * Command line application which installs the specified Microsoft Visual C++ Redistributable Packages.<br> Firstly, the
 * working directory specified with [--workingDirectory | -w] parameter (or {@code $USER_HOME/Downloads/vcredist-temp}
 * as default) is created if it's not already present. Then for each package, the program:
 * <ol>
 *     <li>If </li>
 *     <li>Checks if the same version is already installed - if yes, it is skipped</li>
 *     <li>Downloads the package installer by using given link</li>
 *     <li>Starts the downloaded package installer in silent mode</li>
 * </ol>
 * Lastly, if [--cleanup | -c] argument is not provided, or called with true, the working directory is completely removed.
 * <p>
 */
@SuppressWarnings("squid:S106")
public class MicrosoftVisualCppRedistributableDeploymentApp {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static int deploymentsSkipped = 0;
    private static int downloadsSuccessful = 0;
    private static int installationsSuccessful = 0;
    private static boolean quietMode = false;
    private static Path workingDirectory;

    public static void main(String[] argv) {
        try {
            Args args = new Args();
            JCommander.newBuilder()
                .addObject(args)
                .build()
                .parse(argv);
            run(args);
        } catch (ParameterException e) {
            e.usage();
            System.exit(1);
        }
    }

    public static void run(Args args) {
        quietMode = args.quietMode;
        workingDirectory = args.workingDirectory;

        if (args.redistributablesToDeploy != null) {
            deployCollection(args.redistributablesToDeploy);
            cleanup(args.cleanup);
            return;
        }

        if (args.redistributablesToDownload != null) {
            downloadCollection(args.redistributablesToDownload);
        }
        if (args.redistributablesToInstall != null) {
            installCollection(args.redistributablesToInstall);
        }
        cleanup(args.cleanup);
    }

    private static void deployCollection(Collection<MicrosoftVisualCppRedistributable> redistributables) {
        output(MessageFormat.format("""
               |------------------------------------------------------------------------------------
            ---| STARTED MICROSOFT VISUAL C++ REDISTRIBUTABLES DEPLOYMENT
               | Start time: {0}
            """, currentTime()));
        try {
            Files.createDirectories(workingDirectory);
        } catch (IOException e) {
            output(MessageFormat.format("""
                ---| FINISHED MICROSOFT VISUAL C++ REDISTRIBUTABLES DOWNLOAD WITH ERROR
                   | Reason: {0}
                   | End time: {1}
                   |------------------------------------------------------------------------------------
                """, e, currentTime()));
            return;
        }
        
        for (MicrosoftVisualCppRedistributable redistributable : redistributables) {
            Path fileName = createFileName(redistributable.release(), redistributable.processorArchitecture());
            deploy(redistributable, fileName, workingDirectory);
        }

        int redistributablesToDeployCount = redistributables.size();

        int downloadsUnsuccessful = redistributablesToDeployCount - downloadsSuccessful - deploymentsSkipped;
        int installationsUnsuccessful =
            redistributablesToDeployCount - installationsSuccessful - deploymentsSkipped;

        output(MessageFormat.format("""
                ---| FINISHED MICROSOFT VISUAL C++ REDISTRIBUTABLES DEPLOYMENT
                   | Deployments skipped: {0}
                   | Successfully downloaded files: {1}
                   | Successfully installed files: {2}
                   | Unsuccessfully downloaded files: {3}
                   | Unsuccessfully installed files: {4}
                   | End time: {5}
                   |------------------------------------------------------------------------------------
                """,
            deploymentsSkipped,
            downloadsSuccessful,
            installationsSuccessful,
            downloadsUnsuccessful,
            installationsUnsuccessful,
            currentTime()));

    }

    private static void downloadCollection(Collection<MicrosoftVisualCppRedistributable> redistributables) {
        output(MessageFormat.format("""
               |------------------------------------------------------------------------------------
            ---| STARTED MICROSOFT VISUAL C++ REDISTRIBUTABLES DOWNLOAD
               | Start time: {0}
            """, currentTime()));
        try {
            Files.createDirectories(workingDirectory);
        } catch (IOException e) {
            output(MessageFormat.format("""
                ---| FINISHED MICROSOFT VISUAL C++ REDISTRIBUTABLES DOWNLOAD WITH ERROR
                   | Reason: {0}
                   | End time: {1}
                   |------------------------------------------------------------------------------------
                """, e, currentTime()));
            return;
        }

        for (MicrosoftVisualCppRedistributable redistributable : redistributables) {
            Path fileName = createFileName(redistributable.release(), redistributable.processorArchitecture());
            download(redistributable, fileName, workingDirectory);
        }

        int redistributablesToDownloadCount = redistributables.size();

        int downloadsUnsuccessful = redistributablesToDownloadCount - downloadsSuccessful;

        output(MessageFormat.format("""
                ---| FINISHED MICROSOFT VISUAL C++ REDISTRIBUTABLES DOWNLOAD
                   | Successfully downloaded files: {0}
                   | Unsuccessfully downloaded files: {1}
                   | End time: {2}
                   |------------------------------------------------------------------------------------
                """,
            downloadsSuccessful, downloadsUnsuccessful, currentTime()));
    }

    private static void installCollection(Collection<MicrosoftVisualCppRedistributable> redistributables) {
        output(MessageFormat.format("""
               |------------------------------------------------------------------------------------
            ---| STARTED MICROSOFT VISUAL C++ REDISTRIBUTABLES INSTALLATION
               | Start time: {0}
            """, currentTime()));

        if (!Files.exists(workingDirectory)) {
            output(MessageFormat.format("""
                ---| FINISHED MICROSOFT VISUAL C++ REDISTRIBUTABLES INSTALLATION WITH ERROR
                   | Reason: {0}
                   | End time: {1}
                   |------------------------------------------------------------------------------------
                """, workingDirectory + " does not exist", currentTime()));
            return;
        }

        for (MicrosoftVisualCppRedistributable redistributable : redistributables) {
            Path fileName = createFileName(redistributable.release(), redistributable.processorArchitecture());
            install(redistributable, fileName, workingDirectory);
        }

        int redistributablesToInstallCount = redistributables.size();

        int installationsUnsuccessful = redistributablesToInstallCount - downloadsSuccessful;

        output(MessageFormat.format("""
                ---| FINISHED MICROSOFT VISUAL C++ REDISTRIBUTABLES INSTALLATION
                   | Successfully installed files: {0}
                   | Unsuccessfully installed files: {1}
                   | End time: {2}
                   |------------------------------------------------------------------------------------
                """,
            downloadsSuccessful, installationsUnsuccessful, currentTime()));
    }

    private static void deploy(
        MicrosoftVisualCppRedistributable redistributable,
        Path fileName,
        Path workingDirectory
    ) {
        InstallationInfo installationInfo = redistributable.installationInfo();

        Optional<Semver> version = installationInfo.getVersion();
        String versionString = version.isPresent()
            ? String.valueOf(version.orElseThrow())
            : "Couldn't be retrieved";

        if (installationInfo.isInstalled()) {
            output(MessageFormat.format("""
                   | SKIPPING {0} DEPLOYMENT
                   | Reason: The redistributable is already installed
                   | Installed version: {1}
                """, redistributable, versionString));

            deploymentsSkipped++;
            return;
        }

        download(redistributable, fileName, workingDirectory);
        install(redistributable, fileName, workingDirectory);
    }

    private static void download(
        MicrosoftVisualCppRedistributable redistributable,
        Path downloadFileName,
        Path downloadDirectory
    ) {
        Downloader downloader = new MicrosoftVisualCppRedistributableDownloader(
            redistributable, downloadDirectory.resolve(downloadFileName)
        );

        URI downloadUri = redistributable.downloadInfo().downloadUri();

        output(MessageFormat.format("""
               | STARTED {0} DOWNLOAD
               | Download URI: {1}
            """, redistributable, downloadUri)
        );

        try {
            long transferred = downloader.download();

            output(MessageFormat.format("""
                   | FINISHED {0} DOWNLOAD SUCCESSFULLY
                   | Transferred: {1} KB
                   | Downloaded file: {2}
                """, redistributable, transferred / 1000, downloadFileName)
            );

            downloadsSuccessful++;

        } catch (DownloadFailedException e) {
            output(MessageFormat.format("""
                   | FINISHED {0} DOWNLOAD UNSUCCESSFULLY
                   | Reason: {1}
                """, redistributable, e)
            );
        }
    }

    private static void install(
        MicrosoftVisualCppRedistributable redistributable,
        Path installationFileName,
        Path installationDirectory
    ) {
        Installer installer = new MicrosoftVisualCppRedistributableInstaller(
            redistributable, installationDirectory.resolve(installationFileName)
        );

        output(MessageFormat.format("""
               | STARTED {0} INSTALLATION
               | Installing file: {1}
            """, redistributable, installationFileName));

        try {
            installer.install();

            output(MessageFormat.format("""
                   | FINISHED {0} INSTALLATION SUCCESSFULLY
                """, redistributable)
            );

            installationsSuccessful++;

        } catch (InstallationFailedException e) {
            output(MessageFormat.format("""
                   | FINISHED {0} INSTALLATION UNSUCCESSFULLY
                   | Reason: {1}
                """, redistributable, e)
            );
        }
    }

    private static Path createFileName(
        Release release,
        ProcessorArchitecture processorArchitecture
    ) {
        final String DELIMITER = "-";
        return Path.of("vc-redistributable"
            .concat(DELIMITER)
            .concat(String.valueOf(release))
            .concat(DELIMITER)
            .concat(String.valueOf(processorArchitecture))
            .concat(".exe"));
    }

    private static void cleanup(boolean cleanup) {
        if (!cleanup) {
            return;
        }
        try {
            deleteDirectory(workingDirectory);
            output(MessageFormat.format("""
                   | FINISHED WORKING DIRECTORY {0} REMOVAL SUCCESSFULLY
                   |------------------------------------------------------------------------------------
                """, workingDirectory));
        } catch (IOException e) {
            output(MessageFormat.format("""
                   | FINISHED WORKING DIRECTORY {0} REMOVAL WITH ERROR
                   | Reason: {0}
                   |------------------------------------------------------------------------------------
                """, e));
        }
    }

    private static void deleteDirectory(Path directory) throws IOException {
        if (Files.exists(directory)) {
            Files.walkFileTree(directory, new SimpleFileVisitor<>() {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }

    private static void output(String s) {
        if (!quietMode) {
            System.out.println(s);
        }
    }

    private static String currentTime() {
        return LocalDateTime.now().format(DATE_TIME_FORMATTER);
    }
}
