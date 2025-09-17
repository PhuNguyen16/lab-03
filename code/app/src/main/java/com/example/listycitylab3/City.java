package com.example.listycitylab3;

// City.java
import java.io.Serializable;

public class City implements Serializable {
    private String name;
    private String province;

    public City(String name, String province) {
        this.name = name;
        this.province = province;
    }

    public City() { }

    public String getName() {
        return name;
    }
    public String getProvince() {
        return province;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setProvince(String province) {
        this.province = province;
    }
}

