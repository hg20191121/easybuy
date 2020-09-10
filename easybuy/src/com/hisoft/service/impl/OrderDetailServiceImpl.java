package com.hisoft.service.impl;

import com.hisoft.dao.OrderDetailDao;
import com.hisoft.dao.impl.OrderDetailDaoImpl;
import com.hisoft.entity.OrderDetail;
import com.hisoft.service.OrderDetailService;
import com.hisoft.util.JdbcConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailServiceImpl implements OrderDetailService {
    private ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
    private OrderDetailDao orderDetailDao = new OrderDetailDaoImpl(threadLocal);

    @Override
    public List<OrderDetail> queryAll() {
        return null;
    }

    @Override
    public OrderDetail queryOneByPrimaryKey(Integer integer) {
        return null;
    }

    @Override
    public Integer queryCount() {
        return null;
    }

    @Override
    public Integer insertOne(OrderDetail orderDetail) {
        Integer key = null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            //开始事务，默认提交设置为否
            threadLocal.get().setAutoCommit(false);
            key = orderDetailDao.insertOne(orderDetail);
            threadLocal.get().commit();
        } catch (SQLException e) {
            e.printStackTrace();
            //如果发生异常，就要进行回滚
            try {
                threadLocal.get().rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            JdbcConnectionUtil.close(threadLocal.get());
            threadLocal.remove();
        }
        return key;
    }

    @Override
    public Integer updateOne(OrderDetail orderDetail, Integer integer) {
        return null;
    }

    @Override
    public Integer remove(Integer integer) {
        return null;
    }
}
