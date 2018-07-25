package uk.co.mattbiggin.magorian.model;

import java.util.Objects;

public class Officer {
    public final String name;

    public Officer(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Officer officer = (Officer) o;
        return Objects.equals(name, officer.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}
