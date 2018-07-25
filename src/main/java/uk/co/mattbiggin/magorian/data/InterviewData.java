package uk.co.mattbiggin.magorian.data;

import uk.co.mattbiggin.magorian.model.Action;
import uk.co.mattbiggin.magorian.model.Interview;
import uk.co.mattbiggin.magorian.model.Officer;

import java.time.LocalDate;
import java.util.List;

public interface InterviewData {
    LocalDate getReportDate();

    List<Interview> readCurrentWeek(List<Officer> officers, List<Action> actions);
}
