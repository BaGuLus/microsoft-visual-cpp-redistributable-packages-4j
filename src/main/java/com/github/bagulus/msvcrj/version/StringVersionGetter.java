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

package com.github.bagulus.msvcrj.version;

import com.github.bagulus.msvcrj.model.Version;

public class StringVersionGetter implements VersionGetter {

    private final Version version;

    public StringVersionGetter(String string) throws VersionCheckFailedException {
        if (matchesVersionRegex(string)) {
            throw new VersionCheckFailedException("Unable to parse version from string, it doesn't match valid regex");
        }
        this.version = parseVersion(string);
    }

    private static Version parseVersion(String string) throws VersionCheckFailedException {
        String[] versionTokens = getVersionTokens(string);
        boolean containsRBuild = versionTokens.length > 3; // no length range checks needed, done by the regex

        return new Version(
            parseSingleVersionToken(versionTokens[0]),
            parseSingleVersionToken(versionTokens[1]),
            parseSingleVersionToken(versionTokens[2]),
            parseSingleVersionToken(containsRBuild ? versionTokens[3] : "0")
        );
    }

    private static String[] getVersionTokens(String string) {
        return string.split("\\.");
    }

    private static int parseSingleVersionToken(String versionToken) throws VersionCheckFailedException {
        try {
            return Integer.parseInt(versionToken);
        } catch (NumberFormatException e) {
            throw new VersionCheckFailedException("Unable to parse version as number from string token", e);
        }
    }

    /**
     * Validates string against a version regex.
     *
     * @param string string to validate
     * @return true if string matches the version regex, false otherwise
     */
    private static boolean matchesVersionRegex(String string) {
        return Version.REGEX.matcher(string).matches();
    }

    @Override
    public Version getVersion() {
        return version;
    }
}
