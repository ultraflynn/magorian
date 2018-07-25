package uk.co.mattbiggin.magorian.generation.v1;

import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;
import uk.co.mattbiggin.magorian.model.Interview;

import java.time.LocalDate;
import java.util.Map;

import static com.google.common.collect.ImmutableList.copyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BreakdownByDayTest {
    private static final LocalDate NOW = LocalDate.now();
    private static final LocalDate THEN = LocalDate.now().minusDays(1);

    @Before
    public void setUp() {
    }

    @Test
    public void shouldBreakdownSingleInterview() {
        Interview firstInterview = new Interview(NOW, "APPLICANT", "OFFICER", "ACTION");

        Map<LocalDate, Integer> n = analyse(firstInterview);

        assertThat(n.get(NOW), is(1));
    }

    @Test
    public void shouldBreakdownTwoInterviewsOnDifferentDays() {
        Interview firstInterview = new Interview(NOW, "APPLICANT", "OFFICER", "ACTION");
        Interview secondInterview = new Interview(THEN, "APPLICANT", "OFFICER", "ACTION");

        Map<LocalDate, Integer> n = analyse(firstInterview, secondInterview);

        assertThat(n.get(NOW), is(1));
        assertThat(n.get(THEN), is(1));
    }

    @Test
    public void shouldBreakdownTwoInterviewsOnSameDay() {
        Interview firstInterview = new Interview(NOW, "APPLICANT", "OFFICER", "ACTION");
        Interview secondInterview = new Interview(NOW, "APPLICANT", "OFFICER", "ACTION");

        Map<LocalDate, Integer> n = analyse(firstInterview, secondInterview);

        assertThat(n.get(NOW), is(2));
    }

    private Map<LocalDate, Integer> analyse(Interview... interviews) {
        BreakdownByDay breakdown = new BreakdownByDay();

        Map<LocalDate, Integer> n = Maps.newHashMap();

        breakdown.analyse(copyOf(interviews), r -> {
            n.put(r.getDate(), r.getCount());
            return "RESULT";
        });
        return n;
    }
}