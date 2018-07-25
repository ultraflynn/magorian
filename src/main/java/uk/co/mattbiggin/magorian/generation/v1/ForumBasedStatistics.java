package uk.co.mattbiggin.magorian.generation.v1;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import uk.co.mattbiggin.magorian.DateFormats;
import uk.co.mattbiggin.magorian.config.ReportConfig;
import uk.co.mattbiggin.magorian.config.model.Report;
import uk.co.mattbiggin.magorian.config.model.Section;
import uk.co.mattbiggin.magorian.data.InterviewData;
import uk.co.mattbiggin.magorian.generation.StatisticsGenerator;
import uk.co.mattbiggin.magorian.model.Action;
import uk.co.mattbiggin.magorian.model.GeneratedOutput;
import uk.co.mattbiggin.magorian.model.Interview;
import uk.co.mattbiggin.magorian.model.Officer;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ForumBasedStatistics implements StatisticsGenerator {
    private static void addSection(Section section, List<Officer> officers, List<Interview> interviews, List<String> output) {
        output.add("");
        output.add("==========================" + section.getName() + "=================================");
        output.add("");

        officers.forEach(officer -> {
            Map<String, Long> grouped = interviews.stream().filter(interview -> {
                if (section.getFilter().length() > 0) {
                    return interview.action.equals(section.getFilter());
                } else {
                    return true;
                }
            }).collect(Collectors.groupingBy(o -> o.officer, Collectors.counting()));
            output.add(String.valueOf(Optional.ofNullable(grouped.get(officer.name)).orElse(0L)));
        });
    }

    private static List<Officer> loadRequiredOfficers(ReportConfig reportConfig, String type, List<String> output) {
        List<Officer> officers = reportConfig.readOfficers(type);

        output.add("");
        output.add("======================LOADED OFFICERS================================");
        output.add("");
        output.add(officers.stream().map(o -> o.name).collect(Collectors.joining("\n")));
        return officers;
    }

    private static List<Action> loadRequiredActions(ReportConfig reportConfig, String type, List<String> output) {
        List<Action> actions = reportConfig.readActions(type);

        output.add("");
        output.add("======================LOADED ACTIONS================================");
        output.add("");
        output.add(actions.stream().map(o -> o.name).collect(Collectors.joining("\n")));
        return actions;
    }

    private static void totalPerDay(List<Interview> interviews, List<String> output) {
        Map<LocalDate, List<Interview>> grouped = interviews.stream().collect(Collectors.groupingBy(o -> o.date));
        List<LocalDate> dates = new ArrayList<>(grouped.keySet());
        Collections.sort(dates);

        dates.forEach(d -> {
            int n = grouped.get(d).size();
            output.add("[*] " + n + " on " + d.format(DateFormats.REPORT_TOTAL_PER_DAY_FORMAT.formatter()));
        });
    }

    private static String totalNumberOfInterviews(List<Interview> interviews) {
        return String.valueOf(interviews.size());
    }

    private static void totalPerAction(List<Action> actions, List<Interview> interviews, List<String> output) {
        Map<String, List<Interview>> grouped = interviews.stream().collect(Collectors.groupingBy(o -> o.action));

        actions.forEach(a -> {
            List<Interview> selected = Optional.ofNullable(grouped.get(a.name)).orElse(ImmutableList.of());
            int n = selected.size();
            if (n > 0) {
                List<String> counts = new BreakdownByDay()
                        .analyse(selected, s -> s.getDate().format(DateFormats.REPORT_ACTION_PER_DAY_FORMAT.formatter()) + " " + s.getCount());

                String breakdown = counts.stream().collect(Collectors.joining(", "));

                output.add("[*] " + n + " were " + a.name + " [" + breakdown + "]");
            }
        });
    }

    private static void summaryPerOfficer(List<Interview> interviews, List<String> output) {
        Map<String, Long> grouped = interviews.stream().collect(Collectors.groupingBy(o -> o.officer, Collectors.counting()));
        grouped.entrySet().stream()
                .sorted((o1, o2) -> o2.getValue().intValue() - o1.getValue().intValue())
                .forEach(e -> output.add(e.getValue() + " " + e.getKey()));
    }

    private static void detailPerOfficer(List<Interview> interviews, List<Action> actions, List<String> output) {
        Map<String, Long> order = interviews.stream().collect(Collectors.groupingBy(o -> o.officer, Collectors.counting()));
        Map<String, List<Interview>> grouped = interviews.stream().collect(Collectors.groupingBy(o -> o.officer));

        order.entrySet().stream()
                .sorted((o1, o2) -> o2.getValue().intValue() - o1.getValue().intValue())
                .forEach(e -> detailPerSingleOfficer(e.getKey(), grouped.get(e.getKey()), actions, output));
    }

    private static void detailPerSingleOfficer(String officer, List<Interview> interviews, List<Action> actions, List<String> output) {
        Map<String, List<Interview>> grouped = interviews.stream().collect(Collectors.groupingBy(o -> o.action));

        output.add(officer + " completed " + interviews.size() + " interviews");
        actions.forEach(a -> {
            int n = Optional.ofNullable(grouped.get(a.name)).orElse(ImmutableList.of()).size();
            if (n > 0) {
                output.add(n + " " + a.name);
            }
        });
        output.add("");
    }

    private static void yearly(List<Interview> interviews, List<Action> requiredActions, List<String> output) {
        output.add("");
        output.add("=============================YEARLY================================");
        output.add("");

        Map<String, List<Interview>> grouped = interviews.stream().collect(Collectors.groupingBy(o -> o.action));
        requiredActions.forEach(action -> {
            int n = Optional.ofNullable(grouped.get(action.name)).orElse(ImmutableList.of()).size();
            output.add(String.valueOf(n));
        });

    }

    private static void reportDate(LocalDate reportDate, List<String> output) {
        String date = reportDate.plusDays(6)
                .format(DateFormats.REPORT_HEADER_FORMAT.formatter());
        output.add("[size=120]Report as of " + date + "[/size]");

    }

    @Override
    public GeneratedOutput generateStats(Report report, InterviewData interviewData, ReportConfig reportConfig) {
        List<String> output = Lists.newArrayList();
        String type = report.getType();

        output.add("");
        output.add("++++++++");
        output.add("++ " + type.toUpperCase() + " ++");
        output.add("++++++++");

        List<Officer> requiredOfficers = loadRequiredOfficers(reportConfig, type, output);
        List<Action> requiredActions = loadRequiredActions(reportConfig, type, output);

        output.add("");
        output.add("=======================FORUM POST===================================");
        output.add("");

        LocalDate reportDate = interviewData.getReportDate();

        reportDate(reportDate, output);

        List<Interview> interviews = interviewData.readCurrentWeek(requiredOfficers, requiredActions);

        output.add("[quote]");
        output.add("[size=120]Daily Summary[/size]");
        output.add("[list]");
        totalPerDay(interviews, output);
        output.add("[/list][/quote]");
        output.add("[quote][size=120][color=#80FF00]" + totalNumberOfInterviews(interviews) + "[/color] interviews completed this week[/size][list]");
        totalPerAction(requiredActions, interviews, output);
        output.add("[/list][/quote]");
        output.add("");
        output.add("[log]Summary by Officer");
        summaryPerOfficer(interviews, output);
        output.add("[/log]");
        output.add("");
        output.add("[Spoiler]");
        detailPerOfficer(interviews, requiredActions, output);
        output.add("[/Spoiler]");

        report.getSections().forEach(s -> addSection(s, requiredOfficers, interviews, output));

        yearly(interviews, requiredActions, output);

        return GeneratedOutput.from(output, reportDate);
    }
}
