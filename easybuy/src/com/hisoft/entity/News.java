package com.hisoft.entity;

import java.util.Date;

/**
 * News实体类
 */
public class News {


    //id
    private Integer n_Id;
    //新闻的标题
    private String n_Title;
    //新闻的内容
    private String n_Content;
    //新闻录入的日期
    private Date n_Date;




    public News(Integer n_Id, String n_Title, String n_Content, Date n_Date) {
        this.n_Id = n_Id;
        this.n_Title = n_Title;
        this.n_Content = n_Content;
        this.n_Date = n_Date;
    }

    public News() {

    }

    @Override
    public String toString() {
        return "News{" +
                "n_Id=" + n_Id +
                ", n_Title='" + n_Title + '\'' +
                ", n_Content='" + n_Content + '\'' +
                ", n_Date=" + n_Date +
                '}';
    }

    public Integer getN_Id() {
        return n_Id;
    }

    public void setN_Id(Integer n_Id) {
        this.n_Id = n_Id;
    }

    public String getN_Title() {
        return n_Title;
    }

    public void setN_Title(String n_Title) {
        this.n_Title = n_Title;
    }

    public String getN_Content() {
        return n_Content;
    }

    public void setN_Content(String n_Content) {
        this.n_Content = n_Content;
    }

    public Date getN_Date() {
        return n_Date;
    }

    public void setN_Date(Date n_Date) {
        this.n_Date = n_Date;
    }
}
