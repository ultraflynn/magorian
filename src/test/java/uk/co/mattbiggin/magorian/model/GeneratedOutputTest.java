package uk.co.mattbiggin.magorian.model;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GeneratedOutputTest {
    private static final List<String> OUTPUT = ImmutableList.of("a", "b", "c");
    private static final LocalDate REPORT_DATE = LocalDate.of(2018, 4, 16);

    @Test
    public void shouldConvertDateCorrectly() {
        GeneratedOutput output = GeneratedOutput.from(OUTPUT, REPORT_DATE);

        assertThat(output.getOutput().size(), is(3));
        assertThat(output.getReportDate(), is(LocalDate.of(2018, 4, 16)));
    }
}