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
import java.util.List;
import java.util.Optional;

/**
 * Command line application which installs the specified Microsoft Visual C++ Redistributable Packages. Firstly, a
 * download directory is created in user's Downloads directory. Then for each package, the program:
 * <ol>
 *     <li>Checks if the same version is already installed - if yes, it is skipped</li>
 *     <li>Downloads the package installer by using given link</li>
 *     <li>Starts the downloaded package installer in silent mode</li>
 * </ol>
 * Lastly the created download directory is completely removed.
 * <p>
 */
@SuppressWarnings("squid:S106")
public class MicrosoftVisualCppRedistributableDeploymentApp {

    private static final Path DOWNLOAD_DIRECTORY = Path.of(System.getProperty("user.home"), "Downloads",
        "vcredist-temp");
    private static final List<MicrosoftVisualCppRedistributable> MICROSOFT_VISUAL_CPP_REDISTRIBUTABLE_TO_INSTALL = List.of(
        MicrosoftVisualCppRedistributable.X64_2005,
        MicrosoftVisualCppRedistributable.X86_2005,
        MicrosoftVisualCppRedistributable.X64_2008,
        MicrosoftVisualCppRedistributable.X86_2008,
        MicrosoftVisualCppRedistributable.X64_2010,
        MicrosoftVisualCppRedistributable.X86_2010,
        MicrosoftVisualCppRedistributable.X64_2012,
        MicrosoftVisualCppRedistributable.X86_2012,
        MicrosoftVisualCppRedistributable.X64_2013,
        MicrosoftVisualCppRedistributable.X86_2013,
        MicrosoftVisualCppRedistributable.X64_2015PLUS,
        MicrosoftVisualCppRedistributable.X86_2015PLUS
    );
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static int deploymentsSkipped = 0;
    private static int downloadsSuccessful = 0;
    private static int installationsSuccessful = 0;


    public static void main(String[] args) {
        run();
    }

    public static void run() {
        System.out.println(MessageFormat.format("""
               |------------------------------------------------------------------------------------
            ---| STARTED MICROSOFT VISUAL C++ REDISTRIBUTABLES DEPLOYMENT
               | Start time: {0}
            """, LocalDateTime.now().format(dateTimeFormatter)));
        try {
            deleteDownloadDirectory();
            Files.createDirectories(DOWNLOAD_DIRECTORY);

            for (MicrosoftVisualCppRedistributable redistributable : MICROSOFT_VISUAL_CPP_REDISTRIBUTABLE_TO_INSTALL) {
                Path fileName = createFileName(redistributable.release(), redistributable.processorArchitecture());
                deploy(redistributable, fileName, DOWNLOAD_DIRECTORY);
            }

            int redistributablesToInstallCount = MICROSOFT_VISUAL_CPP_REDISTRIBUTABLE_TO_INSTALL.size();

            int downloadsUnsuccessful = redistributablesToInstallCount - downloadsSuccessful - deploymentsSkipped;
            int installationsUnsuccessful =
                redistributablesToInstallCount - installationsSuccessful - deploymentsSkipped;

            System.out.println(MessageFormat.format("""
                    ---| FINISHED MICROSOFT VISUAL C++ REDISTRIBUTABLES DEPLOYMENT
                       | Deployments skipped: {0}
                       | Successfully downloaded files: {1}
                       | Successfully installed files: {2}
                       | Unsuccessfully downloaded files: {3}
                       | Unsuccessfully installed files: {4}
                    """,
                deploymentsSkipped,
                downloadsSuccessful,
                installationsSuccessful,
                downloadsUnsuccessful,
                installationsUnsuccessful)
            );

            deleteDownloadDirectory();

        } catch (IOException e) {
            System.out.println(MessageFormat.format("""
                ---| FINISHED MICROSOFT VISUAL C++ REDISTRIBUTABLES DEPLOYMENT WITH ERROR
                   | Reason: {0}
                """, e)
            );
        } finally {
            System.out.println(MessageFormat.format("""
                   | End time: {0}
                   |------------------------------------------------------------------------------------
                """, LocalDateTime.now().format(dateTimeFormatter))
            );
        }
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
            System.out.println(MessageFormat.format("""
                   | SKIPPING {0} DEPLOYMENT
                   | Reason: The redistributable is already installed
                   | Installed version: {1}
                """, redistributable, versionString));

            deploymentsSkipped++;
            return;
        }

        try {
            download(redistributable, fileName, workingDirectory);
            install(redistributable, fileName, workingDirectory);

        } catch (DownloadFailedException | InstallationFailedException e) {
            System.out.println(MessageFormat.format("""
                   | FINISHED {0} DEPLOYMENT UNSUCCESSFULLY
                   | Reason: {1}
                """, redistributable, e)
            );
        }
    }

    private static void download(
        MicrosoftVisualCppRedistributable redistributable,
        Path downloadFileName,
        Path downloadDirectory
    ) throws DownloadFailedException {
        Downloader downloader = new MicrosoftVisualCppRedistributableDownloader(
            redistributable, downloadFileName, downloadDirectory
        );

        URI downloadUri = redistributable.downloadInfo().downloadUri();

        System.out.println(MessageFormat.format("""
               | STARTED {0} DOWNLOAD
               | Download URI: {1}
            """, redistributable, downloadUri)
        );

        try {
            long transferred = downloader.download();

            System.out.println(MessageFormat.format("""
                   | FINISHED {0} DOWNLOAD SUCCESSFULLY
                   | Transferred: {1} KB
                   | Downloaded file: {2}
                """, redistributable, transferred / 1000, downloadFileName)
            );

            downloadsSuccessful++;

        } catch (DownloadFailedException e) {
            System.out.println(MessageFormat.format("""
                   | FINISHED {0} DOWNLOAD UNSUCCESSFULLY
                   | Reason: {1}
                """, redistributable, e)
            );
            throw e;
        }
    }

    private static void install(
        MicrosoftVisualCppRedistributable redistributable,
        Path installationFileName,
        Path installationDirectory
    ) throws InstallationFailedException {
        Installer installer = new MicrosoftVisualCppRedistributableInstaller(
            redistributable, installationFileName, installationDirectory
        );

        System.out.println(MessageFormat.format("""
               | STARTED {0} INSTALLATION
               | Installing file: {1}
            """, redistributable, installationFileName));

        try {
            installer.install();

            System.out.println(MessageFormat.format("""
                   | FINISHED {0} INSTALLATION SUCCESSFULLY
                """, redistributable)
            );

            installationsSuccessful++;

        } catch (InstallationFailedException e) {
            System.out.println(MessageFormat.format("""
                   | FINISHED {0} INSTALLATION UNSUCCESSFULLY
                   | Reason: {1}
                """, redistributable, e)
            );
            throw e;
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

    private static void deleteDownloadDirectory() throws IOException {
        if (Files.exists(DOWNLOAD_DIRECTORY)) {
            Files.walkFileTree(DOWNLOAD_DIRECTORY, new SimpleFileVisitor<>() {

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
}
