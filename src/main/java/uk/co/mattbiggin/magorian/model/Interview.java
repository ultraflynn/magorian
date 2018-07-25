package uk.co.mattbiggin.magorian.model;

import java.time.LocalDate;

public final class Interview {
    public final LocalDate date;
    public final String applicant;
    public final String officer;
    public final String action;

    public Interview(LocalDate date, String applicant, String officer, String action) {
        this.date = date;
        this.applicant = applicant;
        this.officer = officer;
        this.action = action;
    }

    @Override
    public String toString() {
        return "\"" + date + "\",\"" + applicant + "\",\"" + officer + "\",\"" + action + "\"";
    }
}
