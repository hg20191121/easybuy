package com.hisoft.dao.impl;

import com.hisoft.dao.AddressDao;
import com.hisoft.dao.CategoryDao;
import com.hisoft.entity.Address;
import com.hisoft.util.JdbcConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressDaoImpl extends ConnectionHolder implements AddressDao {

    public AddressDaoImpl(ThreadLocal<Connection> threadLocal) {
        super(threadLocal);
    }

    @Override
    public Integer remove(Integer integer) {
        Connection connection = this.threadLocal.get();
        PreparedStatement pstat = null;
        Integer key = null;
        String sql = null;

        try {
            sql = "delete from easybuy_user_address where id=?";
            pstat = connection.prepareStatement(sql);
            pstat.setInt(1, integer);
            key = pstat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(pstat, null, null);
        }
        return key;
    }

    @Override
    public List<Address> queryAll() throws SQLException {
        Connection connection = this.threadLocal.get();
        PreparedStatement pstat = null;
        String sql = null;

        List<Address> listaddress = new ArrayList<>();
        Address address = new Address();
        sql = "select id,userId,address,createTime,isDefault,remark from easybuy_user_address";
        pstat = connection.prepareStatement(sql);
        ResultSet res = pstat.executeQuery();
        while (res.next()) {
            address.setId(res.getInt("id"));
            address.setUserId(res.getInt("userId"));
            address.setAddress(res.getNString("address"));
            address.setCreateDate(res.getDate("createTime"));
            address.setIsDefault(res.getInt("isDefault"));
            address.setRemark(res.getString("remark"));
            listaddress.add(address);
        }
        JdbcConnectionUtil.close(pstat, res, null);
        return listaddress;
    }

    @Override
    public Address queryOneByPrimaryKey(Integer integer) throws SQLException {
        Connection connection = this.threadLocal.get();
        PreparedStatement pstat = null;
        ResultSet res = null;
        String sql = null;
        //List<Address> listaddress=new ArrayList<>();
        Address address = new Address();

        sql = "select userId,address,createTime,isDefault,remark from easybuy_user_address where id=? ";
        pstat = connection.prepareStatement(sql);
        pstat.setInt(1, integer);
        res = pstat.executeQuery();

        while (res.next()) {
            address.setUserId(res.getInt("userId"));
            address.setAddress(res.getNString("address"));
            address.setCreateDate(res.getDate("createTime"));
            address.setIsDefault(res.getInt("isDefault"));
            address.setRemark(res.getString("remark"));
            //listaddress.add(address);
        }
        JdbcConnectionUtil.close(pstat, res, null);
        return address;

    }

    @Override
    public Integer queryCount() throws SQLException {
        Connection connection = this.threadLocal.get();
        PreparedStatement pstat = null;
        ResultSet res = null;
        String sql = null;
        Integer key = null;

        sql = "select count(1) num from easybuy_user_address";
        connection.prepareStatement(sql);
        res = pstat.executeQuery();

        key = res.getInt("num");
        JdbcConnectionUtil.close(pstat, res, null);
        return key;
    }

    @Override
    public Integer insertOne(Address address) {

        Connection connection = this.threadLocal.get();
        String sql = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Integer key = null;
        try {
            sql = "insert into easybuy_user_address (userId,address,createTime,isDefault,remark) values (?,?,?,?,?)";
            statement = connection.prepareStatement(sql);

            statement.setInt(1, address.getUserId());
            statement.setString(2, address.getAddress());
            statement.setObject(3, address.getCreateDate());
            statement.setInt(4, address.getIsDefault());
            statement.setString(5, address.getRemark());
            key = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(statement, resultSet, null);
        }
        return key;
    }

    @Override
    public Integer updateOne(Address address, Integer integer) {
        Connection connection = this.threadLocal.get();
        PreparedStatement pstat = null;
        String sql = null;
        ResultSet res = null;
        Integer anInt = null;

        try {
            sql = "Update easybuy_user_address set userId=?,address=(?),createTime=?,isDefault=?,remark=(?) where id=?";
            pstat = connection.prepareStatement(sql);
            pstat.setInt(1, address.getUserId());
            pstat.setString(2, address.getAddress());
            pstat.setObject(3, address.getCreateDate());
            pstat.setInt(4, address.getIsDefault());
            pstat.setString(5, address.getRemark());
            pstat.setInt(6, integer);
            anInt = pstat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(pstat, res, null);
        }
        return anInt;
    }




    /*  ---------------------------------------     */

    @Override
    public List<Address> queryAddressByUserId(Integer id) {
        Connection connection = this.threadLocal.get();
        PreparedStatement pstat = null;
        String sql = null;
        ResultSet res = null;
        Integer anInt = 0;
        List<Address> addressList = new ArrayList<>();
        Address address = new Address();
        try {
            sql = "select id,address,createTime,isDefault,remark from easybuy_user_address where userId=?";
            pstat = connection.prepareStatement(sql);
            pstat.setInt(1, id);
            res = pstat.executeQuery();
            while (res.next()) {
                address.setId(res.getInt("id"));
                address.setAddress(res.getNString("address"));
                address.setCreateDate(res.getDate("createTime"));
                address.setIsDefault(res.getInt("isDefault"));
                address.setRemark(res.getString("remark"));
                addressList.add(address);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(pstat, res, null);
        }
        return addressList;
    }
}
