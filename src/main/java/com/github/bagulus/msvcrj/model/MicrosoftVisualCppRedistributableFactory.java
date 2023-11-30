package com.github.bagulus.msvcrj.model;

import static com.github.bagulus.msvcrj.model.InstallationInfo.INSTALLATION_PARAMETERS_2005;
import static com.github.bagulus.msvcrj.model.InstallationInfo.INSTALLATION_PARAMETERS_2008;
import static com.github.bagulus.msvcrj.model.InstallationInfo.INSTALLATION_PARAMETERS_2010;
import static com.github.bagulus.msvcrj.model.InstallationInfo.INSTALLATION_PARAMETERS_2012_PLUS;

import com.github.bagulus.msvcrj.install.RegistryInstalledValueExistsInstallationCheckHandler;
import com.github.bagulus.msvcrj.install.RegistryKeyExistsInstallationCheckHandler;
import com.github.bagulus.msvcrj.version.FailedVersionCheckHandler;
import com.github.bagulus.msvcrj.version.MultipleDWordRegistryVersionCheckHandler;
import com.github.bagulus.msvcrj.version.ProductNameRegistryVersionCheckHandler;
import com.github.bagulus.msvcrj.version.SingleDWordRegistryVersionCheckHandler;
import com.github.bagulus.msvcrj.version.VersionCheckHandler;
import com.github.bagulus.msvcrj.version.VersionRegistryVersionCheckHandler;
import com.github.robtimus.os.windows.registry.RegistryKey;
import java.net.URI;

/**
 * Factory class, which contains only static methods that return {@link MicrosoftVisualCppRedistributable} objects for
 * each version and processor architecture.
 * <p><br>
 * <a href="https://gist.github.com/ChuckMichael/7366c38f27e524add3c54f710678c98b">Download URIs</a>
 * <a
 * href="https://stackoverflow.com/questions/12206314/detect-if-visual-c-redistributable-for-visual-studio-2012-is-installed">Registry
 * keys</a>
 */
class MicrosoftVisualCppRedistributableFactory {

    private MicrosoftVisualCppRedistributableFactory() {
        throw new AssertionError("Class not instantiable");
    }

    static MicrosoftVisualCppRedistributable get2005X64Package() {

        Version version = Version.V2005;
        ProcessorArchitecture processorArchitecture = ProcessorArchitecture.X64;

        DownloadInfo downloadInfo = new DownloadInfo(
            URI.create(
                "https://download.microsoft.com/download/8/B/4/8B42259F-5D70-43F4-AC2E-4B208FD8D66A/vcredist_x64.EXE")
        );

        RegistryKey registryKey = RegistryKey.HKEY_LOCAL_MACHINE.resolve(
            "SOFTWARE\\Classes\\Installer\\Products\\1af2a8da7e60d0b429d7e6453b3d0182");

        InstallationInfo installationInfo = new InstallationInfo(
            INSTALLATION_PARAMETERS_2005,
            new RegistryKeyExistsInstallationCheckHandler(registryKey),
            createVersionCheckHandler(SingleDWordRegistryVersionCheckHandler.class, registryKey)
        );

        return new MicrosoftVisualCppRedistributable(
            version,
            processorArchitecture,
            downloadInfo,
            installationInfo
        );
    }

    static MicrosoftVisualCppRedistributable get2005X86Package() {

        Version version = Version.V2005;
        ProcessorArchitecture processorArchitecture = ProcessorArchitecture.X86;

        DownloadInfo downloadInfo = new DownloadInfo(
            URI.create(
                "https://download.microsoft.com/download/8/B/4/8B42259F-5D70-43F4-AC2E-4B208FD8D66A/vcredist_x86.EXE")
        );

        RegistryKey registryKey = RegistryKey.HKEY_LOCAL_MACHINE.resolve(
            "SOFTWARE\\Classes\\Installer\\Products\\c1c4f01781cc94c4c8fb1542c0981a2a");

        InstallationInfo installationInfo = new InstallationInfo(
            INSTALLATION_PARAMETERS_2005,
            new RegistryKeyExistsInstallationCheckHandler(registryKey),
            createVersionCheckHandler(SingleDWordRegistryVersionCheckHandler.class, registryKey)
        );

        return new MicrosoftVisualCppRedistributable(
            version,
            processorArchitecture,
            downloadInfo,
            installationInfo
        );
    }

