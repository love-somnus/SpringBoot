package com.somnus.springboot.mybatis.model;

import javax.persistence.*;

@Table(name = "t_log")
public class TLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String ext;

    public TLog() {
        super();
    }
    
    public TLog(String ext) {
        super();
        this.ext = ext;
    }

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return ext
     */
    public String getExt() {
        return ext;
    }

    /**
     * @param ext
     */
    public void setExt(String ext) {
        this.ext = ext;
    }
}