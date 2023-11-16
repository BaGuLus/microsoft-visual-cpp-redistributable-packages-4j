package com.github.bagulus.msvcrj.model;

import com.github.bagulus.msvcrj.install.InstallationCheckHandler;

import java.net.URI;
import java.text.MessageFormat;

public record MicrosoftVisualCppRedistributable(
        Version version,
        ProcessorArchitecture processorArchitecture,
        DownloadInfo downloadInfo,
        InstallationInfo installationInfo
) {
    public static final MicrosoftVisualCppRedistributable X64_2005
            = MicrosoftVisualCppRedistributableFactory.get2005X64Package();
    public static final MicrosoftVisualCppRedistributable X86_2005
            = MicrosoftVisualCppRedistributableFactory.get2005X86Package();
    public static final MicrosoftVisualCppRedistributable X64_2008
            = MicrosoftVisualCppRedistributableFactory.get2008X64Package();
    public static final MicrosoftVisualCppRedistributable X86_2008
            = MicrosoftVisualCppRedistributableFactory.get2008X86Package();
    public static final MicrosoftVisualCppRedistributable X64_2010
            = MicrosoftVisualCppRedistributableFactory.get2010X64Package();
    public static final MicrosoftVisualCppRedistributable X86_2010
            = MicrosoftVisualCppRedistributableFactory.get2010X86Package();
    public static final MicrosoftVisualCppRedistributable X64_2012
            = MicrosoftVisualCppRedistributableFactory.get2012X64Package();
    public static final MicrosoftVisualCppRedistributable X86_2012
            = MicrosoftVisualCppRedistributableFactory.get2012X86Package();
    public static final MicrosoftVisualCppRedistributable X64_2013
            = MicrosoftVisualCppRedistributableFactory.get2013X64Package();
    public static final MicrosoftVisualCppRedistributable X86_2013
            = MicrosoftVisualCppRedistributableFactory.get2013X86Package();
    public static final MicrosoftVisualCppRedistributable X64_2015TO2022
            = MicrosoftVisualCppRedistributableFactory.get2015to2022X64Package();
    public static final MicrosoftVisualCppRedistributable X86_2015TO2022
            = MicrosoftVisualCppRedistributableFactory.get2015to2022X86Package();

    @Override
    public String toString() {
        return MessageFormat.format(
                "Microsoft Visual C++ {0} {1} Redistributable Package", version, processorArchitecture
        );
    }

    public enum Version {
        V2005,
        V2008,
        V2010,
        V2012,
        V2013,
        V2015_2022;

        @Override
        public String toString() {
            return name().substring(1).replace("_", "-");
        }
    }

    public enum ProcessorArchitecture {
        X64,
        X86
    }

    public record DownloadInfo(
            URI downloadUri
    ) {
    }

    public record InstallationInfo(
            String[] installationParameters,
            InstallationCheckHandler installationCheckHandler
    ) {
        public static final String[] INSTALLATION_PARAMETERS_2005 = new String[] {"/Q"};
        public static final String[] INSTALLATION_PARAMETERS_2008 = new String[] {"/q"};
        public static final String[] INSTALLATION_PARAMETERS_2010 = new String[] {"/q", "/norestart"};
        public static final String[] INSTALLATION_PARAMETERS_2012_PLUS = new String[] {"/install", "/quiet", "/norestart"};
    }
}
