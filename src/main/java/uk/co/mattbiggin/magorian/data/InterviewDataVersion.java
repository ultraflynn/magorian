package uk.co.mattbiggin.magorian.data;

import com.google.common.collect.ImmutableMap;
import uk.co.mattbiggin.magorian.config.MagorianSetup;
import uk.co.mattbiggin.magorian.data.v1.UniToolsHtmlData;
import uk.co.mattbiggin.magorian.data.v2.PortalCsvQueryResults;
import uk.co.mattbiggin.magorian.data.v3.PortalJsonResults;

import java.util.EnumSet;
import java.util.Map;
import java.util.function.Function;

public enum InterviewDataVersion {
    UNI_TOOLS("unitools", UniToolsHtmlData::new),
    PORTAL_QUERY_RESULTS("portalqueryresults", PortalCsvQueryResults::new),
    PORTAL_JSON_RESULTS("portaljsonresults", PortalJsonResults::new);

    private static final Map<String, InterviewDataVersion> LOOKUP;

    static {
        ImmutableMap.Builder<String, InterviewDataVersion> builder = ImmutableMap.builder();
        for (InterviewDataVersion data : EnumSet.allOf(InterviewDataVersion.class)) {
            builder.put(data.getVersion(), data);
        }
        LOOKUP = builder.build();
    }

    private final String version;
    private final Function<MagorianSetup, InterviewData> data;

    InterviewDataVersion(String version, Function<MagorianSetup, InterviewData> data) {
        this.version = version;
        this.data = data;
    }

    public static InterviewData getData(String version, MagorianSetup config) {
        return LOOKUP.getOrDefault(version, PORTAL_QUERY_RESULTS).data.apply(config);
    }

    public String getVersion() {
        return version;
    }
}
