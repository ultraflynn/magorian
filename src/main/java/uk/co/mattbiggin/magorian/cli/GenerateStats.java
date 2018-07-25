package uk.co.mattbiggin.magorian.cli;

import picocli.CommandLine;
import uk.co.mattbiggin.magorian.config.FileReportConfig;
import uk.co.mattbiggin.magorian.config.MagorianSetup;
import uk.co.mattbiggin.magorian.config.ReportConfig;
import uk.co.mattbiggin.magorian.config.json.JsonSetup;
import uk.co.mattbiggin.magorian.data.InterviewData;
import uk.co.mattbiggin.magorian.data.InterviewDataVersion;
import uk.co.mattbiggin.magorian.generation.StatisticsGenerator;
import uk.co.mattbiggin.magorian.generation.StatisticsGeneratorVersion;
import uk.co.mattbiggin.magorian.output.ConsoleOutputWriter;
import uk.co.mattbiggin.magorian.output.FileOutputWriter;
import uk.co.mattbiggin.magorian.output.MultipleOutputter;
import uk.co.mattbiggin.magorian.output.OutputWriter;

import java.util.concurrent.Callable;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Option;

@Command(description = "Generate the weekly PO and OO stats", name = "stats",
        mixinStandardHelpOptions = true)
public final class GenerateStats implements Callable<Void> {
    @Option(names = {"-c", "--config"}, description = "Configuration file")
    private String configFileName = "config/config-tool.json";

    @Option(names = {"-v", "--verbose"}, description = "Verbose")
    private boolean verbose = false;

    public static void main(String[] args) {
        // CheckSum implements Callable, so parsing, error handling and handling user
        // requests for usage help or version help can be done with one line of code.
        CommandLine.call(new GenerateStats(), args);
    }

    @Override
    public Void call() {
        // TODO Enable verbose mode (going to be useful for supporting DI using this)
        // TODO Generate the right named jar file
        // TODO Generate the deploy directory
        // TODO Generate "run" files

        MagorianSetup setup = new JsonSetup(configFileName, verbose);

        InterviewData interviewData = InterviewDataVersion.getData(setup.getConfig().getSource(), setup);

        // We need to read certain files that contain report config
        ReportConfig reportConfig = new FileReportConfig();

        // Will take the interview data and turn it into the statistics
        StatisticsGenerator generator = StatisticsGeneratorVersion.getData(setup.getConfig().getStatistics());

        // Get a multiple outputter, armed with console and file outputters.
        OutputWriter writer = new MultipleOutputter(new ConsoleOutputWriter(), new FileOutputWriter());

        // Generate the stats for each type and write to the output(s)
        setup.getConfig().getReports().forEach(r -> writer.write(r.getType(), generator.generateStats(r, interviewData, reportConfig)));

        return null;
    }
}
