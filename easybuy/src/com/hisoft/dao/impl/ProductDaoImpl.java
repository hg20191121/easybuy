package com.hisoft.dao.impl;

import com.hisoft.dao.ProductDao;
import com.hisoft.entity.Order;
import com.hisoft.entity.Product;
import com.hisoft.entity.User;
import com.hisoft.util.JdbcConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl extends ConnectionHolder implements ProductDao {

    public ProductDaoImpl(ThreadLocal<Connection> threadLocal) {
        super(threadLocal);
    }


    /*    实现BaseDao的部分开始,注释请查看BaseDao    */

    @Override
    public Integer remove(Integer integer) {
        Connection connection=this.threadLocal.get();
        PreparedStatement pstat=null;
        String sql=null;
        Integer key=null;
   ResultSet resultSet=null;
        try {
            sql="delete from easybuy_product where id=?";
            pstat = connection.prepareStatement(sql);
            pstat.setInt(1,integer);
            key=pstat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcConnectionUtil.close(pstat, resultSet, null);
        }

        return key;
    }

    @Override
    public List<Product> queryAll() throws SQLException {
        Connection connection = this.threadLocal.get();
        String sql = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Product> list = new ArrayList<>();
        try {

            sql = "select id,name,description,price,stock,categoryLevel1Id,categoryLevel2Id,categoryLevel3Id,fileName,isDelete from easybuy_product";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setP_id(resultSet.getInt("id"));
                product.setP_name(resultSet.getString("name"));
                product.setP_description(resultSet.getString("description"));
                product.setP_price(resultSet.getFloat("price"));
                product.setP_stock(resultSet.getInt("stock"));
                product.setP_categoryLevel1(resultSet.getInt("categoryLevel1Id"));
                product.setP_categoryLevel2(resultSet.getInt("categoryLevel2Id"));
                product.setP_categoryLevel3(resultSet.getInt("categoryLevel3Id"));
                product.setP_fileName(resultSet.getString("fileName"));
                list.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(statement, resultSet, null);
        }
        return list;
    }

    @Override
    public Product queryOneByPrimaryKey(Integer integer) {
        Connection connection = this.threadLocal.get();
        String sql = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Product product = null;
        try {
            sql = "select id,name,description,price,stock,categoryLevel1Id,categoryLevel2Id,categoryLevel3Id,fileName,isDelete from easybuy_product where id=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, integer.intValue());
            resultSet = statement.executeQuery();
            product = new Product();
            if (resultSet.next()) {
                product.setP_id(resultSet.getInt("id"));
                product.setP_name(resultSet.getString("name"));
                product.setP_description(resultSet.getString("description"));
                product.setP_price(resultSet.getFloat("price"));
                product.setP_stock(resultSet.getInt("stock"));
                product.setP_categoryLevel1(resultSet.getInt("categoryLevel1Id"));
                product.setP_categoryLevel2(resultSet.getInt("categoryLevel2Id"));
                product.setP_categoryLevel3(resultSet.getInt("categoryLevel3Id"));
                product.setP_fileName(resultSet.getString("fileName"));
                if (resultSet.getInt("isDelete") == 0) {
                    product.setP_isDelete(false);
                } else {
                    product.setP_isDelete(true);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(statement, resultSet, null);
        }
        return product;
    }

    @Override
    public Integer queryCount() {
        Connection connection = this.threadLocal.get();
        String sql = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Integer key = null;
        try {

            sql = "select count(1) num from easybuy_product ";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                key = resultSet.getInt("num");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(statement, resultSet, null);
        }
        return key;
    }

    @Override
    public Integer insertOne(Product product) {
        Connection connection = this.threadLocal.get();
        String sql = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Integer key = null;
        try {

            sql = "insert  into easybuy_product (name,description,price,stock,categoryLevel1Id,categoryLevel2Id,categoryLevel3Id,fileName,isDelete) values  (?,?,?,?,?,?,?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, product.getP_name());
            statement.setString(2, product.getP_description());
            statement.setFloat(3, product.getP_price());
            statement.setInt(4, product.getP_stock());
            statement.setInt(5, product.getP_categoryLevel1());
            statement.setInt(6, product.getP_categoryLevel2());
            statement.setInt(7, product.getP_categoryLevel3());
            statement.setString(8, product.getP_fileName());
            if (true == product.getP_isDelete()) {
                statement.setInt(9, 1);
            } else {
                statement.setInt(9, 0);
            }
            key = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(statement, resultSet, null);
        }
        return key;
    }

    @Override
    public Integer updateOne(Product product, Integer integer) {
        Connection connection = this.threadLocal.get();
        String sql = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Integer key = null;
        try {

            sql = "update easybuy_product set name=(?),description=(?),price=?,stock=?,categoryLevel1Id=?,categoryLevel2Id=?,categoryLevel3Id=?,fileName=(?),isDelete=? where id=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, product.getP_name());
            statement.setString(2, product.getP_description());
            statement.setFloat(3, product.getP_price());
            statement.setInt(4, product.getP_stock());
            statement.setInt(5, product.getP_categoryLevel1());
            statement.setInt(6, product.getP_categoryLevel2());
            statement.setInt(7, product.getP_categoryLevel3());
            statement.setString(8, product.getP_fileName());
            if (true == product.getP_isDelete()) {
                statement.setInt(9, 1);
            } else {
                statement.setInt(9, 0);
            }
            statement.setInt(10,integer.intValue());
            key = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(statement, resultSet, null);
        }
        return key;
    }


    /*    实现BaseDao的部分结束,注释请查看BaseDao    */

    /*    实现ProductDao的部分开始,注释请查看ProductDao   */

    @Override
    public List<Product> qurryProductsByCategoryLevel1Id(Integer integer) {
        Connection connection = this.threadLocal.get();
        String sql = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Product> list = new ArrayList<>();
        try {

            sql = "select id,name,description,price,stock,categoryLevel1Id,categoryLevel2Id,categoryLevel3Id,fileName,isDelete from easybuy_product where categoryLevel1Id=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1,integer.intValue());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setP_id(resultSet.getInt("id"));
                product.setP_name(resultSet.getString("name"));
                product.setP_description(resultSet.getString("description"));
                product.setP_price(resultSet.getFloat("price"));
                product.setP_stock(resultSet.getInt("stock"));
                product.setP_categoryLevel1(resultSet.getInt("categoryLevel1Id"));
                product.setP_categoryLevel2(resultSet.getInt("categoryLevel2Id"));
                product.setP_categoryLevel3(resultSet.getInt("categoryLevel3Id"));
                product.setP_fileName(resultSet.getString("fileName"));
                list.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(statement, resultSet, null);
        }
        return list;
    }

    @Override
    public List<Product> queryProductsByOrderId(Integer id) {
        Connection connection = this.threadLocal.get();
        String sql = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Product> list = new ArrayList<>();
        try {
//            sql = "select ep.* from easybuy_product ep,(select productid from easybuy_order_detail where orderid =?) pid" +
//                    "where ep.id =pid.productid; ";
            sql = "select t1.* from easybuy_product t1 where t1.id in (select productId from easybuy_order_detail t2 where orderId = ?)";
            statement = connection.prepareStatement(sql);
            statement.setInt(1,id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setP_id(resultSet.getInt("id"));
                product.setP_name(resultSet.getString("name"));
                product.setP_description(resultSet.getString("description"));
                product.setP_price(resultSet.getFloat("price"));
                product.setP_stock(resultSet.getInt("stock"));
                product.setP_categoryLevel1(resultSet.getInt("categoryLevel1Id"));
                product.setP_categoryLevel2(resultSet.getInt("categoryLevel2Id"));
                product.setP_categoryLevel3(resultSet.getInt("categoryLevel3Id"));
                product.setP_fileName(resultSet.getString("fileName"));
                list.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(statement, resultSet, null);
        }
        return list;
    }

    @Override
    public List<Product> queryProductsByPage(Integer iCurrentPage, Integer pageSize) {
        Connection connection = this.threadLocal.get();
        String sql = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Product> list = new ArrayList<>();
        try {

            sql = "select id,name,description,price,stock,categoryLevel1Id,categoryLevel2Id,categoryLevel3Id,fileName,isDelete from easybuy_product limit ?,?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1,(iCurrentPage-1)*pageSize);
            statement.setInt(2,pageSize);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setP_id(resultSet.getInt("id"));
                product.setP_name(resultSet.getString("name"));
                product.setP_description(resultSet.getString("description"));
                product.setP_price(resultSet.getFloat("price"));
                product.setP_stock(resultSet.getInt("stock"));
                product.setP_categoryLevel1(resultSet.getInt("categoryLevel1Id"));
                product.setP_categoryLevel2(resultSet.getInt("categoryLevel2Id"));
                product.setP_categoryLevel3(resultSet.getInt("categoryLevel3Id"));
                product.setP_fileName(resultSet.getString("fileName"));
                list.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(statement, resultSet, null);
        }
        return list;
    }

    @Override
    public List<Product> queryProductsByKeyWord(String keywork) {
        Connection connection = this.threadLocal.get();
        String sql = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Product> list = new ArrayList<>();
        try {

            sql = "select * from easybuy_product where name like \"%\"?\"%\"";
            statement = connection.prepareStatement(sql);
            statement.setString(1,keywork);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setP_id(resultSet.getInt("id"));
                product.setP_name(resultSet.getString("name"));
                product.setP_description(resultSet.getString("description"));
                product.setP_price(resultSet.getFloat("price"));
                product.setP_stock(resultSet.getInt("stock"));
                product.setP_categoryLevel1(resultSet.getInt("categoryLevel1Id"));
                product.setP_categoryLevel2(resultSet.getInt("categoryLevel2Id"));
                product.setP_categoryLevel3(resultSet.getInt("categoryLevel3Id"));
                product.setP_fileName(resultSet.getString("fileName"));
                list.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(statement, resultSet, null);
        }
        return list;
    }



    /*    实现ProductDao的部分完成,注释请查看ProductDao   */
}
