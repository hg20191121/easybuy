package com.hisoft.dao;

import com.hisoft.entity.Category;
import com.hisoft.entity.Order;
import com.hisoft.entity.OrderDetail;

import java.util.List;

public interface OrderDao extends BaseDao<Order,Integer> {

    /**
     * 分页查询order列表
     * @param currentPage
     * @param pageSize
     * @return
     */
    default List<Order> queryOrdersByPage(Integer currentPage, Integer pageSize) {
        return null;
    }

    default List<Order> queryOrderListByUserId(Integer userId) {
        return null;
    }


    List<OrderDetail> queryOrderDetailByOrderId(Integer orderId);


    List<OrderDetail> queryOrderDetailByUserId(Integer userId);
}
