package com.hisoft.service;

import com.hisoft.entity.Category;
import com.hisoft.entity.Order;
import com.hisoft.entity.OrderDetail;

import java.util.List;

public interface OrderService extends BaseService<Order,Integer> {

    default List<Order> queryOrderListByUserId(Integer userId) {
        return null;
    }

    /**
     * 分页查询order列表
     * @param currentPage
     * @param pageSize
     * @return
     */
    default List<Order> queryOrdersByPage(Integer currentPage,Integer pageSize) {
        return null;
    }

    List<OrderDetail> queryOrderDetailByOrderId(Integer iOrderId);

    List<OrderDetail> queryOrderDetailByUserId(Integer userId);
}
