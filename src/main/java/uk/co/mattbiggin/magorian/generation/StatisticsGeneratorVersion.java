package uk.co.mattbiggin.magorian.generation;

import com.google.common.collect.ImmutableMap;
import uk.co.mattbiggin.magorian.generation.v1.ForumBasedStatistics;

import java.util.EnumSet;
import java.util.Map;
import java.util.function.Supplier;

public enum StatisticsGeneratorVersion {
    VERSION_ONE("v1", ForumBasedStatistics::new);

    private static final Map<String, StatisticsGeneratorVersion> LOOKUP;

    static {
        ImmutableMap.Builder<String, StatisticsGeneratorVersion> builder = ImmutableMap.builder();
        for (StatisticsGeneratorVersion generator : EnumSet.allOf(StatisticsGeneratorVersion.class)) {
            builder.put(generator.getVersion(), generator);
        }
        LOOKUP = builder.build();
    }

    private final String version;
    private final Supplier<StatisticsGenerator> generator;

    StatisticsGeneratorVersion(String version, Supplier<StatisticsGenerator> generator) {
        this.version = version;
        this.generator = generator;
    }

    public static StatisticsGenerator getData(String version) {
        return LOOKUP.getOrDefault(version, VERSION_ONE).generator.get();
    }

    public String getVersion() {
        return version;
    }
}
