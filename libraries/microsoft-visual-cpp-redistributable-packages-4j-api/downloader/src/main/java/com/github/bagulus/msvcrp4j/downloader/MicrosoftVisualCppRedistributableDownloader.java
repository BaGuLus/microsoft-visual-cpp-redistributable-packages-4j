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

package com.github.bagulus.msvcrp4j.downloader;

import com.github.bagulus.msvcrp4j.model.MicrosoftVisualCppRedistributable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class MicrosoftVisualCppRedistributableDownloader implements Downloader {

    private final MicrosoftVisualCppRedistributable redistributable;
    private final Path downloadFilePath;

    public MicrosoftVisualCppRedistributableDownloader(
        MicrosoftVisualCppRedistributable redistributable, Path downloadFilePath
    ) {
        this.redistributable = Objects.requireNonNull(redistributable);
        this.downloadFilePath = Objects.requireNonNull(downloadFilePath);
    }

    @Override
    public long download() throws DownloadFailedException {
        try (
            InputStream urlStream = redistributable.downloadInfo().downloadUri().toURL().openStream();
            ReadableByteChannel readableByteChannel = Channels.newChannel(urlStream);
            FileOutputStream fileOutputStream = new FileOutputStream(
                Files.createFile(downloadFilePath).toFile())
        ) {
            FileChannel fileChannel = fileOutputStream.getChannel();
            return fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            throw new DownloadFailedException(e);
        }
    }
}