    static MicrosoftVisualCppRedistributable get2008X64Package() {

        Version version = Version.V2008;
        ProcessorArchitecture processorArchitecture = ProcessorArchitecture.X64;

        DownloadInfo downloadInfo = new DownloadInfo(
            URI.create(
                "https://download.microsoft.com/download/5/D/8/5D8C65CB-C849-4025-8E95-C3966CAFD8AE/vcredist_x64.exe")
        );

        RegistryKey registryKey = RegistryKey.HKEY_LOCAL_MACHINE.resolve(
            "SOFTWARE\\Classes\\Installer\\Products\\67D6ECF5CD5FBA732B8B22BAC8DE1B4D");

        InstallationInfo installationInfo = new InstallationInfo(
            INSTALLATION_PARAMETERS_2008,
            new RegistryKeyExistsInstallationCheckHandler(registryKey),
            createVersionCheckHandler(ProductNameRegistryVersionCheckHandler.class, registryKey)
        );

        return new MicrosoftVisualCppRedistributable(
            version,
            processorArchitecture,
            downloadInfo,
            installationInfo
        );
    }

    static MicrosoftVisualCppRedistributable get2008X86Package() {

        Version version = Version.V2008;
        ProcessorArchitecture processorArchitecture = ProcessorArchitecture.X86;

        DownloadInfo downloadInfo = new DownloadInfo(
            URI.create(
                "https://download.microsoft.com/download/8/B/4/8B42259F-5D70-43F4-AC2E-4B208FD8D66A/vcredist_x86.EXE")
        );
        // TODO: RegistryKey not created
        RegistryKey registryKey = RegistryKey.HKEY_LOCAL_MACHINE.resolve(
            "SOFTWARE\\Classes\\Installer\\Products\\6E815EB96CCE9A53884E7857C57002F0");

        InstallationInfo installationInfo = new InstallationInfo(
            INSTALLATION_PARAMETERS_2008,
            new RegistryKeyExistsInstallationCheckHandler(registryKey),
            createVersionCheckHandler(ProductNameRegistryVersionCheckHandler.class, registryKey)
        );

        return new MicrosoftVisualCppRedistributable(
            version,
            processorArchitecture,
            downloadInfo,
            installationInfo
        );
    }

    static MicrosoftVisualCppRedistributable get2010X64Package() {

        Version version = Version.V2010;
        ProcessorArchitecture processorArchitecture = ProcessorArchitecture.X64;

        DownloadInfo downloadInfo = new DownloadInfo(
            URI.create(
                "https://download.microsoft.com/download/E/E/0/EE05C9EF-A661-4D9E-BCE2-6961ECDF087F/vcredist_x64.exe")
        );

        RegistryKey registryKey = RegistryKey.HKEY_LOCAL_MACHINE.resolve(
            "SOFTWARE\\Classes\\Installer\\Products\\1926E8D15D0BCE53481466615F760A7F");

        InstallationInfo installationInfo = new InstallationInfo(
            INSTALLATION_PARAMETERS_2010,
            new RegistryKeyExistsInstallationCheckHandler(registryKey),
            createVersionCheckHandler(ProductNameRegistryVersionCheckHandler.class, registryKey)
        );

        return new MicrosoftVisualCppRedistributable(
            version,
            processorArchitecture,
            downloadInfo,
            installationInfo
        );
    }

