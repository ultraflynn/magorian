package uk.co.mattbiggin.magorian.config;

import uk.co.mattbiggin.magorian.model.Action;
import uk.co.mattbiggin.magorian.model.Officer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReportConfig implements ReportConfig {
    // Load the file into a list and trim any whitespace
    private static <T> List<T> readFile(String fileName, Function<String, T> generator) {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            return stream.map(String::trim).map(generator).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Officer> readOfficers(String type) {
        return readFile("config/" + type + "-officers.txt", Officer::new);
    }

    @Override
    public List<Action> readActions(String type) {
        // TODO Add validation when the config files are not | delimited
        return readFile("config/" + type + "-actions.txt", (String action) -> {
            String[] s = action.split("\\|");
            return new Action(s[0], s[1]);
        });
    }
}
