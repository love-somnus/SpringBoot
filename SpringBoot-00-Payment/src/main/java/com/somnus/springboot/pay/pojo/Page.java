package com.somnus.springboot.pay.pojo;

import java.io.Serializable;

import lombok.Data;

@Data
public class Page implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long    total;
	
    private Integer pageNum  = 1;
    
    private Integer pageSize = 10;
    
    private String order;
    
    private boolean autoCount = true;

}
