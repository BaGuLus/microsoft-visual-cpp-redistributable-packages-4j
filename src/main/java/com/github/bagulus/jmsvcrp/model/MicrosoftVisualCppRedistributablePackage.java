package com.github.bagulus.jmsvcrp.model;

import com.github.bagulus.jmsvcrp.installation.InstallationCheckHandler;

import java.net.URI;
import java.text.MessageFormat;

public class MicrosoftVisualCppRedistributablePackage {
    public static final MicrosoftVisualCppRedistributablePackage X64_2008
            = MicrosoftVisualCppRedistributablePackageFactory.get2008X64Package();
    public static final MicrosoftVisualCppRedistributablePackage X86_2008
            = MicrosoftVisualCppRedistributablePackageFactory.get2008X86Package();
    public static final MicrosoftVisualCppRedistributablePackage X64_2015TO2022
            = MicrosoftVisualCppRedistributablePackageFactory.get2015to2022X64Package();
    public static final MicrosoftVisualCppRedistributablePackage X86_2015TO2022
            = MicrosoftVisualCppRedistributablePackageFactory.get2015to2022X86Package();

    private final String name;
    private final URI downloadUri;
    private final InstallationCheckHandler installationCheckHandler;

    MicrosoftVisualCppRedistributablePackage(
            Version version,
            ProcessorArchitecture processorArchitecture,
            URI downloadUri,
            InstallationCheckHandler installationCheckHandler
    ) {
        this.name = MessageFormat.format(
                "Microsoft Visual C++ {0} {1} Redistributable Package", version, processorArchitecture
        );
        this.downloadUri = downloadUri;
        this.installationCheckHandler = installationCheckHandler;
    }

    public String getName() {
        return name;
    }

    public URI getDownloadUri() {
        return downloadUri;
    }

    public boolean isInstalled() {
        return installationCheckHandler.isInstalled();
    }

    enum Version {
        V2008,
        V2015_2022;

        @Override
        public String toString() {
            return name().substring(1).replace("_", "-");
        }
    }

    enum ProcessorArchitecture {
        X64,
        X86
    }
}
