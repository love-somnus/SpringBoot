package com.somnus.springboot.pay.pojo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class QueryResult<T> {

    private List<T> list;
    private Page    page;

    public QueryResult() {
        page = new Page();
        list = new ArrayList<T>();
    }

    public QueryResult(List<T> list, Page page) {
        super();
        this.list = list;
        this.page = page;
    }
}
