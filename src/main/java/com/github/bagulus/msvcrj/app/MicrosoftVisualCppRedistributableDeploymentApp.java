package com.github.bagulus.msvcrj.app;

import com.github.bagulus.msvcrj.download.DownloadFailedException;
import com.github.bagulus.msvcrj.download.Downloader;
import com.github.bagulus.msvcrj.download.MicrosoftVisualCppRedistributableDownloader;
import com.github.bagulus.msvcrj.install.InstallationFailedException;
import com.github.bagulus.msvcrj.install.Installer;
import com.github.bagulus.msvcrj.install.MicrosoftVisualCppRedistributableInstaller;
import com.github.bagulus.msvcrj.model.MicrosoftVisualCppRedistributable;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * Command line application which installs the specified Microsoft Visual C++ Redistributable Packages.
 * Firstly, a download directory is created in user's Downloads directory. Then for each package, the program:
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
    private static final Path DOWNLOAD_DIRECTORY = Path.of(System.getProperty("user.home"), "Downloads", "vcredist-temp");
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
            MicrosoftVisualCppRedistributable.X64_2015TO2022,
            MicrosoftVisualCppRedistributable.X86_2015TO2022
    );

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
                """, new Date()));
        try {
            deleteDownloadDirectory();
            Files.createDirectories(DOWNLOAD_DIRECTORY);

            for (MicrosoftVisualCppRedistributable redistributable : MICROSOFT_VISUAL_CPP_REDISTRIBUTABLE_TO_INSTALL) {
                Path fileName = createFileName(redistributable.version(), redistributable.processorArchitecture());
                deploy(redistributable, fileName, DOWNLOAD_DIRECTORY);
            }

            int redistributablesToInstallCount = MICROSOFT_VISUAL_CPP_REDISTRIBUTABLE_TO_INSTALL.size();

            int downloadsUnsuccessful = redistributablesToInstallCount - downloadsSuccessful - deploymentsSkipped;
            int installationsUnsuccessful = redistributablesToInstallCount - installationsSuccessful - deploymentsSkipped;

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
                    """, new Date())
            );
        }
    }

    private static void deploy(
            MicrosoftVisualCppRedistributable redistributable,
            Path fileName,
            Path workingDirectory
    ) {
        if (redistributable.installationInfo().installationCheckHandler().isInstalled()) {
            System.out.println(MessageFormat.format("""
                       | SKIPPING {0} DEPLOYMENT
                       | Reason: The redistributable is already installed
                    """, redistributable));

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
            MicrosoftVisualCppRedistributable.Version version,
            MicrosoftVisualCppRedistributable.ProcessorArchitecture processorArchitecture
    ) {
        final String DELIMITER = "-";
        return Path.of("vc-redistributable"
                .concat(DELIMITER)
                .concat(String.valueOf(version))
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
