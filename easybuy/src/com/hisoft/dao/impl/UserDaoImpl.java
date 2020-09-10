package com.hisoft.dao.impl;

import com.hisoft.dao.UserDao;
import com.hisoft.entity.User;
import com.hisoft.util.EncryptionUtil;
import com.hisoft.util.JdbcConnectionUtil;
import com.mysql.jdbc.Statement;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends ConnectionHolder implements UserDao {

    public UserDaoImpl(ThreadLocal<Connection> threadLocal) {
        super(threadLocal);
    }

    /*    实现BaseDao的部分开始,注释请查看BaseDao    */

    @Override
    public Integer remove(Integer integer) {
   Connection connection=this.threadLocal.get();
   String sql=null;
   PreparedStatement pstat=null;
   ResultSet resultSet=null;
        Integer key = null;
        try {
            sql="delete from easybuy_user where id=?";
            pstat=connection.prepareStatement(sql);
            pstat.setInt(1,integer);
            key=pstat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcConnectionUtil.close(pstat,resultSet,null);
        }
        return key;
    }

    @Override
    public List<User> queryAll() {
        Connection connection = this.threadLocal.get();
        String sql = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<User> list = new ArrayList<>();
        try {
            sql = "select id,loginName,username,password,sex,identityCode,email from easybuy_user";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setU_id(resultSet.getInt("id"));
                user.setU_loginName(resultSet.getString("loginName"));
                user.setU_userName(resultSet.getString("userName"));
                user.setU_password(resultSet.getString("password"));
                user.setU_sex(Character.valueOf(resultSet.getString("sex").charAt(0)));
                user.setU_identityCode(resultSet.getString("identityCode"));
                user.setU_email(resultSet.getString("email"));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(statement, resultSet, null);
        }
        return list;
    }

    @Override
    public User queryOneByPrimaryKey(Integer integer) {
        Connection connection = this.threadLocal.get();
        String sql = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        List<User> list = new ArrayList<>();
        try {
            sql = "select id,loginName,username,password,sex,identityCode,email from easybuy_user where id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, integer.intValue());
            resultSet = statement.executeQuery();
            user = new User();
            if (resultSet.next()) {
                user.setU_id(resultSet.getInt("id"));
                user.setU_loginName(resultSet.getString("loginName"));
                user.setU_userName(resultSet.getString("userName"));
                user.setU_password(resultSet.getString("password"));
                user.setU_sex(Character.valueOf(resultSet.getString("sex").charAt(0)));
                user.setU_identityCode(resultSet.getString("identityCode"));
                user.setU_email(resultSet.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(statement, resultSet, null);
        }
        return user;
    }

    @Override
    public Integer queryCount() {
        Connection connection = this.threadLocal.get();
        String sql = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Integer num = null;
        try {
            sql = "select count(1) num from easybuy_user";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                num = resultSet.getInt("num");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(statement, resultSet, null);
        }
        return num;
    }

    @Override
    public Integer insertOne(User user) {
        Connection connection = this.threadLocal.get();
        String sql = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Integer key = null;
        try {
            sql = "insert into easybuy_user (loginName,userName,password,sex,identityCode,email)values (?,?,?,?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getU_loginName());
            statement.setString(2, user.getU_userName());
            statement.setString(3, user.getU_password());
            statement.setInt(4,Integer.valueOf(user.getU_sex().toString()));
            statement.setString(5, user.getU_identityCode());
            statement.setString(6, user.getU_email()); key = statement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(statement, resultSet, null);
        }
        return key;
    }

    @Override
    public Integer updateOne(User user, Integer integer) {
        Connection connection = this.threadLocal.get();
        String sql = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Integer key = null;
        try {
            sql = "Update easybuy_user set loginName=(?),userName=(?),password=(?),sex=(?),identityCode=(?),email=(?) where id=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getU_loginName());
            statement.setString(2, user.getU_userName());
            statement.setString(3, user.getU_password());
            statement.setString(4, user.getU_sex().toString());
            statement.setString(5, user.getU_identityCode());
            statement.setString(6, user.getU_email());
            key = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(statement, resultSet, null);
        }
        return key;
    }


    /*    实现BaseDao的部分结束,注释请查看BaseDao    */

    /*    实现UserDao的部分开始,注释请查看UserDao    */

    @Override
    public User queryUserByUserNameAndPassword(String username, String password) {
        try {
            password = EncryptionUtil.str2Md5(password,"utf-8");
        } catch (NoSuchAlgorithmException e) {
            // do nothing
        }
        Connection connection = this.threadLocal.get();
        String sql = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            sql = "select id,loginName,userName,password,sex,identityCode,email,mobile,type from easybuy_user where password = ? and loginName = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, password);
            statement.setString(2, username);
            resultSet = statement.executeQuery();

            user = new User(password);
            if (resultSet.next()) {
                user.setU_id(resultSet.getInt("id"));
                user.setU_loginName(resultSet.getString("loginName"));
                user.setU_userName(resultSet.getString("userName"));
                user.setU_sex(Character.valueOf(resultSet.getString("sex").charAt(0)));
                user.setU_identityCode(resultSet.getString("identityCode"));
                user.setU_email(resultSet.getString("email"));
                user.setU_type(resultSet.getInt("type"));
                user.setU_mobile(resultSet.getString("mobile"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(statement, resultSet, null);
        }
        return user;
    }

    @Override
    public User queryUserByUserName(String username) {
        Connection connection = this.threadLocal.get();
        String sql = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        List<User> list = new ArrayList<>();
        try {
            sql = "select id,loginName,username,password,sex,identityCode,email from easybuy_user where userName = (?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            resultSet = statement.executeQuery();
            user = new User();
            if (resultSet.next()) {
                user.setU_id(resultSet.getInt("id"));
                user.setU_loginName(resultSet.getString("loginName"));
                user.setU_userName(resultSet.getString("userName"));
                user.setU_password(resultSet.getString("password"));
                user.setU_sex(Character.valueOf(resultSet.getString("sex").charAt(0)));
                user.setU_identityCode(resultSet.getString("identityCode"));
                user.setU_email(resultSet.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(statement, resultSet, null);
        }
        return user;
    }

    @Override
    public User queryUserByLoginName(String loginName) {
        Connection connection = this.threadLocal.get();
        String sql = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        List<User> list = new ArrayList<>();
        try {
            sql = "select id,loginName,username,password,sex,identityCode,email from easybuy_user where loginName = (?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, loginName);
            resultSet = statement.executeQuery();
            user = new User();
            if (resultSet.next()) {
                user.setU_id(resultSet.getInt("id"));
                user.setU_loginName(resultSet.getString("loginName"));
                user.setU_userName(resultSet.getString("userName"));
                user.setU_password(resultSet.getString("password"));
                user.setU_sex(Character.valueOf(resultSet.getString("sex").charAt(0)));
                user.setU_identityCode(resultSet.getString("identityCode"));
                user.setU_email(resultSet.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(statement, resultSet, null);
        }
        return user;
    }

    @Override
    public List<User> queryUsersByPage(Integer currentPage, Integer pageSize) {
        Connection connection = this.threadLocal.get();
        String sql = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<User> list = new ArrayList<>();
        try {
            sql = "select id,loginName,username,password,sex,identityCode,email from easybuy_user limit ?,?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1,(currentPage-1)*pageSize);
            statement.setInt(2,pageSize);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setU_id(resultSet.getInt("id"));
                user.setU_loginName(resultSet.getString("loginName"));
                user.setU_userName(resultSet.getString("userName"));
                user.setU_password(resultSet.getString("password"));
                user.setU_sex(Character.valueOf(resultSet.getString("sex").charAt(0)));
                user.setU_identityCode(resultSet.getString("identityCode"));
                user.setU_email(resultSet.getString("email"));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(statement, resultSet, null);
        }
        return list;
    }

    /*    实现UserDao的部分结束,注释请查看UserDao    */

}
