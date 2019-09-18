package com.somnus.springboot.mybatis.model;

import javax.persistence.*;

import lombok.Data;

@Table(name = "t_city")
@Data
public class TCity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "city_name")
    private String cityName;

    private String description;
}