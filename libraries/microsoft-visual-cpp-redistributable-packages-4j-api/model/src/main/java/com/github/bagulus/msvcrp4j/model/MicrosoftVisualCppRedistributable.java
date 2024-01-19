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

import java.text.MessageFormat;

public record MicrosoftVisualCppRedistributable(
    Release release,
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
            "Microsoft Visual C++ {0} {1} Redistributable Package", release, processorArchitecture
        );
    }
}
