package uk.co.mattbiggin.magorian.generation.v1;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.co.mattbiggin.magorian.config.ReportConfig;
import uk.co.mattbiggin.magorian.config.model.Report;
import uk.co.mattbiggin.magorian.data.InterviewData;
import uk.co.mattbiggin.magorian.model.Action;
import uk.co.mattbiggin.magorian.model.GeneratedOutput;
import uk.co.mattbiggin.magorian.model.Interview;
import uk.co.mattbiggin.magorian.model.Officer;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ForumBasedStatisticsTest {
    private static final List<Officer> OFFICERS = ImmutableList.of(new Officer("OFFICER"));
    private static final List<Action> ACTIONS = ImmutableList.of(new Action("CODE", "ACTION"));
    private static final LocalDate REPORT_DATE = LocalDate.of(2018, 4, 16);
    private static final LocalDate INTERVIEW_DATE = LocalDate.now();

    @Mock
    private Report report;

    @Mock
    private InterviewData interviewData;

    @Mock
    private ReportConfig reportConfig;

    @Before
    public void setUp() {
        when(report.getType()).thenReturn("TYPE");
        when(interviewData.getReportDate()).thenReturn(REPORT_DATE);
        List<Interview> interviews = ImmutableList.of(new Interview(INTERVIEW_DATE, "APPLICANT", "OFFICER", "ACTION"));
        when(interviewData.readCurrentWeek(OFFICERS, ACTIONS)).thenReturn(interviews);
        when(reportConfig.readOfficers("TYPE")).thenReturn(OFFICERS);
        when(reportConfig.readActions("TYPE")).thenReturn(ACTIONS);
    }

    @Test
    public void shouldProduceSkeletonStats() {
        ForumBasedStatistics statistics = new ForumBasedStatistics();
        GeneratedOutput output = statistics.generateStats(report, interviewData, reportConfig);

        assertThat(output.getReportDate(), is(LocalDate.of(2018, 4, 16)));
    }
}