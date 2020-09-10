package com.hisoft.util;

import java.util.List;

public class PageHelper {

    //当前请求的url
    private String url;
    //当前页数
    private Integer currentPage;
    //总页数
    private Integer pageCount;

    //默认的一页大小
    public static final Integer PAGE_SIZE = 8;

    @Override
    public String toString() {
        return "PageHelper{" +
                "url='" + url + '\'' +
                ", currentPage=" + currentPage +
                ", pageCount=" + pageCount +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public PageHelper(String url, Integer currentPage, Integer pageCount) {
        this.url = url;
        this.currentPage = currentPage;
        this.pageCount = pageCount;
    }

    public PageHelper() {
    }


    public static PageHelper createPageHelper(String url,Integer totalItemsCount,Integer currentPage) {
        PageHelper pageHelper = new PageHelper();
        pageHelper.setUrl(url);
        pageHelper.setCurrentPage(currentPage);
        pageHelper.setPageCount(totalItemsCount%PAGE_SIZE == 0?totalItemsCount/PAGE_SIZE:totalItemsCount/PAGE_SIZE+1);
        return pageHelper;
    }
}
