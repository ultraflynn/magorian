package uk.co.mattbiggin.magorian;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public enum DateFormats {
    PORTAL_FILE_NAME_FORMAT("yyyyMMdd"),
    PORTAL_RESULT_LINE_FORMAT("yyyy-MM-dd HH:mm:ss"),
    HTML_HEADING_FORMAT("d MMMM yyyy"),
    REPORT_HEADER_FORMAT("dd/MM/yyyy"),
    RESULTS_FILE_NAME_FORMAT("yyyy-MM-dd"),
    UNITOOLS_RESULT_LINE_FORMAT("MMM d',' yyyy"),
    REPORT_TOTAL_PER_DAY_FORMAT("EEEE"),
    REPORT_ACTION_PER_DAY_FORMAT("E");

    private final DateTimeFormatter formatter;

    DateFormats(String pattern) {
        this.formatter = DateTimeFormatter.ofPattern(pattern).withLocale(Locale.UK);
    }

    public DateTimeFormatter formatter() {
        return formatter;
    }
}
