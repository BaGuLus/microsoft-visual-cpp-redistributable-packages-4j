package com.github.bagulus.jmsvcrp.model;

import com.github.bagulus.jmsvcrp.installation.RegistryInstalledValueExistsInstallationCheckHandler;
import com.github.bagulus.jmsvcrp.installation.RegistryKeyExistsInstallationCheckHandler;
import com.github.robtimus.os.windows.registry.RegistryKey;

import java.net.URI;

class MicrosoftVisualCppRedistributablePackageFactory {

    private MicrosoftVisualCppRedistributablePackageFactory() {
        throw new AssertionError("Class not instantiable");
    }

    static MicrosoftVisualCppRedistributablePackage get2008X64Package() {
        return new MicrosoftVisualCppRedistributablePackage(
                MicrosoftVisualCppRedistributablePackage.Version.V2008,
                MicrosoftVisualCppRedistributablePackage.ProcessorArchitecture.X64,
                URI.create("https://download.microsoft.com/download/5/D/8/5D8C65CB-C849-4025-8E95-C3966CAFD8AE/vcredist_x64.exe"),
                new RegistryKeyExistsInstallationCheckHandler(
                        RegistryKey.HKEY_LOCAL_MACHINE.resolve("SOFTWARE\\Classes\\Installer\\Products\\67D6ECF5CD5FBA732B8B22BAC8DE1B4D")
                )
        );
    }

    static MicrosoftVisualCppRedistributablePackage get2008X86Package() {
        return new MicrosoftVisualCppRedistributablePackage(
                MicrosoftVisualCppRedistributablePackage.Version.V2008,
                MicrosoftVisualCppRedistributablePackage.ProcessorArchitecture.X86,
                URI.create("https://download.microsoft.com/download/8/B/4/8B42259F-5D70-43F4-AC2E-4B208FD8D66A/vcredist_x86.EXE"),
                new RegistryKeyExistsInstallationCheckHandler(
                        RegistryKey.HKEY_LOCAL_MACHINE.resolve("SOFTWARE\\Classes\\Installer\\Products\\6E815EB96CCE9A53884E7857C57002F0")
                )
        );
    }

    static MicrosoftVisualCppRedistributablePackage get2015to2022X64Package() {
        return new MicrosoftVisualCppRedistributablePackage(
                MicrosoftVisualCppRedistributablePackage.Version.V2015_2022,
                MicrosoftVisualCppRedistributablePackage.ProcessorArchitecture.X64,
                URI.create("https://aka.ms/vs/17/release/VC_redist.x64.exe"),
                new RegistryInstalledValueExistsInstallationCheckHandler(
                        RegistryKey.HKEY_LOCAL_MACHINE.resolve("SOFTWARE\\WOW6432Node\\Microsoft\\VisualStudio\\14.0\\VC\\Runtimes\\x64")
                )
        );
    }

    static MicrosoftVisualCppRedistributablePackage get2015to2022X86Package() {
        return new MicrosoftVisualCppRedistributablePackage(
                MicrosoftVisualCppRedistributablePackage.Version.V2015_2022,
                MicrosoftVisualCppRedistributablePackage.ProcessorArchitecture.X86,
                URI.create("https://aka.ms/vs/17/release/VC_redist.x86.exe"),
                new RegistryInstalledValueExistsInstallationCheckHandler(
                        RegistryKey.HKEY_LOCAL_MACHINE.resolve("SOFTWARE\\WOW6432Node\\Microsoft\\VisualStudio\\14.0\\VC\\Runtimes\\x86")
                )
        );
    }
}
