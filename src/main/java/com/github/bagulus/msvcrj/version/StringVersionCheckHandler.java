package com.github.bagulus.msvcrj.version;

public class StringVersionCheckHandler implements VersionCheckHandler {
    private final int major;
    private final int minor;
    private final int build;
    private final int rbuild;

    public StringVersionCheckHandler(String string) throws VersionCheckFailedException {
        if (matchesVersionRegex(string)) {
            throw new VersionCheckFailedException("Unable to parse version from string, it doesn't match valid regex");
        }
        String[] versionTokens = getVersionTokens(string);
        boolean containsRBuild = versionTokens.length > 3; // no length range checks needed, done by the regex

        this.major = parseSingleVersionToken(versionTokens[0]);
        this.minor = parseSingleVersionToken(versionTokens[1]);
        this.build = parseSingleVersionToken(versionTokens[2]);
        this.rbuild = parseSingleVersionToken(containsRBuild ? versionTokens[3] : "0");
    }

    protected static String[] getVersionTokens(String string) {
        return string.split("\\.");
    }

    protected static int parseSingleVersionToken(String versionToken) throws VersionCheckFailedException {
        try {
            return Integer.parseInt(versionToken);
        } catch (NumberFormatException e) {
            throw new VersionCheckFailedException("Unable to parse version as number from string token", e);
        }
    }

    /**
     * Validates string against a version regex. It matches strings in format "{@code MAJOR.MINOR.BUILD}" or
     * "{@code MAJOR.MINOR.BUILD.RBUILD}".
     *
     * @param string string to validate
     * @return true if string matches the version regex, false otherwise
     */
    protected static boolean matchesVersionRegex(String string) {
        return string.matches("^(\\d+\\.){2}(\\d+)(\\.\\d+)?$");
    }

    @Override
    public int getMajor() {
        return major;
    }

    @Override
    public int getMinor() {
        return minor;
    }

    @Override
    public int getBuild() {
        return build;
    }

    @Override
    public int getRBuild() {
        return rbuild;
    }

    @Override
    public String getVersion() {
        return major + "." + minor + "." + build + "." + rbuild;
    }
}
