package com.hisoft.service.impl;

import com.hisoft.dao.ProductDao;
import com.hisoft.dao.impl.ProductDaoImpl;
import com.hisoft.entity.Order;
import com.hisoft.entity.Product;
import com.hisoft.service.ProductService;
import com.hisoft.util.JdbcConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    private ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    private ProductDao productDao = new ProductDaoImpl(threadLocal);

    /*   实现了BaseService开始,具体注释请查看BaseService    */

    @Override
    public Integer remove(Integer integer) {
        Integer rem=null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            rem=productDao.remove(integer);
            //return productDao.remove(integer);
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
    public List<Product> queryAll() {
        List<Product> all = null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            all = productDao.queryAll();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(threadLocal.get());
            threadLocal.remove();
        }
        return all;
    }

    @Override
    public Product queryOneByPrimaryKey(Integer integer)  {
        Product product = null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            product = productDao.queryOneByPrimaryKey(integer);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(threadLocal.get());
            threadLocal.remove();
        }
        return product;
    }

    @Override
    public Integer queryCount() {
        Integer num = null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            num = productDao.queryCount();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(threadLocal.get());
            threadLocal.remove();
        }
        return num;
    }

    @Override
    public Integer insertOne(Product product) {
        Integer key = null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            //开始事务，默认提交设置为否
            threadLocal.get().setAutoCommit(false);
            key = productDao.insertOne(product);
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
    public Integer updateOne(Product product, Integer integer) {
        Integer key = null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            //开始事务，默认提交设置为否
            threadLocal.get().setAutoCommit(false);
            key = productDao.updateOne(product,integer);
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


    /*    实现ProductDao的部分开始,注释请查看ProductDao   */

    @Override
    public List<Product> qurryProductsByCategoryLevel1Id(Integer integer) {
        List<Product> all = null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            all = productDao.qurryProductsByCategoryLevel1Id(integer);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(threadLocal.get());
            threadLocal.remove();
        }
        return all;
    }

    @Override
    public List<Product> queryProductsByOrderId(Integer id) {
        List<Product> products = new ArrayList<>();
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            products = productDao.queryProductsByOrderId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(threadLocal.get());
            threadLocal.remove();
        }
        return products;
    }

    @Override
    public List<Product> queryProductsByPage(Integer iCurrentPage, Integer pageSize) {
        List<Product> products = new ArrayList<>();
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            products = productDao.queryProductsByPage(iCurrentPage,pageSize);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(threadLocal.get());
            threadLocal.remove();
        }
        return products;
    }

    @Override
    public List<Product> queryProductsByKeyWord(String keywork) {
        List<Product> products = new ArrayList<>();
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            products = productDao.queryProductsByKeyWord(keywork);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(threadLocal.get());
            threadLocal.remove();
        }
        return products;
    }


    /*    实现ProductDao的部分完成,注释请查看ProductDao   */
}
