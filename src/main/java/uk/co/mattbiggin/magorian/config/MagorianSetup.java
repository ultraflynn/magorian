package uk.co.mattbiggin.magorian.config;

import uk.co.mattbiggin.magorian.config.model.Configuration;

public interface MagorianSetup {
    Configuration getConfig();

    boolean isVerbose();
}
