package uk.co.mattbiggin.magorian.model;

import java.util.Objects;

public class Action {
    public final String code;
    public final String name;

    public Action(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Action action = (Action) o;
        return Objects.equals(code, action.code) &&
                Objects.equals(name, action.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(code, name);
    }
}
