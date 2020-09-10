package com.hisoft.entity;

import java.util.List;

/**
 * Category实体类
 */
public class Category {

    //分类id
    private Integer c_id;
    //分类名
    private String c_name;
    //分类的父id
    private Integer c_parentId;
    //分类的类型,1:一级分类,2:二级分类,3:三级分类
    private Integer c_type;

    //下一级category的集合,最后一级为空
    private List<Category> c_children;

    //这个分类下的产品集合
    private List<Product> c_products;

    //icon-class
    private String c_iconClass;

    public String getC_iconClass() {
        return c_iconClass;
    }

    public void setC_iconClass(String c_iconClass) {
        this.c_iconClass = c_iconClass;
    }

    @Override
    public String toString() {
        return "Category{" +
                "c_id=" + c_id +
                ", c_name='" + c_name + '\'' +
                ", c_parentId=" + c_parentId +
                ", c_type=" + c_type +
                ", c_children=" + c_children +
                ", c_products=" + c_products +
                ", c_iconClass='" + c_iconClass + '\'' +
                '}';
    }

    public Category() {
    }

    public Category(Integer c_id, String c_name, Integer c_parentId, Integer c_type, List<Category> c_children, List<Product> c_products, String c_iconClass) {
        this.c_id = c_id;
        this.c_name = c_name;
        this.c_parentId = c_parentId;
        this.c_type = c_type;
        this.c_children = c_children;
        this.c_products = c_products;
        this.c_iconClass = c_iconClass;
    }

    public List<Category> getC_children() {
        return c_children;
    }

    public void setC_children(List<Category> c_children) {
        this.c_children = c_children;
    }

    public List<Product> getC_products() {
        return c_products;
    }

    public void setC_products(List<Product> c_products) {
        this.c_products = c_products;
    }

    public Category(Integer c_id, String c_name, Integer c_parentId, Integer c_type, List<Category> c_children, List<Product> c_products) {
        this.c_id = c_id;
        this.c_name = c_name;
        this.c_parentId = c_parentId;
        this.c_type = c_type;
        this.c_children = c_children;
        this.c_products = c_products;
    }

    public Integer getC_id() {
        return c_id;
    }

    public void setC_id(Integer c_id) {
        this.c_id = c_id;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public Integer getC_parentId() {
        return c_parentId;
    }

    public void setC_parentId(Integer c_parentId) {
        this.c_parentId = c_parentId;
    }

    public Integer getC_type() {
        return c_type;
    }

    public void setC_type(Integer c_type) {
        this.c_type = c_type;
    }
}
