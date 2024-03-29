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

package com.github.bagulus.msvcrp4j.model;


import com.github.bagulus.msvcrp4j.model.InstallationInfo.InstallationParameters;
import com.github.bagulus.msvcrp4j.resolver.installation.InstallationResolverFactory;
import com.github.bagulus.msvcrp4j.resolver.version.VersionResolverFactory;
import com.github.robtimus.os.windows.registry.RegistryKey;
import java.net.URI;

/**
 * Factory class, which contains only static methods that return {@link MicrosoftVisualCppRedistributable} objects for
 * each {@link Release} and {@link ProcessorArchitecture} (their latest version).
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

        Release release = Release.Y2005;
        ProcessorArchitecture processorArchitecture = ProcessorArchitecture.X64;

        DownloadInfo downloadInfo = new DownloadInfo(
            URI.create(
                "https://download.microsoft.com/download/8/B/4/8B42259F-5D70-43F4-AC2E-4B208FD8D66A/vcredist_x64.EXE")
        );

        RegistryKey registryKey = RegistryKey.HKEY_LOCAL_MACHINE.resolve(
            "SOFTWARE\\Classes\\Installer\\Products\\1af2a8da7e60d0b429d7e6453b3d0182");

        InstallationInfo installationInfo = new InstallationInfo(
            InstallationParameters.Y2005,
            InstallationResolverFactory.createRegistryKeyExistsInstallationResolver(registryKey),
            VersionResolverFactory.createDWordRegistryVersionResolver(registryKey)
        );

        return new MicrosoftVisualCppRedistributable(
            release,
            processorArchitecture,
            downloadInfo,
            installationInfo
        );
    }

    static MicrosoftVisualCppRedistributable get2005X86Package() {

        Release release = Release.Y2005;
        ProcessorArchitecture processorArchitecture = ProcessorArchitecture.X86;

        DownloadInfo downloadInfo = new DownloadInfo(
            URI.create(
                "https://download.microsoft.com/download/8/B/4/8B42259F-5D70-43F4-AC2E-4B208FD8D66A/vcredist_x86.EXE")
        );

        RegistryKey registryKey = RegistryKey.HKEY_LOCAL_MACHINE.resolve(
            "SOFTWARE\\Classes\\Installer\\Products\\c1c4f01781cc94c4c8fb1542c0981a2a");

        InstallationInfo installationInfo = new InstallationInfo(
            InstallationParameters.Y2005,
            InstallationResolverFactory.createRegistryKeyExistsInstallationResolver(registryKey),
            VersionResolverFactory.createDWordRegistryVersionResolver(registryKey)
        );

        return new MicrosoftVisualCppRedistributable(
            release,
            processorArchitecture,
            downloadInfo,
            installationInfo
        );
    }

    static MicrosoftVisualCppRedistributable get2008X64Package() {

        Release release = Release.Y2008;
        ProcessorArchitecture processorArchitecture = ProcessorArchitecture.X64;

        DownloadInfo downloadInfo = new DownloadInfo(
            URI.create(
                "https://download.microsoft.com/download/5/D/8/5D8C65CB-C849-4025-8E95-C3966CAFD8AE/vcredist_x64.exe")
        );

        RegistryKey registryKey = RegistryKey.HKEY_LOCAL_MACHINE.resolve(
            "SOFTWARE\\Classes\\Installer\\Products\\67D6ECF5CD5FBA732B8B22BAC8DE1B4D");

        InstallationInfo installationInfo = new InstallationInfo(
            InstallationParameters.Y2008,
            InstallationResolverFactory.createRegistryKeyExistsInstallationResolver(registryKey),
            VersionResolverFactory.createProductNameRegistryVersionResolver(registryKey)
        );

        return new MicrosoftVisualCppRedistributable(
            release,
            processorArchitecture,
            downloadInfo,
            installationInfo
        );
    }

    static MicrosoftVisualCppRedistributable get2008X86Package() {

        Release release = Release.Y2008;
        ProcessorArchitecture processorArchitecture = ProcessorArchitecture.X86;

        DownloadInfo downloadInfo = new DownloadInfo(
            URI.create(
                "https://download.microsoft.com/download/5/D/8/5D8C65CB-C849-4025-8E95-C3966CAFD8AE/vcredist_x86.exe")
        );

        RegistryKey registryKey = RegistryKey.HKEY_LOCAL_MACHINE.resolve(
            "SOFTWARE\\Classes\\Installer\\Products\\6E815EB96CCE9A53884E7857C57002F0");

        InstallationInfo installationInfo = new InstallationInfo(
            InstallationParameters.Y2008,
            InstallationResolverFactory.createRegistryKeyExistsInstallationResolver(registryKey),
            VersionResolverFactory.createProductNameRegistryVersionResolver(registryKey)
        );

        return new MicrosoftVisualCppRedistributable(
            release,
            processorArchitecture,
            downloadInfo,
            installationInfo
        );
    }

    static MicrosoftVisualCppRedistributable get2010X64Package() {

        Release release = Release.Y2010;
        ProcessorArchitecture processorArchitecture = ProcessorArchitecture.X64;

        DownloadInfo downloadInfo = new DownloadInfo(
            URI.create(
                "https://download.microsoft.com/download/E/E/0/EE05C9EF-A661-4D9E-BCE2-6961ECDF087F/vcredist_x64.exe")
        );

        RegistryKey registryKey = RegistryKey.HKEY_LOCAL_MACHINE.resolve(
            "SOFTWARE\\Classes\\Installer\\Products\\1926E8D15D0BCE53481466615F760A7F");

        InstallationInfo installationInfo = new InstallationInfo(
            InstallationParameters.Y2010,
            InstallationResolverFactory.createRegistryKeyExistsInstallationResolver(registryKey),
            VersionResolverFactory.createProductNameRegistryVersionResolver(registryKey)
        );

        return new MicrosoftVisualCppRedistributable(
            release,
            processorArchitecture,
            downloadInfo,
            installationInfo
        );
    }

    static MicrosoftVisualCppRedistributable get2010X86Package() {

        Release release = Release.Y2010;
        ProcessorArchitecture processorArchitecture = ProcessorArchitecture.X86;

        DownloadInfo downloadInfo = new DownloadInfo(
            URI.create(
                "https://download.microsoft.com/download/E/E/0/EE05C9EF-A661-4D9E-BCE2-6961ECDF087F/vcredist_x86.exe")
        );

        RegistryKey registryKey = RegistryKey.HKEY_LOCAL_MACHINE.resolve(
            "SOFTWARE\\Classes\\Installer\\Products\\1D5E3C0FEDA1E123187686FED06E995A");

        InstallationInfo installationInfo = new InstallationInfo(
            InstallationParameters.Y2010,
            InstallationResolverFactory.createRegistryKeyExistsInstallationResolver(registryKey),
            VersionResolverFactory.createProductNameRegistryVersionResolver(registryKey)
        );

        return new MicrosoftVisualCppRedistributable(
            release,
            processorArchitecture,
            downloadInfo,
            installationInfo
        );
    }

    static MicrosoftVisualCppRedistributable get2012X64Package() {

        Release release = Release.Y2012;
        ProcessorArchitecture processorArchitecture = ProcessorArchitecture.X64;

        DownloadInfo downloadInfo = new DownloadInfo(
            URI.create(
                "https://download.microsoft.com/download/1/6/B/16B06F60-3B20-4FF2-B699-5E9B7962F9AE/VSU_4/vcredist_x64.exe")
        );

        RegistryKey registryKey = RegistryKey.HKEY_LOCAL_MACHINE.resolve(
            "SOFTWARE\\Classes\\Installer\\Dependencies\\{ca67548a-5ebe-413a-b50c-4b9ceb6d66c6}");

        InstallationInfo installationInfo = new InstallationInfo(
            InstallationParameters.Y2012,
            InstallationResolverFactory.createRegistryKeyExistsInstallationResolver(registryKey),
            VersionResolverFactory.createStringRegistryVersionResolver(registryKey)
        );

        return new MicrosoftVisualCppRedistributable(
            release,
            processorArchitecture,
            downloadInfo,
            installationInfo
        );
    }

    static MicrosoftVisualCppRedistributable get2012X86Package() {

        Release release = Release.Y2012;
        ProcessorArchitecture processorArchitecture = ProcessorArchitecture.X86;

        DownloadInfo downloadInfo = new DownloadInfo(
            URI.create(
                "https://download.microsoft.com/download/1/6/B/16B06F60-3B20-4FF2-B699-5E9B7962F9AE/VSU_4/vcredist_x86.exe")
        );

        RegistryKey registryKey = RegistryKey.HKEY_LOCAL_MACHINE.resolve(
            "SOFTWARE\\Classes\\Installer\\Dependencies\\{33d1fd90-4274-48a1-9bc1-97e33d9c2d6f}");

        InstallationInfo installationInfo = new InstallationInfo(
            InstallationParameters.Y2012,
            InstallationResolverFactory.createRegistryKeyExistsInstallationResolver(registryKey),
            VersionResolverFactory.createStringRegistryVersionResolver(registryKey)
        );

        return new MicrosoftVisualCppRedistributable(
            release,
            processorArchitecture,
            downloadInfo,
            installationInfo
        );
    }

    static MicrosoftVisualCppRedistributable get2013X64Package() {

        Release release = Release.Y2013;
        ProcessorArchitecture processorArchitecture = ProcessorArchitecture.X64;

        DownloadInfo downloadInfo = new DownloadInfo(
            URI.create("https://aka.ms/highdpimfc2013x64enu")
        );

        RegistryKey registryKey = RegistryKey.HKEY_LOCAL_MACHINE.resolve(
            "SOFTWARE\\Classes\\Installer\\Dependencies\\{042d26ef-3dbe-4c25-95d3-4c1b11b235a7}");

        InstallationInfo installationInfo = new InstallationInfo(
            InstallationParameters.Y2013,
            InstallationResolverFactory.createRegistryKeyExistsInstallationResolver(registryKey),
            VersionResolverFactory.createStringRegistryVersionResolver(registryKey)
        );

        return new MicrosoftVisualCppRedistributable(
            release,
            processorArchitecture,
            downloadInfo,
            installationInfo
        );
    }

    static MicrosoftVisualCppRedistributable get2013X86Package() {

        Release release = Release.Y2013;
        ProcessorArchitecture processorArchitecture = ProcessorArchitecture.X86;

        DownloadInfo downloadInfo = new DownloadInfo(
            URI.create("https://aka.ms/highdpimfc2013x86enu")
        );

        RegistryKey registryKey = RegistryKey.HKEY_LOCAL_MACHINE.resolve(
            "SOFTWARE\\Classes\\Installer\\Dependencies\\{9dff3540-fc85-4ed5-ac84-9e3c7fd8bece}");

        InstallationInfo installationInfo = new InstallationInfo(
            InstallationParameters.Y2013,
            InstallationResolverFactory.createRegistryKeyExistsInstallationResolver(registryKey),
            VersionResolverFactory.createStringRegistryVersionResolver(registryKey)
        );

        return new MicrosoftVisualCppRedistributable(
            release,
            processorArchitecture,
            downloadInfo,
            installationInfo
        );
    }

    static MicrosoftVisualCppRedistributable get2015PlusX64Package() {

        Release release = Release.Y2015PLUS;
        ProcessorArchitecture processorArchitecture = ProcessorArchitecture.X64;

        DownloadInfo downloadInfo = new DownloadInfo(
            URI.create("https://aka.ms/vs/17/release/VC_redist.x64.exe")
        );

        RegistryKey registryKey = RegistryKey.HKEY_LOCAL_MACHINE.resolve(
            "SOFTWARE\\WOW6432Node\\Microsoft\\VisualStudio\\14.0\\VC\\Runtimes\\x64");

        InstallationInfo installationInfo = new InstallationInfo(
            InstallationParameters.Y2015PLUS,
            InstallationResolverFactory.createRegistryInstalledValueSetInstallationResolver(registryKey),
            VersionResolverFactory.createStringRegistryVersionResolver(registryKey)
        );

        return new MicrosoftVisualCppRedistributable(
            release,
            processorArchitecture,
            downloadInfo,
            installationInfo
        );
    }

    static MicrosoftVisualCppRedistributable get2015PlusX86Package() {

        Release release = Release.Y2015PLUS;
        ProcessorArchitecture processorArchitecture = ProcessorArchitecture.X86;

        DownloadInfo downloadInfo = new DownloadInfo(
            URI.create("https://aka.ms/vs/17/release/VC_redist.x86.exe")
        );

        RegistryKey registryKey = RegistryKey.HKEY_LOCAL_MACHINE.resolve(
            "SOFTWARE\\WOW6432Node\\Microsoft\\VisualStudio\\14.0\\VC\\Runtimes\\x86");

        InstallationInfo installationInfo = new InstallationInfo(
            InstallationParameters.Y2015PLUS,
            InstallationResolverFactory.createRegistryInstalledValueSetInstallationResolver(registryKey),
            VersionResolverFactory.createStringRegistryVersionResolver(registryKey)
        );

        return new MicrosoftVisualCppRedistributable(
            release,
            processorArchitecture,
            downloadInfo,
            installationInfo
        );
    }
}
