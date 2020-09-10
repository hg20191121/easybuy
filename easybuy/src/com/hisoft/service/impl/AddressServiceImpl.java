package com.hisoft.service.impl;

import com.hisoft.dao.AddressDao;
import com.hisoft.dao.impl.AddressDaoImpl;
import com.hisoft.entity.Address;
import com.hisoft.service.AddressService;
import com.hisoft.util.JdbcConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AddressServiceImpl implements AddressService {
    private ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
    private AddressDao addressDao = new AddressDaoImpl(threadLocal);

    @Override
    public Integer remove(Integer integer) {
   Integer rem=null;

        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            rem=addressDao.remove(integer);
           // return addressDao.remove(integer);
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
    public List<Address> queryAll() {
        List<Address> addressList=null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            addressList=addressDao.queryAll();
           // return addressDao.queryAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcConnectionUtil.close(threadLocal.get());
            threadLocal.remove();
        }
        return addressList;
    }

    @Override
    public Address queryOneByPrimaryKey(Integer integer) {
        Address key=null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            key=addressDao.queryOneByPrimaryKey(integer);
            //return addressDao.queryOneByPrimaryKey(integer);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcConnectionUtil.close(threadLocal.get());
            threadLocal.remove();
        }
        return key;
    }

    @Override
    public Integer queryCount() {
        Integer count=null;

        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
          count= addressDao.queryCount();
           // return addressDao.queryCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcConnectionUtil.close(threadLocal.get());
            threadLocal.remove();
        }
        return count;
    }

    @Override
    public Integer insertOne(Address address) {
        Integer insert=null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            threadLocal.get().setAutoCommit(false);
            insert=addressDao.insertOne(address);
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
        //return addressDao.insertOne(address);
        return insert;
    }

    @Override
    public Integer updateOne(Address address, Integer integer) {
        Integer update=null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            threadLocal.get().setAutoCommit(false);
            update=addressDao.updateOne(address,integer);
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
        // return addressDao.updateOne(address,integer);
        return update;
    }




    /*  ----------------------------------------------------    */

    @Override
    public List<Address> queryAddressByUserId(Integer id) {
        List<Address> userid=null;
        try {
            threadLocal.set(JdbcConnectionUtil.getConnection());
            userid=addressDao.queryAddressByUserId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcConnectionUtil.close(threadLocal.get());
            threadLocal.remove();
        }
return userid;
        //return addressDao.queryAddressByUserId(id);
    }
}
