package com.zqq.house.user.page;

/**
 * Created By 张庆庆
 * DATA: 2018/4/20
 * TIME: 9:15
 */

public class PageParams {

    private static final Integer DEFAULT_PAGE_NUM = 1;
    private static final Integer DEFAULT_PAGE_SIZE = 2;
    private Integer pageNum;
    private Integer pageSize;
    private Integer offset;
    private Integer limit;

    public static PageParams build(Integer pageNum,Integer pageSize){
        PageParams pageParams = new PageParams();
        pageParams.setPageNum(pageNum);
        if (pageNum == null){
            pageParams.setPageNum(DEFAULT_PAGE_NUM);
        }
        pageParams.setPageSize(pageSize);
        if (pageSize == null){
            pageParams.setPageSize(DEFAULT_PAGE_SIZE);
        }
        pageParams.setOffset(pageParams.getPageSize());
        pageParams.setLimit(pageParams.getPageSize()*(pageParams.getPageNum()-1));
        return pageParams;
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

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
