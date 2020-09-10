package com.hisoft.entity;

import javafx.util.converter.IntegerStringConverter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 购物车的实体,因为此项目购物车保存在session中,所以不会有数据库对应的表
 */
public class ShoppingCar {

    //购物车里的商品包装类
    private List<ProductComposite> items = new ArrayList<>();
    //购物车里的商品总价
    private Float sum;

    @Override
    public String toString() {
        return "ShoppingCar{" +
                "items=" + items +
                ", sum=" + sum +
                '}';
    }

    public ShoppingCar() {
    }

    public ShoppingCar(List<ProductComposite> items, Float sum) {
        this.items = items;
        this.sum = sum;
    }

    public List<ProductComposite> getItems() {
        return items;
    }

    public void setItems(List<ProductComposite> items) {
        this.items = items;
    }

    public Float getSum() {
        return sum;
    }

    public void setSum(Float sum) {
        this.sum = sum;
    }

    public void updateSum() {

//        Float collect = this.items.stream().collect(Collector.of((Supplier<ArrayList<Float>>) ArrayList::new,
//                (arr, composite) -> {
//                    arr.add(composite.cost);
//                },
//                (arr1, arr2) -> {
//                    arr1.addAll(arr2);
//                    return arr1;
//                },
//                arr -> arr.get(0),
//                Collector.Characteristics.UNORDERED));
        this.sum = (float)this.items.stream().mapToDouble((ProductComposite::getCost)).sum();
    }

}
