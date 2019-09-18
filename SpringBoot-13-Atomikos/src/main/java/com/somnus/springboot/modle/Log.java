package com.somnus.springboot.modle;

import lombok.Data;

@Data
public class Log {
    private Integer id;

    private String ext;

    public Log() {
        super();
    }
    
    public Log(String ext) {
        super();
        this.ext = ext;
    }
}