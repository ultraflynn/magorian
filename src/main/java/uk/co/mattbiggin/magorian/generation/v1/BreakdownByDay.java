package uk.co.mattbiggin.magorian.generation.v1;

import uk.co.mattbiggin.magorian.model.Interview;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

class BreakdownByDay {
    /**
     * Break down the interviews by the number performed per day.
     *
     * @param interviews The interviews for a week
     * @param formatter  Provides formatting
     * @return A String containing the formatted breakdown by day
     */
    List<String> analyse(List<Interview> interviews, Function<InterviewCount, String> formatter) {
        return interviews.stream()
                .collect(Collectors.groupingBy(o -> o.date))
                .entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .map(e -> new InterviewCount(e.getKey(), e.getValue().size()))
                .map(formatter)
                .collect(Collectors.toList());
    }

    public static class InterviewCount {
        private final LocalDate date;
        private final int count;

        InterviewCount(LocalDate date, int count) {
            this.date = date;
            this.count = count;
        }

        public LocalDate getDate() {
            return date;
        }

        int getCount() {
            return count;
        }
    }
}
