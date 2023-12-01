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

import com.github.robtimus.os.windows.registry.RegistryException;
import com.github.robtimus.os.windows.registry.RegistryKey;

public class ProductNameRegistryVersionGetter extends StringVersionGetter {

    public ProductNameRegistryVersionGetter(RegistryKey registryKey) throws VersionCheckFailedException {
        super(getStringVersion(registryKey));
    }

    private static String getStringVersion(RegistryKey registryKey) throws VersionCheckFailedException {
        String productName;
        try {
            productName = registryKey.getStringValue("ProductName");
        } catch (RegistryException e) {
            throw new VersionCheckFailedException("Unable to parse version from product name", e);
        }

        String[] productNameTokens = productName.split(" ");
        int length = productNameTokens.length;
        if (length < 1) {
            throw new VersionCheckFailedException("Unable to parse version from product name, product name is empty");
        }
        return productNameTokens[length - 1];
    }
}
