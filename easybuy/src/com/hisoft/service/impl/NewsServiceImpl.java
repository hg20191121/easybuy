package com.hisoft.service.impl;

import com.hisoft.dao.NewsDao;
import com.hisoft.dao.impl.NewsDaoImpl;
import com.hisoft.entity.Category;
import com.hisoft.entity.News;
import com.hisoft.service.NewsService;
import com.hisoft.util.JdbcConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class NewsServiceImpl implements NewsService {

    private ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    private NewsDao newsDao = new NewsDaoImpl(threadLocal);

    /*   实现了BaseService开始,具体注释请查看BaseService    */


    @Override
    public Integer remove(Integer integer) {
        Integer rem=null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            rem=newsDao.remove(integer);
            //return newsDao.remove(integer);
            threadLocal.get().commit();
        } catch (SQLException e) {
            try {
                threadLocal.get().rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            JdbcConnectionUtil.close(threadLocal.get());
            threadLocal.remove();
        }
        return rem;
    }

    @Override
    public List<News> queryAll() {
        List<News> all = null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            all = newsDao.queryAll();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(threadLocal.get());
            threadLocal.remove();
        }
        return all;
    }

    @Override
    public News queryOneByPrimaryKey(Integer integer) {
        News news = null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            news = newsDao.queryOneByPrimaryKey(integer);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(threadLocal.get());
            threadLocal.remove();
        }
        return news;
    }

    @Override
    public Integer queryCount() {
        Integer key = null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            key = newsDao.queryCount();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(threadLocal.get());
            threadLocal.remove();
        }
        return key;
    }

    @Override
    public Integer insertOne(News news) {
        Integer key = null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            //开始事务，默认提交设置为否
            threadLocal.get().setAutoCommit(false);
            key = newsDao.insertOne(news);
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
    public Integer updateOne(News news, Integer integer) {
        Integer key = null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            //开始事务，默认提交设置为否
            threadLocal.get().setAutoCommit(false);
            key = newsDao.updateOne(news,integer);
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


    /*   实现了NewsService开始,NewsService    */

    @Override
    public List<News> queryNewsByDateByOrder(Integer n) {
        List<News> all = null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            all = newsDao.queryNewsByDateByOrder(n);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(threadLocal.get());
            threadLocal.remove();
        }
        return all;
    }

    @Override
    public List<News> queryNewsByPage(Integer currentPage, Integer pageSize) {
        List<News> all = null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            all = newsDao.queryNewsByPage(currentPage,pageSize);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(threadLocal.get());
            threadLocal.remove();
        }
        return all;
    }

    /*   实现了NewsService结束,NewsService    */
}
