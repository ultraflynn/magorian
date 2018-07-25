package uk.co.mattbiggin.magorian.config.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.mattbiggin.magorian.config.MagorianSetup;
import uk.co.mattbiggin.magorian.config.model.Configuration;

import java.io.File;
import java.io.IOException;

public final class JsonSetup implements MagorianSetup {
    private final boolean verbose;
    private final Configuration config;

    public JsonSetup(String filename, boolean verbose) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            config = mapper.readValue(new File(filename), Configuration.class);
            this.verbose = verbose;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Configuration getConfig() {
        return config;
    }

    @Override
    public boolean isVerbose() {
        return verbose;
    }
}
