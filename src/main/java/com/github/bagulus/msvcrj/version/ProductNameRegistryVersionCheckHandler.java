package com.github.bagulus.msvcrj.version;

import com.github.robtimus.os.windows.registry.RegistryException;
import com.github.robtimus.os.windows.registry.RegistryKey;

public class ProductNameRegistryVersionCheckHandler extends StringVersionCheckHandler {

    public ProductNameRegistryVersionCheckHandler(RegistryKey registryKey) throws VersionCheckFailedException {
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
