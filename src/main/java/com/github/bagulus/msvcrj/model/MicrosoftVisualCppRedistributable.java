package com.github.bagulus.msvcrj.model;

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
    public static final MicrosoftVisualCppRedistributable X64_2015PLUS
        = MicrosoftVisualCppRedistributableFactory.get2015PlusX64Package();
    public static final MicrosoftVisualCppRedistributable X86_2015PLUS
        = MicrosoftVisualCppRedistributableFactory.get2015PlusX86Package();

    @Override
    public String toString() {
        return MessageFormat.format(
            "Microsoft Visual C++ {0} {1} Redistributable Package", version, processorArchitecture
        );
    }
}
