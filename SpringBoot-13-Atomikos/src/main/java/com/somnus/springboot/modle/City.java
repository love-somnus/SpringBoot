package com.somnus.springboot.modle;

import lombok.Data;

@Data
public class City {
    private Integer id;

    private String cityName;

    private String description;

    public City() {
        super();
    }
    
    public City(String cityName, String description) {
        super();
        this.cityName = cityName;
        this.description = description;
    }
}