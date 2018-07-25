package uk.co.mattbiggin.magorian.version;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ResourceApplicationProperties implements ApplicationProperties {
    private final Properties properties;

    public ResourceApplicationProperties() {
        try (InputStream stream = getClass().getResourceAsStream("/magorian.properties")) {
            properties = new Properties();
            properties.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getName() {
        return properties.getProperty("magorian.name");
    }

    @Override
    public String getVersion() {
        return properties.getProperty("magorian.version");
    }
}
