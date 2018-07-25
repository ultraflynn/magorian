package uk.co.mattbiggin.magorian.data.v2;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.opencsv.CSVReader;
import uk.co.mattbiggin.magorian.DateFormats;
import uk.co.mattbiggin.magorian.config.MagorianSetup;
import uk.co.mattbiggin.magorian.data.DataFileReadException;
import uk.co.mattbiggin.magorian.data.InterviewData;
import uk.co.mattbiggin.magorian.data.MissingDataFileException;
import uk.co.mattbiggin.magorian.model.Action;
import uk.co.mattbiggin.magorian.model.Interview;
import uk.co.mattbiggin.magorian.model.Officer;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;

public class PortalCsvQueryResults implements InterviewData {
    private static final Logger LOGGER = Logger.getLogger(PortalCsvQueryResults.class.getName());

    private final boolean verbose;
    private final LocalDate reportDate;
    private final List<QueryLine> lines;

    public PortalCsvQueryResults(MagorianSetup setup) {
        this.verbose = setup.isVerbose();

        // TODO Check the file names to make sure they fall on the correct day of the week
        // TODO Remove this functionality from the constructor and test it

        final Pattern pattern = Pattern.compile("\\d+{8}\\.csv"); // TODO Why is this showing an error?
        final String dataDirectory = setup.getConfig().getDataDirectory();

        String date;
        try {
            try (Stream<Path> stream = Files.find(Paths.get(dataDirectory), 1, (p, a) -> {
                if (a.isRegularFile()) {
                    Matcher m = pattern.matcher(p.getFileName().toString());
                    return m.matches();
                }
                return false;
            }).sorted((path, other) -> path.compareTo(other) * -1)) {
                List<Path> files = ofNullable(stream.collect(Collectors.toList())).orElseThrow(() -> new MissingDataFileException("No data files found in " + dataDirectory));

                date = files.get(0).getFileName().toString().split("\\.")[0];
                reportDate = LocalDate.parse(date, DateFormats.PORTAL_FILE_NAME_FORMAT.formatter());
            }
        } catch (IOException e) {
            throw new DataFileReadException(e);
        }

        String filePath = dataDirectory + File.separator + date + ".csv";
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextLine;
            boolean ignoringFirst = true;
            ImmutableList.Builder<QueryLine> builder = ImmutableList.builder();
            while ((nextLine = reader.readNext()) != null) {
                if (ignoringFirst) {
                    // Ignore the header row
                    ignoringFirst = false;
                } else {
                    LocalDate d = LocalDate.parse(nextLine[1], DateFormats.PORTAL_RESULT_LINE_FORMAT.formatter());
                    String applicant = nextLine[5];
                    String officer = nextLine[3];
                    String action = nextLine[0];
                    builder.add(new QueryLine(d, applicant, officer, action));
                }
            }
            lines = builder.build();
        } catch (IOException e) {
            throw new CsvSyntaxError(e);
        }
    }

    @VisibleForTesting
    PortalCsvQueryResults(LocalDate reportDate, List<QueryLine> lines) {
        verbose = true;
        this.reportDate = reportDate;
        this.lines = lines;
    }

    @Override
    public LocalDate getReportDate() {
        return reportDate;
    }

    @Override
    public List<Interview> readCurrentWeek(List<Officer> officers, List<Action> actions) {
        List<String> actionNames = actions.stream().map(s -> s.name).collect(Collectors.toList());
        Map<String, String> mapping = actions.stream().collect(Collectors.toMap(o -> o.code, o -> o.name));

        return lines.stream()
                .map(l -> {
                    String result = ofNullable(mapping.get(l.result)).orElse("ignore");
                    return new Interview(l.date, l.applicant, l.officer, result);
                })
                .peek(interview -> {
                    if (verbose) {
                        LOGGER.info(interview.toString());
                    }
                })
                .filter(interview -> officers.contains(new Officer(interview.officer)))
                .filter(interview -> actionNames.contains(interview.action))
                .collect(Collectors.toList());
    }
}
