package uk.co.mattbiggin.magorian.data.v3;

import java.time.LocalDate;

public class QueryLine {
    private final LocalDate date;
    private final String applicant;
    private final String officer;
    private final String action;

    public QueryLine(LocalDate date, String applicant, String officer, String action) {
        this.date = date;
        this.applicant = applicant;
        this.officer = officer;
        this.action = action;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getApplicant() {
        return applicant;
    }

    public String getOfficer() {
        return officer;
    }

    public String getAction() {
        return action;
    }
}
