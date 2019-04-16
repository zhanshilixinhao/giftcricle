package com.chouchong.common;

/**
 * 分页返回序列化对象
 *
 * @author yichenshanren
 * @date 2017/9/28
 */

public class PageResponse<T> extends ResponseImpl<T> {

    private long total;
    private int pages;
    private int pageNum;
    private int pageSize;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public PageResponse() {
    }

    protected PageResponse(int errCode, T data, long total, int pages, int pageNum, int pageSize) {
        super(errCode, data);
        this.total = total;
        this.pages = pages;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    protected PageResponse(int errCode, T data, long total, int pages, int pageNum, int pageSize, String imgHost) {
        super(errCode, data, imgHost);
        this.total = total;
        this.pages = pages;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
