package com.github.bagulus.msvcrp4j.app.cli;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.text.MessageFormat;

final class PathParameter {

    private PathParameter() {
        throw new AssertionError("Class not instantiable");
    }

    public static class Converter implements IStringConverter<Path> {

        @Override
        public Path convert(String s) {
            return Path.of(s);
        }
    }

    public static class Validator implements IParameterValidator {

        @Override
        public void validate(String name, String value) throws ParameterException {
            try {
                Path.of(value);
            } catch (InvalidPathException e) {
                throw new ParameterException(
                    MessageFormat.format("Parameter {0} contains invalid path \"{1}\"", name, value)
                );
            }
        }
    }
}
