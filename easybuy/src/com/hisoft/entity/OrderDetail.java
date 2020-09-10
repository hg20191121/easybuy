package com.hisoft.entity;

public class OrderDetail {
    //订单下绑定的产品链id
    private Integer id;
    //绑定的订单id
    private Integer ordetId;
    //订单下绑定的产品id
    private Integer productId;
    //产品数量
    private Integer quantity;
    //总花费
    private Float cost;
    //产品信息
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrdetId() {
        return ordetId;
    }

    public void setOrdetId(Integer ordetId) {
        this.ordetId = ordetId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }
    //构造方法

    public OrderDetail() {
    }

    public OrderDetail(Integer ordetId, Integer productId, Integer quantity, Float cost) {
        this.ordetId = ordetId;
        this.productId = productId;
        this.quantity = quantity;
        this.cost = cost;
    }

    public OrderDetail(Integer id, Integer ordetId, Integer productId, Integer quantity, Float cost) {
        this.id = id;
        this.ordetId = ordetId;
        this.productId = productId;
        this.quantity = quantity;
        this.cost = cost;
    }
}
