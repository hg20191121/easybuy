package com.hisoft.service.impl;

import com.hisoft.dao.OrderDao;
import com.hisoft.dao.impl.OrderDaoImpl;
import com.hisoft.entity.Order;
import com.hisoft.entity.OrderDetail;
import com.hisoft.entity.User;
import com.hisoft.service.OrderService;
import com.hisoft.util.JdbcConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class OrderServiceImpl implements OrderService {

    private ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    private OrderDao orderDao = new OrderDaoImpl(threadLocal);


    /*   实现了BaseService开始,具体注释请查看BaseService    */

    @Override
    public Integer remove(Integer integer) {
        Integer rem=null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            rem=orderDao.remove(integer);
            //return orderDao.remove(integer);
            threadLocal.get().commit();
        } catch (SQLException e) {
            try {
                threadLocal.get().rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            JdbcConnectionUtil.close(threadLocal.get());
            threadLocal.remove();
        }
        return rem;
    }

    @Override
    public List<Order> queryAll() {
        List<Order> all = null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            all = orderDao.queryAll();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(threadLocal.get());
            threadLocal.remove();
        }
        return all;
    }

    @Override
    public Order queryOneByPrimaryKey(Integer integer) {
        Order order = null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            order = orderDao.queryOneByPrimaryKey(integer);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(threadLocal.get());
            threadLocal.remove();
        }
        return order;
    }

    @Override
    public Integer queryCount() {
        Integer integer = null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            integer = orderDao.queryCount();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(threadLocal.get());
            threadLocal.remove();
        }
        return integer;
    }

    @Override
    public Integer insertOne(Order order) {
        Integer key = null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            //开始事务，默认提交设置为否
            threadLocal.get().setAutoCommit(false);
            key = orderDao.insertOne(order);
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
    public Integer updateOne(Order order, Integer integer) {
        Integer key = null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            //开始事务，默认提交设置为否
            threadLocal.get().setAutoCommit(false);
            key = orderDao.updateOne(order,integer);
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


    /*   实现了BaseService结束,具体注释请查看BaseService    */


    /*   实现了OrderService开始,具体注释请查看OrderService  */

    @Override
    public List<Order> queryOrderListByUserId(Integer userId) {
        List<Order> orders = new ArrayList<>();
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            orders = orderDao.queryOrderListByUserId(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcConnectionUtil.close(threadLocal.get());
        }
        return orders;
    }

    @Override
    public List<Order> queryOrdersByPage(Integer currentPage, Integer pageSize) {
        List<Order> orders = new ArrayList<>();
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            orders = orderDao.queryOrdersByPage(currentPage,pageSize);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcConnectionUtil.close(threadLocal.get());
        }
        return orders;
    }

    @Override
    public List<OrderDetail> queryOrderDetailByOrderId(Integer iOrderId) {
        List<OrderDetail> details = new ArrayList<>();
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            details = orderDao.queryOrderDetailByOrderId(iOrderId);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcConnectionUtil.close(threadLocal.get());
        }
        return details;
    }

    @Override
    public List<OrderDetail> queryOrderDetailByUserId(Integer userId) {
        List<OrderDetail> details = new ArrayList<>();
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            details = orderDao.queryOrderDetailByUserId(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcConnectionUtil.close(threadLocal.get());
        }
        return details;
    }

    /*   实现了OrderService结束,具体注释请查看OrderService   */
}
