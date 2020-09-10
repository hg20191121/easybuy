package com.hisoft.dao.impl;

import com.alibaba.fastjson.support.odps.udf.CodecCheck;
import com.hisoft.dao.OrderDao;
import com.hisoft.entity.News;
import com.hisoft.entity.Order;
import com.hisoft.entity.OrderDetail;
import com.hisoft.util.JdbcConnectionUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class OrderDaoImpl extends ConnectionHolder implements OrderDao {

    public OrderDaoImpl(ThreadLocal<Connection> threadLocal) {

        super(threadLocal);
    }

    /*    实现BaseDao的部分开始,注释请查看BaseDao    */

    @Override
    public Integer remove(Integer integer) throws SQLException {
        Connection connection = this.threadLocal.get();
        PreparedStatement pstat = null;
        String sql = null;
        Integer key = null;
        ResultSet rs = null;
        sql = "delete from easybuy_order where id=?";
        pstat = connection.prepareStatement(sql);
        pstat.setInt(1, integer);
        key = pstat.executeUpdate();

        JdbcConnectionUtil.close(pstat, rs, null);
        return key;
    }

    @Override
    public List<Order> queryAll() throws SQLException {
        Connection connection = this.threadLocal.get();
        PreparedStatement pstmt = connection.prepareStatement("select id,userId,loginName,userAddress,createTime,cost,serialNumber from easybuy_order;");
        ResultSet rs = pstmt.executeQuery();
        List<Order> orderList = new ArrayList<>();
        while (rs.next()) {
            Order order = new Order();
            order.setO_id(rs.getInt("id"));
            order.setO_userId(rs.getInt("userId"));
            order.setO_loginName(rs.getString("loginName"));
            order.setO_userAddress(rs.getString("userAddress"));
            order.setO_createTime(rs.getDate("createTime"));
            order.setO_cost(rs.getFloat("cost"));
            order.setO_serialNumber(rs.getString("serialNumber"));
            orderList.add(order);
            order.getO_productList().addAll(new ProductDaoImpl(threadLocal).queryProductsByOrderId(order.getO_id()));
        }
        JdbcConnectionUtil.close(pstmt, rs, null);
        return orderList;
    }

    @Override
    public Order queryOneByPrimaryKey(Integer integer) throws SQLException {
        Connection connection = this.threadLocal.get();
        PreparedStatement pstmt = connection.prepareStatement("select userId,loginName,userAddress,createTime,cost,serailNumber from easybuy_order where id=? ");
        pstmt.setInt(1, integer);
        ResultSet rs = pstmt.executeQuery();
        Order order = new Order();
        if (rs.next()) {
            order.setO_id(rs.getInt("id"));
            order.setO_userId(rs.getInt("userId"));
            order.setO_loginName(rs.getString("loginName"));
            order.setO_userAddress(rs.getString("userAddress"));
            order.setO_createTime(rs.getDate("createTime"));
            order.setO_cost(rs.getFloat("cost"));
            order.setO_serialNumber(rs.getString("serialNumber"));
            order.getO_productList().addAll(new ProductDaoImpl(threadLocal).queryProductsByOrderId(order.getO_id()));
        }
        JdbcConnectionUtil.close(pstmt, rs, null);
        return order;
    }

    @Override
    public Integer queryCount() throws SQLException {
        Connection connection = this.threadLocal.get();
        PreparedStatement pstmt = connection.prepareStatement("select count(1) num from easybuy_order");
        ResultSet rs = pstmt.executeQuery();
        Integer num = 0;
        if (rs.next()) {
            num = rs.getInt("num");
        }
        JdbcConnectionUtil.close(pstmt, rs, null);
        return num;
    }

    @Override
    public Integer insertOne(Order order) {
        Connection connection = this.threadLocal.get();
        PreparedStatement pstmt = null;
        Integer id = null;

        try {
            pstmt = connection.prepareStatement("insert into easybuy_order (userId,loginName,userAddress,createTime,cost,serialNumber)values(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, order.getO_userId());
            pstmt.setString(2, order.getO_loginName());
            pstmt.setObject(3, order.getO_userAddress());
            pstmt.setObject(4, order.getO_createTime());
            pstmt.setFloat(5, order.getO_cost());
            pstmt.setString(6, order.getO_serialNumber());
            Logger logger = Logger.getLogger(OrderDaoImpl.class);
            id = pstmt.executeUpdate();
            if (id != 0) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                generatedKeys.next();
                id = generatedKeys.getInt(1);
//                logger.info("orderID:"+id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(pstmt, null, null);
        }
        return id;
    }

    @Override
    public Integer updateOne(Order order, Integer integer) {
        Connection connection = this.threadLocal.get();
        PreparedStatement pstmt = null;
        Integer key = 0;
        try {
            pstmt = connection.prepareStatement("Update easybuy_order set userId=(?),loginName=(?),userAddress=(?),createTime=(?),cost=(?),serialNumber=(?) where id=(?)");
            pstmt.setInt(1, order.getO_userId());
            pstmt.setString(2, order.getO_loginName());
            pstmt.setString(3, order.getO_userAddress());
            pstmt.setObject(4, order.getO_createTime());
            pstmt.setFloat(5, order.getO_cost());
            pstmt.setString(6, order.getO_serialNumber());
            pstmt.setInt(7, order.getO_id());
            key = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(pstmt, null, null);
        }

        return key;
    }

    /*    实现BaseDao的部分结束,注释请查看BaseDao    */



    /*     实现OrderDao部分开始,注释请查看OrderDao      */

    /**
     * 分页查询
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public List<Order> queryOrdersByPage(Integer currentPage, Integer pageSize) {
        Connection connection = this.threadLocal.get();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Order> orderList = new ArrayList<>();
        try {
            pstmt = connection.prepareStatement("select id,userId,loginName,userAddress,createTime,cost,serialNumber from easybuy_order limit ?,?;");
            pstmt.setInt(1, (currentPage - 1) * pageSize);
            pstmt.setInt(2, pageSize);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setO_id(rs.getInt("id"));
                order.setO_userId(rs.getInt("userId"));
                order.setO_loginName(rs.getString("loginName"));
                order.setO_userAddress(rs.getString("userAddress"));
                order.setO_createTime(rs.getDate("createTime"));
                order.setO_cost(rs.getFloat("cost"));
                order.setO_serialNumber(rs.getString("serialNumber"));
                order.setDetails(queryOrderDetailByOrderId(order.getO_id()));
                orderList.add(order);
//                order.getO_productList().addAll(new ProductDaoImpl(threadLocal).queryProductsByOrderId(order.getO_id()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(pstmt, rs, null);
        }
        return orderList;
    }

    //!!
    @Override
    public List<Order> queryOrderListByUserId(Integer userId) {
        Connection connection = this.threadLocal.get();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Order> orderList = new ArrayList<>();
        try {
            pstmt = connection.prepareStatement("select id,userId,loginName,userAddress,createTime,cost,serialNumber from easybuy_order where userId=?");
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setO_id(rs.getInt("id"));
                order.setO_userId(rs.getInt("userId"));
                order.setO_loginName(rs.getString("loginName"));
                order.setO_userAddress(rs.getString("userAddress"));
                order.setO_createTime(rs.getDate("createTime"));
                order.setO_cost(rs.getFloat("cost"));
                order.setO_serialNumber(rs.getString("serialNumber"));

                order.setDetails(queryOrderDetailByUserId(order.getO_id()));
                orderList.add(order);
//                order.getO_productList().addAll(new ProductDaoImpl(threadLocal).queryProductsByOrderId(order.getO_id()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(pstmt, rs, null);
        }
        return orderList;
    }


    @Override
    public List<OrderDetail> queryOrderDetailByOrderId(Integer orderId) {
        Connection connection = this.threadLocal.get();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<OrderDetail> orderList = new ArrayList<>();
        try {
            pstmt = connection.prepareStatement("select * from easybuy_order_detail where orderId=?");
            pstmt.setInt(1, orderId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setCost(rs.getFloat("cost"));
                orderDetail.setOrdetId(rs.getInt("orderId"));
                orderDetail.setProductId(rs.getInt("productId"));
                orderDetail.setQuantity(rs.getInt("quantity"));
                orderDetail.setProduct(new ProductDaoImpl(threadLocal).queryOneByPrimaryKey(orderDetail.getProductId()));
                orderList.add(orderDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(pstmt, rs, null);
        }
        return orderList;
    }


    public List<OrderDetail> queryOrderDetailByUserId(Integer userId) {
        Connection connection = this.threadLocal.get();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<OrderDetail> orderList = new ArrayList<>();
        try {

            pstmt = connection.prepareStatement("select id,orderId,productId,quantity,cost from easybuy_order_detail  where orderId = ?");
//            pstmt = connection.prepareStatement("select eod.id,eod.orderId,eod.productId,eod.quantity,eod.cost from easybuy_order_detail eod," +
//                    "(select id from easybuy_order where userId = ?) od where eod.orderId = od.id");
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setCost(rs.getFloat("cost"));
                orderDetail.setOrdetId(rs.getInt("orderId"));
                orderDetail.setProductId(rs.getInt("productId"));
                orderDetail.setQuantity(rs.getInt("quantity"));
                orderDetail.setProduct(new ProductDaoImpl(threadLocal).queryOneByPrimaryKey(orderDetail.getProductId()));
                orderList.add(orderDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(pstmt, rs, null);
        }
        return orderList;
    }

    /*     实现OrderDao部分结束,注释请查看OrderDao      */

}
