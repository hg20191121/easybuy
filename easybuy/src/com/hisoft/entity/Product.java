package com.hisoft.entity;

import java.io.Serializable;

/**
 * 产品实体累
 */
public class Product implements Serializable {

    //产品id
    private Integer p_id;
    //产品name
    private String p_name;
    //产品描述
    private String p_description;
    //产品价格
    private Float p_price;
    //产品库存
    private Integer p_stock;
    //所属一级分类id
    private Integer p_categoryLevel1;
    //所属二级分类id
    private Integer p_categoryLevel2;
    //所属三级分类id
    private Integer p_categoryLevel3;
    //产品的图片名
    private String p_fileName;
    //是否删除
    private boolean p_isDelete;

    @Override
    public String toString() {
        return "Product{" +
                "p_id=" + p_id +
                ", p_name='" + p_name + '\'' +
                ", p_description='" + p_description + '\'' +
                ", p_price=" + p_price +
                ", p_stock=" + p_stock +
                ", p_categoryLevel1=" + p_categoryLevel1 +
                ", p_categoryLevel2=" + p_categoryLevel2 +
                ", p_categoryLevel3=" + p_categoryLevel3 +
                ", p_fileName='" + p_fileName + '\'' +
                ", p_isDelete=" + p_isDelete +
                '}';
    }

    public Integer getP_id() {
        return p_id;
    }

    public void setP_id(Integer p_id) {
        this.p_id = p_id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_description() {
        return p_description;
    }

    public void setP_description(String p_description) {
        this.p_description = p_description;
    }

    public Float getP_price() {
        return p_price;
    }

    public void setP_price(Float p_price) {
        this.p_price = p_price;
    }

    public Integer getP_stock() {
        return p_stock;
    }

    public void setP_stock(Integer p_stock) {
        this.p_stock = p_stock;
    }

    public Integer getP_categoryLevel1() {
        return p_categoryLevel1;
    }

    public void setP_categoryLevel1(Integer p_categoryLevel1) {
        this.p_categoryLevel1 = p_categoryLevel1;
    }

    public Integer getP_categoryLevel2() {
        return p_categoryLevel2;
    }

    public void setP_categoryLevel2(Integer p_categoryLevel2) {
        this.p_categoryLevel2 = p_categoryLevel2;
    }

    public Integer getP_categoryLevel3() {
        return p_categoryLevel3;
    }

    public void setP_categoryLevel3(Integer p_categoryLevel3) {
        this.p_categoryLevel3 = p_categoryLevel3;
    }

    public String getP_fileName() {
        return p_fileName;
    }

    public void setP_fileName(String p_fileName) {
        this.p_fileName = p_fileName;
    }

    public boolean getP_isDelete() {
        return p_isDelete;
    }

    public void setP_isDelete(boolean p_isDelete) {
        this.p_isDelete = p_isDelete;
    }

    public Product() {
    }

    public Product(Integer p_id, String p_name, String p_description, Float p_price, Integer p_stock, Integer p_categoryLevel1, Integer p_categoryLevel2, Integer p_categoryLevel3, String p_fileName, boolean p_isDelete) {
        this.p_id = p_id;
        this.p_name = p_name;
        this.p_description = p_description;
        this.p_price = p_price;
        this.p_stock = p_stock;
        this.p_categoryLevel1 = p_categoryLevel1;
        this.p_categoryLevel2 = p_categoryLevel2;
        this.p_categoryLevel3 = p_categoryLevel3;
        this.p_fileName = p_fileName;
        this.p_isDelete = p_isDelete;
    }
}