    static MicrosoftVisualCppRedistributable get2010X86Package() {

        Version version = Version.V2010;
        ProcessorArchitecture processorArchitecture = ProcessorArchitecture.X86;

        DownloadInfo downloadInfo = new DownloadInfo(
            URI.create(
                "https://download.microsoft.com/download/E/E/0/EE05C9EF-A661-4D9E-BCE2-6961ECDF087F/vcredist_x86.exe")
        );

        RegistryKey registryKey = RegistryKey.HKEY_LOCAL_MACHINE.resolve(
            "SOFTWARE\\Classes\\Installer\\Products\\1D5E3C0FEDA1E123187686FED06E995A");

        InstallationInfo installationInfo = new InstallationInfo(
            INSTALLATION_PARAMETERS_2010,
            new RegistryKeyExistsInstallationCheckHandler(registryKey),
            createVersionCheckHandler(ProductNameRegistryVersionCheckHandler.class, registryKey)
        );

        return new MicrosoftVisualCppRedistributable(
            version,
            processorArchitecture,
            downloadInfo,
            installationInfo
        );
    }

    static MicrosoftVisualCppRedistributable get2012X64Package() {

        Version version = Version.V2012;
        ProcessorArchitecture processorArchitecture = ProcessorArchitecture.X64;

        DownloadInfo downloadInfo = new DownloadInfo(
            URI.create(
                "https://download.microsoft.com/download/1/6/B/16B06F60-3B20-4FF2-B699-5E9B7962F9AE/VSU_4/vcredist_x64.exe")
        );

        RegistryKey registryKey = RegistryKey.HKEY_LOCAL_MACHINE.resolve(
            "SOFTWARE\\Classes\\Installer\\Dependencies\\{ca67548a-5ebe-413a-b50c-4b9ceb6d66c6}");

        InstallationInfo installationInfo = new InstallationInfo(
            INSTALLATION_PARAMETERS_2012_PLUS,
            new RegistryKeyExistsInstallationCheckHandler(registryKey),
            createVersionCheckHandler(VersionRegistryVersionCheckHandler.class, registryKey)
        );

        return new MicrosoftVisualCppRedistributable(
            version,
            processorArchitecture,
            downloadInfo,
            installationInfo
        );
    }

    static MicrosoftVisualCppRedistributable get2012X86Package() {

        Version version = Version.V2012;
        ProcessorArchitecture processorArchitecture = ProcessorArchitecture.X86;

        DownloadInfo downloadInfo = new DownloadInfo(
            URI.create(
                "https://download.microsoft.com/download/1/6/B/16B06F60-3B20-4FF2-B699-5E9B7962F9AE/VSU_4/vcredist_x86.exe")
        );

        RegistryKey registryKey = RegistryKey.HKEY_LOCAL_MACHINE.resolve(
            "SOFTWARE\\Classes\\Installer\\Dependencies\\{33d1fd90-4274-48a1-9bc1-97e33d9c2d6f}");

        InstallationInfo installationInfo = new InstallationInfo(
            INSTALLATION_PARAMETERS_2012_PLUS,
            new RegistryKeyExistsInstallationCheckHandler(registryKey),
            createVersionCheckHandler(VersionRegistryVersionCheckHandler.class, registryKey)
        );

        return new MicrosoftVisualCppRedistributable(
            version,
            processorArchitecture,
            downloadInfo,
            installationInfo
        );
    }

    static MicrosoftVisualCppRedistributable get2013X64Package() {

        Version version = Version.V2013;
        ProcessorArchitecture processorArchitecture = ProcessorArchitecture.X64;

        DownloadInfo downloadInfo = new DownloadInfo(
            URI.create("https://aka.ms/highdpimfc2013x64enu")
        );

        RegistryKey registryKey = RegistryKey.HKEY_LOCAL_MACHINE.resolve(
            "SOFTWARE\\Classes\\Installer\\Dependencies\\{042d26ef-3dbe-4c25-95d3-4c1b11b235a7}");

        InstallationInfo installationInfo = new InstallationInfo(
            INSTALLATION_PARAMETERS_2012_PLUS,
            new RegistryKeyExistsInstallationCheckHandler(registryKey),
            createVersionCheckHandler(VersionRegistryVersionCheckHandler.class, registryKey)
        );

        return new MicrosoftVisualCppRedistributable(
            version,
            processorArchitecture,
            downloadInfo,
            installationInfo
        );
    }

