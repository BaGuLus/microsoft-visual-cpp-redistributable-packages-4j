package com.github.bagulus.msvcrj.app;

import com.github.bagulus.msvcrj.installation.DownloadFailedException;
import com.github.bagulus.msvcrj.installation.InstallationFailedException;
import com.github.bagulus.msvcrj.installation.MicrosoftVisualCppRedistributableInstaller;
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
public class MicrosoftVisualCppRedistributableInstallationApp {
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

    private static int installationsSkipped = 0;
    private static int downloadsSuccessful = 0;
    private static int installationsSuccessful = 0;


    public static void main(String[] args) {
        run();
    }

    public static void run() {
        System.out.println(MessageFormat.format("""
                   |------------------------------------------------------------------------------------
                ---| STARTED MICROSOFT VISUAL C++ REDISTRIBUTABLES INSTALLATION
                   | Start time: {0}
                """, new Date()));
        try {
            deleteDownloadDirectory();
            Files.createDirectories(DOWNLOAD_DIRECTORY);

            MICROSOFT_VISUAL_CPP_REDISTRIBUTABLE_TO_INSTALL
                    .forEach(MicrosoftVisualCppRedistributableInstallationApp::perform);

            int redistributablesToInstallCount = MICROSOFT_VISUAL_CPP_REDISTRIBUTABLE_TO_INSTALL.size();

            int downloadsUnsuccessful = redistributablesToInstallCount - downloadsSuccessful - installationsSkipped;
            int installationsUnsuccessful = redistributablesToInstallCount - installationsSuccessful - installationsSkipped;

            System.out.println(MessageFormat.format("""
                    ---| FINISHED MICROSOFT VISUAL C++ REDISTRIBUTABLES INSTALLATION
                       | Installations skipped: {0}
                       | Successfully downloaded files: {1}
                       | Successfully installed files: {2}
                       | Unsuccessfully downloaded files: {3}
                       | Unsuccessfully installed files: {4}
                    """,
                    installationsSkipped,
                    downloadsSuccessful,
                    installationsSuccessful,
                    downloadsUnsuccessful,
                    installationsUnsuccessful)
            );

            deleteDownloadDirectory();

        } catch (IOException e) {
            System.out.println(MessageFormat.format("""
                    ---| FINISHED MICROSOFT VISUAL C++ REDISTRIBUTABLES INSTALLATION WITH ERROR
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

    private static void perform(MicrosoftVisualCppRedistributable redistributablePackage) {
        String name = redistributablePackage.getName();

        if (redistributablePackage.isInstalled()) {
            System.out.println(MessageFormat.format("""
                       | SKIPPING {0} INSTALLATION
                       | Reason: The package is already installed
                    """, name));

            installationsSkipped++;
            return;
        }

        MicrosoftVisualCppRedistributableInstaller redistributableInstaller
                = new MicrosoftVisualCppRedistributableInstaller(redistributablePackage, DOWNLOAD_DIRECTORY);

        try {
            download(redistributableInstaller);
            install(redistributableInstaller);

        } catch (DownloadFailedException e) {
            System.out.println(MessageFormat.format("""
                   | FINISHED {0} DOWNLOAD UNSUCCESSFULLY
                   | Reason: {1}
                """, name, e)
            );

        } catch (InstallationFailedException e) {
            System.out.println(MessageFormat.format("""
                       | FINISHED {0} INSTALLATION UNSUCCESSFULLY
                       | Reason: {1}
                    """, name, e)
            );
        }
    }

    private static void download(MicrosoftVisualCppRedistributableInstaller redistributableInstaller)
            throws DownloadFailedException {
        String name = redistributableInstaller.getRedistributable().getName();
        URI downloadUri = redistributableInstaller.getRedistributable().getDownloadUri();
        String installationFile = String.valueOf(redistributableInstaller.getInstallationFile());

        System.out.println(MessageFormat.format("""
                   | STARTED {0} DOWNLOAD
                   | Download URI: {1}
                """, name, downloadUri)
        );

        long transferred = redistributableInstaller.download();

        System.out.println(MessageFormat.format("""
                  | FINISHED {0} DOWNLOAD SUCCESSFULLY
                  | Transferred: {1} KB
                  | Downloaded file: {2}
               """, name, transferred / 1000, installationFile)
        );

        downloadsSuccessful++;
    }

    private static void install(MicrosoftVisualCppRedistributableInstaller redistributableInstaller)
            throws InstallationFailedException {
        String name = redistributableInstaller.getRedistributable().getName();
        String installationFile = String.valueOf(redistributableInstaller.getInstallationFile());

        System.out.println(MessageFormat.format("""
                  | STARTED {0} INSTALLATION
                  | Installing file: {1}
               """, name, installationFile));

        redistributableInstaller.install();

        System.out.println(MessageFormat.format("""
                      | FINISHED {0} INSTALLATION SUCCESSFULLY
                   """, name)
        );

        installationsSuccessful++;
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
