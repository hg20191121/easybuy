package com.hisoft.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单
 */
public class Order {

    //订单id
    private Integer o_id;
    //订单所属用户id
    private Integer o_userId;
    //订单所属用户的用户名
    private String o_loginName;
    //订单所属用户的地址
    private String o_userAddress;
    //订单创建时间
    private Date o_createTime;
    //订单金额
    private Float o_cost;
    //订单状态,1:待审核,2:审核通过,3:配货,4:卖家已发货,5:已收货
    private Integer o_status;
    //类型
    private Integer o_type;
    //订单号
    private String o_serialNumber;
    //订单下的产品列表
    private List<Product> o_productList = new ArrayList<>();
    //修改,致命错误
    private List<OrderDetail> details = new ArrayList<>();

    public List<OrderDetail> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetail> details) {
        this.details = details;
    }

    public List<Product> getO_productList() {
        return o_productList;
    }

    public void setO_productList(List<Product> o_productList) {
        this.o_productList = o_productList;
    }

    public Order(Integer o_id, Integer o_userId, String o_loginName, String o_userAddress, Date o_createTime, Float o_cost, Integer o_status, Integer o_type, String o_serialNumber, List<Product> o_productList) {
        this.o_id = o_id;
        this.o_userId = o_userId;
        this.o_loginName = o_loginName;
        this.o_userAddress = o_userAddress;
        this.o_createTime = o_createTime;
        this.o_cost = o_cost;
        this.o_status = o_status;
        this.o_type = o_type;
        this.o_serialNumber = o_serialNumber;
        this.o_productList = o_productList;
    }

    @Override
    public String toString() {
        return "Order{" +
                "o_id=" + o_id +
                ", o_userId=" + o_userId +
                ", o_loginName='" + o_loginName + '\'' +
                ", o_userAddress='" + o_userAddress + '\'' +
                ", o_createTime=" + o_createTime +
                ", o_cost=" + o_cost +
                ", o_status=" + o_status +
                ", o_type=" + o_type +
                ", o_serialNumber='" + o_serialNumber + '\'' +
                '}';
    }

    public Integer getO_id() {
        return o_id;
    }

    public void setO_id(Integer o_id) {
        this.o_id = o_id;
    }

    public Integer getO_userId() {
        return o_userId;
    }

    public void setO_userId(Integer o_userId) {
        this.o_userId = o_userId;
    }

    public String getO_loginName() {
        return o_loginName;
    }

    public void setO_loginName(String o_loginName) {
        this.o_loginName = o_loginName;
    }

    public String getO_userAddress() {
        return o_userAddress;
    }

    public void setO_userAddress(String o_userAddress) {
        this.o_userAddress = o_userAddress;
    }

    public Date getO_createTime() {
        return o_createTime;
    }

    public void setO_createTime(Date o_createTime) {
        this.o_createTime = o_createTime;
    }

    public Float getO_cost() {
        return o_cost;
    }

    public void setO_cost(Float o_cost) {
        this.o_cost = o_cost;
    }

    public Integer getO_status() {
        return o_status;
    }

    public void setO_status(Integer o_status) {
        this.o_status = o_status;
    }

    public Integer getO_type() {
        return o_type;
    }

    public void setO_type(Integer o_type) {
        this.o_type = o_type;
    }

    public String getO_serialNumber() {
        return o_serialNumber;
    }

    public void setO_serialNumber(String o_serialNumber) {
        this.o_serialNumber = o_serialNumber;
    }

    public Order() {
    }

    public Order(Integer o_id, Integer o_userId, String o_loginName, String o_userAddress, Date o_createTime, Float o_cost, Integer o_status, Integer o_type, String o_serialNumber) {
        this.o_id = o_id;
        this.o_userId = o_userId;
        this.o_loginName = o_loginName;
        this.o_userAddress = o_userAddress;
        this.o_createTime = o_createTime;
        this.o_cost = o_cost;
        this.o_status = o_status;
        this.o_type = o_type;
        this.o_serialNumber = o_serialNumber;
    }
}
