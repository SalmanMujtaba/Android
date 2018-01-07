package com.example.sarveshrawat.finalterm;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by sarveshrawat on 12/9/17.
 */

public class Places implements Serializable {
    String name;
    Map<String, Double> location;
    String icon;
    String id;

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(Map<String, Double> location) {
        this.location = location;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Map<String, Double> getLocation() {
        return location;
    }

    public String getIcon() {
        return icon;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Places)) return false;

        Places places = (Places) o;

        if (getName() != null ? !getName().equals(places.getName()) : places.getName() != null)
            return false;
        if (getLocation() != null ? !getLocation().equals(places.getLocation()) : places.getLocation() != null)
            return false;
        if (getIcon() != null ? !getIcon().equals(places.getIcon()) : places.getIcon() != null)
            return false;
        return getId() != null ? getId().equals(places.getId()) : places.getId() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getLocation() != null ? getLocation().hashCode() : 0);
        result = 31 * result + (getIcon() != null ? getIcon().hashCode() : 0);
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        return result;
    }
}
