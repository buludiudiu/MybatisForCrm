package com.bjpowernode.crm.vo;

import java.util.List;

public class PaginationVO<T> {
    private int total;
    private List<T> list ;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
