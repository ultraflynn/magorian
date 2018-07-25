package uk.co.mattbiggin.magorian.output;

import uk.co.mattbiggin.magorian.DateFormats;
import uk.co.mattbiggin.magorian.model.GeneratedOutput;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public final class FileOutputWriter implements OutputWriter {
    @Override
    public void write(String type, GeneratedOutput generatedOutput) {
        LocalDate reportDate = generatedOutput.getReportDate();
        List<String> output = generatedOutput.getOutput();

        try {
            String resultFileName = "reports/" + reportDate.plusDays(6)
                    .format(DateFormats.RESULTS_FILE_NAME_FORMAT.formatter()) + ("-" + type + ".txt");

            Files.write(Paths.get(resultFileName), output.stream().collect(Collectors.joining("\n")).getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
