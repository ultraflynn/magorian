package uk.co.mattbiggin.magorian.config.model;

import java.util.List;

public class Report {
    private String type;
    private List<Section> sections;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }
}
