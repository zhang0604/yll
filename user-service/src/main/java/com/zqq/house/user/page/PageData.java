package com.zqq.house.user.page;


import java.util.List;


public class PageData<T> {

    private List<T> list;
    private Pagination pagination;

    public PageData(Pagination pagination,List<T> list){
        this.pagination = pagination;
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

}
