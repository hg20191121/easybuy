package com.hisoft.dao.impl;

import com.hisoft.dao.OrderDetailDao;
import com.hisoft.entity.OrderDetail;
import com.hisoft.util.JdbcConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailDaoImpl extends ConnectionHolder implements OrderDetailDao {
    public OrderDetailDaoImpl(ThreadLocal<Connection> threadLocal) {
        super(threadLocal);
    }

    @Override
    public List<OrderDetail> queryAll() throws SQLException {
        return null;
    }

    @Override
    public OrderDetail queryOneByPrimaryKey(Integer integer) throws SQLException {
        return null;
    }

    @Override
    public Integer queryCount() throws SQLException {
        return null;
    }

    @Override
    public Integer insertOne(OrderDetail orderDetail) {
        Connection connection = this.threadLocal.get();
        PreparedStatement pstmt = null;
        Integer key = 0;
        try {
            pstmt = connection.prepareStatement("insert into easybuy_order_detail (orderId,productId,quantity,cost) values (?,?,?,?)");
            pstmt.setInt(1, orderDetail.getOrdetId());
            pstmt.setInt(2, orderDetail.getProductId());
            pstmt.setInt(3, orderDetail.getQuantity());
            pstmt.setFloat(4, orderDetail.getCost());
            key = pstmt.executeUpdate();



        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(pstmt, null, null);
        }
        return key;
    }

    @Override
    public Integer updateOne(OrderDetail orderDetail, Integer integer) {
        return null;
    }

    @Override
    public Integer remove(Integer integer) throws SQLException {
        return null;
    }
}
