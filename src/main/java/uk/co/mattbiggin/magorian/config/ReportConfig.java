package uk.co.mattbiggin.magorian.config;

import uk.co.mattbiggin.magorian.model.Action;
import uk.co.mattbiggin.magorian.model.Officer;

import java.util.List;

public interface ReportConfig {
    List<Officer> readOfficers(String type);

    List<Action> readActions(String type);
}
