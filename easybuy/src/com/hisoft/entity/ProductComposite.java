package com.hisoft.entity;

public class ProductComposite {
    //对应的商品
    Product product;
    //对应的数量
    Integer quantity;
    //对应的商品总价(单个商品,可能买了很多个)
    Float cost;

    public ProductComposite() {
    }

    public ProductComposite(Product product, Integer quantity, Float cost) {
        this.product = product;
        this.quantity = quantity;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "ProductComposite{" +
                "product=" + product +
                ", quantity=" + quantity +
                ", cost=" + cost +
                '}';
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
}

