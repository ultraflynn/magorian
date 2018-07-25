package uk.co.mattbiggin.magorian.model;

import com.google.common.collect.ImmutableList;

import java.time.LocalDate;
import java.util.List;

public final class GeneratedOutput {
    private final List<String> output;
    private final LocalDate reportDate;

    private GeneratedOutput(List<String> output, LocalDate reportDate) {
        this.output = output;
        this.reportDate = reportDate;
    }

    public static GeneratedOutput from(List<String> output, LocalDate reportDate) {
        return new GeneratedOutput(output, reportDate);
    }

    public List<String> getOutput() {
        return ImmutableList.copyOf(output);
    }

    public LocalDate getReportDate() {
        return reportDate;
    }
}
