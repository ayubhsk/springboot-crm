package com.whx.vo;

import com.whx.workbench.domain.Activity;

import java.util.List;

public class PageVo<T> {
    private List<T> list;
    private Integer total;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
