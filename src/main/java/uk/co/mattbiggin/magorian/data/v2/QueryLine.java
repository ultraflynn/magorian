package uk.co.mattbiggin.magorian.data.v2;

import java.time.LocalDate;

public class QueryLine {
    final LocalDate date;
    final String applicant;
    final String officer;
    final String result;

    public QueryLine(LocalDate date, String applicant, String officer, String result) {
        this.date = date;
        this.applicant = applicant;
        this.officer = officer;
        this.result = result;
    }
}
