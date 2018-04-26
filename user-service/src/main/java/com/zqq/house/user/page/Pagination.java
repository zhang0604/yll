package com.zqq.house.user.page;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created By 张庆庆
 * DATA: 2018/4/20
 * TIME: 9:06
 */

public class Pagination {

    private Integer pageNum;
    private Integer pageSize;
    private Long totalCount;
    private List<Integer> pages = Lists.newArrayList();


    public Pagination (Integer pageNum,Integer pageSize,Long totalCount) {
        this.pageNum=pageNum;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        for (int i =1 ;i<=pageNum;i++){
            pages.add(i);
        }
        Long totalPage = totalCount/pageSize + (totalCount/pageSize == 0? 0 :1);
        if (totalPage>pageNum){
            for (int i=pageNum+1;i<=totalPage;i++){
                pages.add(i);
            }
        }
    }


    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }
}
