package com.hisoft.service.impl;

import com.hisoft.dao.UserDao;
import com.hisoft.dao.impl.UserDaoImpl;
import com.hisoft.entity.News;
import com.hisoft.entity.User;
import com.hisoft.service.UserService;
import com.hisoft.util.JdbcConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    private UserDao userDao = new UserDaoImpl(threadLocal);


    /*   实现了BaseService开始,具体注释请查看BaseService    */

    @Override
    public Integer remove(Integer integer) {
       Integer rem=null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
           rem=userDao.remove(integer);
           // threadLocal.get().commit();
            threadLocal.get().setAutoCommit(false);
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
    public List<User> queryAll() {
        List<User> all = null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            all = userDao.queryAll();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(threadLocal.get());
            threadLocal.remove();
        }
        return all;
    }

    @Override
    public User queryOneByPrimaryKey(Integer integer) {
        User user = null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            user = userDao.queryOneByPrimaryKey(integer);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(threadLocal.get());
            threadLocal.remove();
        }
        return user;
    }

    @Override
    public Integer queryCount() {
        Integer num = null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            num = userDao.queryCount();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(threadLocal.get());
            threadLocal.remove();
        }
        return num;
    }

    @Override
    public Integer insertOne(User user) {
        Integer key = null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            //开始事务，默认提交设置为否
            threadLocal.get().setAutoCommit(false);
            key = userDao.insertOne(user);
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
            //成则提交
            try {
                threadLocal.get().commit();
                JdbcConnectionUtil.close(threadLocal.get());
                threadLocal.remove();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return key;
    }

    @Override
    public Integer updateOne(User user, Integer integer) {
        Integer key = null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            //开始事务，默认提交设置为否
            threadLocal.get().setAutoCommit(false);
            key = userDao.updateOne(user, integer);
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
            try {
                threadLocal.get().commit();
                JdbcConnectionUtil.close(threadLocal.get());
                threadLocal.remove();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return key;
    }




    /*   实现了BaseService结束,具体注释请查看BaseService    */


    /*  UserService 开始，具体看UserService*/

    @Override
    public User queryUserByUserNameAndPassword(String username, String password) {
        User user=null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            user=userDao.queryUserByUserNameAndPassword(username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcConnectionUtil.close(threadLocal.get());
            threadLocal.remove();
        }
        return user;
    }

    @Override
    public User queryUserByUserName(String username) {
        User user=new User();
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            user=userDao.queryUserByUserName(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcConnectionUtil.close(threadLocal.get());
            threadLocal.remove();
        }
        return user;
    }

    @Override
    public User queryUserByLoginName(String loginName) {
        User user=new User();
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            user=userDao.queryUserByLoginName(loginName);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcConnectionUtil.close(threadLocal.get());
            threadLocal.remove();
        }
        return user;
    }

    @Override
    public List<User> queryUsersByPage(Integer currentPage, Integer pageSize) {
        List<User> users = new ArrayList<>();
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            users=userDao.queryUsersByPage(currentPage,pageSize);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcConnectionUtil.close(threadLocal.get());
            threadLocal.remove();
        }
        return users;
    }

    /*  UserService 结束，具体看UserService*/

}