    static MicrosoftVisualCppRedistributable get2013X86Package() {

        Version version = Version.V2013;
        ProcessorArchitecture processorArchitecture = ProcessorArchitecture.X86;

        DownloadInfo downloadInfo = new DownloadInfo(
            URI.create("https://aka.ms/highdpimfc2013x86enu")
        );

        RegistryKey registryKey = RegistryKey.HKEY_LOCAL_MACHINE.resolve(
            "SOFTWARE\\Classes\\Installer\\Dependencies\\{9dff3540-fc85-4ed5-ac84-9e3c7fd8bece}");

        InstallationInfo installationInfo = new InstallationInfo(
            INSTALLATION_PARAMETERS_2012_PLUS,
            new RegistryKeyExistsInstallationCheckHandler(registryKey),
            createVersionCheckHandler(VersionRegistryVersionCheckHandler.class, registryKey)
        );

        return new MicrosoftVisualCppRedistributable(
            version,
            processorArchitecture,
            downloadInfo,
            installationInfo
        );
    }

    static MicrosoftVisualCppRedistributable get2015to2022X64Package() {

        Version version = Version.V2015_2022;
        ProcessorArchitecture processorArchitecture = ProcessorArchitecture.X64;

        DownloadInfo downloadInfo = new DownloadInfo(
            URI.create("https://aka.ms/vs/17/release/VC_redist.x64.exe")
        );

        RegistryKey registryKey = RegistryKey.HKEY_LOCAL_MACHINE.resolve(
            "SOFTWARE\\WOW6432Node\\Microsoft\\VisualStudio\\14.0\\VC\\Runtimes\\x64");

        InstallationInfo installationInfo = new InstallationInfo(
            INSTALLATION_PARAMETERS_2012_PLUS,
            new RegistryInstalledValueExistsInstallationCheckHandler(registryKey),
            createVersionCheckHandler(MultipleDWordRegistryVersionCheckHandler.class, registryKey)
        );

        return new MicrosoftVisualCppRedistributable(
            version,
            processorArchitecture,
            downloadInfo,
            installationInfo
        );
    }

    static MicrosoftVisualCppRedistributable get2015to2022X86Package() {

        Version version = Version.V2015_2022;
        ProcessorArchitecture processorArchitecture = ProcessorArchitecture.X86;

        DownloadInfo downloadInfo = new DownloadInfo(
            URI.create("https://aka.ms/vs/17/release/VC_redist.x86.exe")
        );

        RegistryKey registryKey = RegistryKey.HKEY_LOCAL_MACHINE.resolve(
            "SOFTWARE\\WOW6432Node\\Microsoft\\VisualStudio\\14.0\\VC\\Runtimes\\x86");

        InstallationInfo installationInfo = new InstallationInfo(
            INSTALLATION_PARAMETERS_2012_PLUS,
            new RegistryInstalledValueExistsInstallationCheckHandler(registryKey),
            createVersionCheckHandler(MultipleDWordRegistryVersionCheckHandler.class, registryKey)
        );

        return new MicrosoftVisualCppRedistributable(
            version,
            processorArchitecture,
            downloadInfo,
            installationInfo
        );
    }

    private static VersionCheckHandler createVersionCheckHandler(
        Class<? extends VersionCheckHandler> clazz,
        RegistryKey registryKey
    ) {
        try {
            return clazz.getConstructor(registryKey.getClass()).newInstance(registryKey);
        } catch (Exception e) {
            return new FailedVersionCheckHandler(e);
        }
    }
}
