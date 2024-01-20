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

package com.github.bagulus.msvcrp4j.installer;


import com.github.bagulus.msvcrp4j.model.MicrosoftVisualCppRedistributable;
import java.io.IOException;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class MicrosoftVisualCppRedistributableInstaller implements Installer {

    private final MicrosoftVisualCppRedistributable redistributable;
    private final Path installationFilePath;


    public MicrosoftVisualCppRedistributableInstaller(
        MicrosoftVisualCppRedistributable redistributable, Path installationFilePath
    ) {
        this.redistributable = Objects.requireNonNull(redistributable);
        this.installationFilePath = Objects.requireNonNull(installationFilePath);
    }

    @Override
    public void install() throws InstallationFailedException {
        String[] command = Stream
            .concat(
                Arrays.stream(new String[]{String.valueOf(installationFilePath)}),
                Arrays.stream(redistributable.installationInfo().getInstallationParameters()))
            .toArray(String[]::new);
        try {
            Process installProcess = new ProcessBuilder()
                .command(command)
                .start();
            int exitValue = installProcess.waitFor();
            if (exitValue != 0) {
                throw new InstallationFailedException(MessageFormat.format(
                    "Install process ended with exit value {0}", exitValue)
                );
            }
        } catch (InterruptedException | IOException e) {
            throw new InstallationFailedException(e);
        }
    }
}
