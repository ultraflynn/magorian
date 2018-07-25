package uk.co.mattbiggin.magorian.data.v1;

import com.google.common.annotations.VisibleForTesting;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import uk.co.mattbiggin.magorian.DateFormats;
import uk.co.mattbiggin.magorian.config.MagorianSetup;
import uk.co.mattbiggin.magorian.data.InterviewData;
import uk.co.mattbiggin.magorian.model.Action;
import uk.co.mattbiggin.magorian.model.Interview;
import uk.co.mattbiggin.magorian.model.Officer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class UniToolsHtmlData implements InterviewData {
    private static final int LENGTH_OF_TIME = " HH:MM:SS".length();

    private final Document document;
    private final boolean verbose;

    public UniToolsHtmlData(MagorianSetup setup) {
        this.verbose = setup.isVerbose();

        String htmlFileLocation = setup.getConfig().getDataDirectory() + File.separator + setup.getConfig().getFileName();
        try {
            // From the HTML source, get me a Jsoup document that I can interpret as HTML
            File input = new File(htmlFileLocation);
            document = Jsoup.parse(input, Charset.defaultCharset().displayName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @VisibleForTesting
    UniToolsHtmlData(Document document) {
        verbose = true;
        this.document = document;
    }

    @Override
    public LocalDate getReportDate() {
        String rawDate = Arrays.stream(document.select("p").first().text()
                .split(" "))
                .skip(3)
                .collect(Collectors.joining(" "));
        return LocalDate.parse(rawDate.replaceFirst("^(\\d+).*? (\\w+ \\d+)", "$1 $2"), DateFormats.HTML_HEADING_FORMAT.formatter());
    }

    @Override
    public List<Interview> readCurrentWeek(List<Officer> officers, List<Action> actions) {
        List<String> actionNames = actions.stream().map(s -> s.name).collect(Collectors.toList());
        return document.select("div#actionList").first().select("div.tr").stream()
                .filter(element -> !element.hasClass("headers"))
                .map(action -> {
                    String rawInterviewDate = action.select("div.date").text();
                    LocalDate interviewDate = LocalDate.parse(rawInterviewDate.substring(0, rawInterviewDate.length() - LENGTH_OF_TIME), DateFormats.UNITOOLS_RESULT_LINE_FORMAT.formatter());
                    String applicant = action.select("div.vChar").text().trim();
                    String officer = action.select("div.aChar").text().trim();
                    String result = action.select("div.action").text().trim();

                    return new Interview(interviewDate, applicant, officer, result);
                })
                .peek(interview -> {
                    if (verbose) {
                        System.out.println(interview.toString());
                    }
                })
                .filter(interview -> officers.contains(new Officer(interview.officer)))
                .filter(interview -> actionNames.contains(interview.action))
                .collect(Collectors.toList());
    }
}
