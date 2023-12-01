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

package com.github.bagulus.msvcrj.model;

import java.util.regex.Pattern;

public record Version(
    int major,
    int minor,
    int build,
    int rbuild
) {
    public Version(int build) {
        this(-1, -1, build, -1);
    }

    /**
     * Matches values in format "{@code MAJOR.MINOR.BUILD}" or "{@code MAJOR.MINOR.BUILD.RBUILD}".
     */
    public static final Pattern REGEX = Pattern.compile("^(\\d+\\.){2}(\\d+)(\\.\\d+)?$");

    /**
     *
     */
    public static final Version ERROR_VERSION = new Version(-1);

    @Override
    public String toString() {
        return major + "." + minor + "." + build + "." + rbuild;
    }
}
