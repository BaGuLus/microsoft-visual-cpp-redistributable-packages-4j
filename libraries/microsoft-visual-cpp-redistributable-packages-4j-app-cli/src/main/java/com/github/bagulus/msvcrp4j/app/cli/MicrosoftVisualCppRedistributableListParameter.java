package com.github.bagulus.msvcrp4j.app.cli;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;
import com.github.bagulus.msvcrp4j.model.MicrosoftVisualCppRedistributable;
import com.github.bagulus.msvcrp4j.model.ProcessorArchitecture;
import com.github.bagulus.msvcrp4j.model.Release;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

final class MicrosoftVisualCppRedistributableListParameter {

    private static final List<MicrosoftVisualCppRedistributable> MICROSOFT_VISUAL_CPP_REDISTRIBUTABLES_ALL = List.of(
        MicrosoftVisualCppRedistributable.X64_2005,
        MicrosoftVisualCppRedistributable.X86_2005,
        MicrosoftVisualCppRedistributable.X64_2008,
        MicrosoftVisualCppRedistributable.X86_2008,
        MicrosoftVisualCppRedistributable.X64_2010,
        MicrosoftVisualCppRedistributable.X86_2010,
        MicrosoftVisualCppRedistributable.X64_2012,
        MicrosoftVisualCppRedistributable.X86_2012,
        MicrosoftVisualCppRedistributable.X64_2013,
        MicrosoftVisualCppRedistributable.X86_2013,
        MicrosoftVisualCppRedistributable.X64_2015PLUS,
        MicrosoftVisualCppRedistributable.X86_2015PLUS
    );

    private MicrosoftVisualCppRedistributableListParameter() {
        throw new AssertionError("Class not instantiable");
    }

    public static class Converter implements IStringConverter<List<MicrosoftVisualCppRedistributable>> {

        @Override
        public List<MicrosoftVisualCppRedistributable> convert(String value) {
            if (value.equalsIgnoreCase("ALL")) {
                return MICROSOFT_VISUAL_CPP_REDISTRIBUTABLES_ALL;
            }
            String[] redistributables = value.split(",");
            List<MicrosoftVisualCppRedistributable> redistributablesList = new ArrayList<>();
            for (String redistributable : redistributables) {
                String[] tokens = parseToTokens(redistributable);
                Release release = releaseOfString(tokens[0]);
                ProcessorArchitecture processorArchitecture = processorArchitectureOfString(tokens[1]);
                redistributablesList.add(redistributableOfArchitectureAndRelease(processorArchitecture, release));
            }
            return new ArrayList<>(new LinkedHashSet<>(redistributablesList));
        }

        private static MicrosoftVisualCppRedistributable redistributableOfArchitectureAndRelease(
            ProcessorArchitecture processorArchitecture, Release release
        ) {
            String nameOfRedistributableStaticField = MessageFormat.format("{0}_{1}", processorArchitecture, release);
            try {
                return (MicrosoftVisualCppRedistributable) MicrosoftVisualCppRedistributable.class
                    .getDeclaredField(nameOfRedistributableStaticField)
                    .get(null);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                return null;
            }
        }
    }

    public static class Validator implements IParameterValidator {

        @Override
        public void validate(String name, String value) throws ParameterException {
            if (value.equalsIgnoreCase("ALL")) {
                return;
            }
            String[] redistributables = value.split(",");
            for (String redistributable : redistributables) {
                if (!denotesRedistributable(redistributable)) {
                    throw new ParameterException(
                        MessageFormat.format("Parameter {0} contains invalid value \"{1}\"", name, redistributable)
                    );
                }
            }
        }

        private boolean denotesRedistributable(String value) {
            String[] tokens = parseToTokens(value);
            if (tokens.length != 2) {
                return false;
            }
            Release release = releaseOfString(tokens[0]);
            if (release == null) {
                return false;
            }
            ProcessorArchitecture processorArchitecture = processorArchitectureOfString(tokens[1]);
            return processorArchitecture != null;
        }
    }

    private static String[] parseToTokens(String s) {
        String[] tokens;

        tokens = s.split("-");
        if (tokens.length == 2) {
            return tokens;
        }
        tokens = s.split("_");
        if (tokens.length == 2) {
            return tokens;
        }
        return new String[0];
    }

    private static Release releaseOfString(String s) {
        try {
            return Release.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private static ProcessorArchitecture processorArchitectureOfString(String s) {
        try {
            return ProcessorArchitecture.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
