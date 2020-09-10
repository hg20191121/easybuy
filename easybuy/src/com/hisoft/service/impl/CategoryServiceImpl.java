package com.hisoft.service.impl;

import com.hisoft.dao.CategoryDao;
import com.hisoft.dao.impl.CategoryDaoImpl;
import com.hisoft.entity.Category;
import com.hisoft.entity.Product;
import com.hisoft.service.CategoryService;
import com.hisoft.util.JdbcConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    private CategoryDao categoryDao = new CategoryDaoImpl(threadLocal);


    /*   实现了BaseService开始,具体注释请查看BaseService    */

    @Override
    public Integer remove(Integer integer)  {
        Integer rem=null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            threadLocal.get().setAutoCommit(false);
            rem=categoryDao.remove(integer);
           // return categoryDao.remove(integer);
            threadLocal.get().commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                threadLocal.get().rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            JdbcConnectionUtil.close(threadLocal.get());
          threadLocal.remove();
        }
        return rem;
    }

    @Override
    public List<Category> queryAll() {
        List<Category> all = null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            all = categoryDao.queryAll();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(threadLocal.get());
            threadLocal.remove();
        }
        return all;
    }

    @Override
    public Category queryOneByPrimaryKey(Integer integer) {
        Category category = null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            category = categoryDao.queryOneByPrimaryKey(integer);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(threadLocal.get());
            threadLocal.remove();
        }
        return category;
    }

    @Override
    public Integer queryCount() {
        Integer key = 0;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            key = categoryDao.queryCount();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(threadLocal.get());
            threadLocal.remove();
        }
        return key;
    }

    @Override
    public Integer insertOne(Category category) {
        Integer key = null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            //开始事务，默认提交设置为否
            threadLocal.get().setAutoCommit(false);
            key = categoryDao.insertOne(category);
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
    public Integer updateOne(Category category, Integer integer) {
        Integer key = null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            //开始事务，默认提交设置为否
            threadLocal.get().setAutoCommit(false);
            key = categoryDao.updateOne(category, integer);
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




    /*    实现CategoryService的部分开始,注释请查看CategoryService相关的方法    */

    @Override
    public List<Category> queryCategoriesByParentId(Integer id) {
        List<Category> all = null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            all = categoryDao.queryCategoriesByParentId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(threadLocal.get());
            threadLocal.remove();
        }
        return all;
    }

    @Override
    public List<Category> queryLevel_1_Categories() {
        List<Category> all = null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            all = categoryDao.queryLevel_1_Categories();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(threadLocal.get());
            threadLocal.remove();
        }
        return all;
    }

    @Override
    public List<Category> queryCategoryByPage(Integer currentPage, Integer pageSize) {
        List<Category> all = null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            all = categoryDao.queryCategoryByPage(currentPage,pageSize);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(threadLocal.get());
            threadLocal.remove();
        }
        return all;
    }

    /*    实现CategoryService结束,注释请查看CategoryService   */
}
