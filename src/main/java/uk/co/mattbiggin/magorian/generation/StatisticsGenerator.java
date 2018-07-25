package uk.co.mattbiggin.magorian.generation;

import uk.co.mattbiggin.magorian.config.ReportConfig;
import uk.co.mattbiggin.magorian.config.model.Report;
import uk.co.mattbiggin.magorian.data.InterviewData;
import uk.co.mattbiggin.magorian.model.GeneratedOutput;

public interface StatisticsGenerator {
    GeneratedOutput generateStats(Report report, InterviewData interviewData, ReportConfig reportConfig);
}
