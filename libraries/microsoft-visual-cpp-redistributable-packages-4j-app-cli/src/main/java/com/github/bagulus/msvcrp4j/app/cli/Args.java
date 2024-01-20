package com.github.bagulus.msvcrp4j.app.cli;

import com.beust.jcommander.IParametersValidator;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import com.github.bagulus.msvcrp4j.app.cli.Args.DownloadAndDeployAreMutualExclusive;
import com.github.bagulus.msvcrp4j.app.cli.Args.InstallAndDeployAreMutualExclusive;
import com.github.bagulus.msvcrp4j.model.MicrosoftVisualCppRedistributable;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Parameters(parametersValidators = {
    DownloadAndDeployAreMutualExclusive.class,
    InstallAndDeployAreMutualExclusive.class
})
public final class Args {

    @Parameter(
        names = {"--download", "-d"},
        arity = 1,
        listConverter = MicrosoftVisualCppRedistributableListParameter.Converter.class,
        validateWith = MicrosoftVisualCppRedistributableListParameter.Validator.class,
        description = "Comma-separated list of packages to download. Package is denoted as YEAR-ARCHITECTURE or "
            + "YEAR_ARCHITECTURE, where YEAR is value [Y2005|Y2008|Y2010|Y2012|Y2013|Y2015PLUS], and ARCHITECTURE is value [X64|X86] (e.g. Y2010-X64,Y2015+_X86). "
            + "To download every package, add argument ALL (--download ALL or -d ALL). "
            + "This parameter can't be used if [--deploy | -y] parameter is used.")
    List<MicrosoftVisualCppRedistributable> redistributablesToDownload = null;

    @Parameter(
        names = {"--install", "-i"},
        arity = 1,
        listConverter = MicrosoftVisualCppRedistributableListParameter.Converter.class,
        validateWith = MicrosoftVisualCppRedistributableListParameter.Validator.class,
        description = "Comma-separated list of packages to install. Package is denoted as YEAR-ARCHITECTURE or "
            + "YEAR_ARCHITECTURE, where YEAR is value [Y2005|Y2008|Y2010|Y2012|Y2013|Y2015PLUS], and ARCHITECTURE is value [X64|X86] (e.g. Y2010-X64,Y2015+_X86). "
            + "To (attempt to) install every package, add argument ALL (--install ALL or -i ALL). "
            + "This parameter can't be used if [--deploy | -y] parameter is used.")
    List<MicrosoftVisualCppRedistributable> redistributablesToInstall = null;

    @Parameter(
        names = {"--deploy", "-y"},
        arity = 1,
        listConverter = MicrosoftVisualCppRedistributableListParameter.Converter.class,
        validateWith = MicrosoftVisualCppRedistributableListParameter.Validator.class,
        description =
            "Comma-separated list of packages to deploy (download and install). Package is denoted as YEAR-ARCHITECTURE or "
                + "YEAR_ARCHITECTURE, where YEAR is value [Y2005|Y2008|Y2010|Y2012|Y2013|Y2015PLUS], and ARCHITECTURE is value [X64|X86] (e.g. Y2010-X64,Y2015+_X86). "
                + "To (attempt to) deploy (download and install) every package, add argument ALL (--deploy ALL or -y ALL). "
                + "This parameter can't be used if [--download | -d] or [--install | -i] parameter is used.")
    List<MicrosoftVisualCppRedistributable> redistributablesToDeploy = null;

    @Parameter(
        names = {"--workingDirectory", "-w"},
        arity = 1,
        converter = PathParameter.Converter.class,
        validateWith = PathParameter.Validator.class,
        description = "Path to a directory, where the files will be downloaded and/or from where they will be installed.")
    Path workingDirectory = Path.of(System.getProperty("user.home"), "Downloads", "vcredist-temp");

    @Parameter(
        names = {"--quiet", "-q"},
        description = "When provided as a parameter, nothing is printed to standard output.")
    boolean quietMode = false;

    @Parameter(
        names = {"--cleanup", "-c"},
        arity = 1,
        description = "If true, removes the working directory at the end. When provided as a parameter, its value must be provided explicitly."
    )
    boolean cleanup = true;


    public static class DownloadAndDeployAreMutualExclusive implements IParametersValidator {

        @Override
        public void validate(Map<String, Object> parameters) throws ParameterException {
            boolean downloadParameterSet = parameters.get("--download") != null || parameters.get("-d") != null;
            boolean deployParameterSet = parameters.get("--deploy") != null || parameters.get("-y") != null;
            if (downloadParameterSet && deployParameterSet) {
                throw new ParameterException(
                    "Parameters --download [-d] and --deploy [-y] cannot be used at the same time. "
                        + "If you want to download and install separate packages, use --download [-d] and install [-i] together,"
                        + "or use --deploy [-y] if you want to download and install the same packages.");
            }
        }
    }

    public static class InstallAndDeployAreMutualExclusive implements IParametersValidator {

        @Override
        public void validate(Map<String, Object> parameters) throws ParameterException {
            boolean installParameterSet = parameters.get("--install") != null || parameters.get("-i") != null;
            boolean deployParameterSet = parameters.get("--deploy") != null || parameters.get("-y") != null;
            if (installParameterSet && deployParameterSet) {
                throw new ParameterException(
                    "Parameters --install [-i] and --deploy [-y] cannot be used at the same time. "
                        + "If you want to download and install separate packages, use --download [-d] and install [-i] together,"
                        + "or use --deploy [-y] if you want to download and install the same packages.");
            }
        }
    }
}
