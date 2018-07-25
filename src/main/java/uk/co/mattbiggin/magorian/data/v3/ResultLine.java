package uk.co.mattbiggin.magorian.data.v3;

public final class ResultLine {
    private String action;
    private String actedOn;
    private long officerid;
    private String officername;
    private long applicantid;
    private String applicantname;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActedOn() {
        return actedOn;
    }

    public void setActedOn(String actedOn) {
        this.actedOn = actedOn;
    }

    public long getOfficerid() {
        return officerid;
    }

    public void setOfficerid(long officerid) {
        this.officerid = officerid;
    }

    public String getOfficername() {
        return officername;
    }

    public void setOfficername(String officername) {
        this.officername = officername;
    }

    public long getApplicantid() {
        return applicantid;
    }

    public void setApplicantid(long applicantid) {
        this.applicantid = applicantid;
    }

    public String getApplicantname() {
        return applicantname;
    }

    public void setApplicantname(String applicantname) {
        this.applicantname = applicantname;
    }
}
