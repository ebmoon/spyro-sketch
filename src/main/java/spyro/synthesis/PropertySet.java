package spyro.synthesis;

import sketch.compiler.ast.core.Function;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PropertySet {
    public final static String conjunctionID = "property_conj";

    List<Property> properties;

    public PropertySet() {
        this.properties = new ArrayList<>();
    }

    public PropertySet(List<Property> properties) {
        this.properties = new ArrayList<>(properties);
    }

    public List<Property> getProperties() { return properties; }

    public void add(Property phi) { properties.add(phi); }
    public void addAll(Collection<Property> phis) { properties.addAll(phis); }

    public List<Function> toSketchCode() {
        return null;
    }
}
