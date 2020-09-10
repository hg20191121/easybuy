package com.hisoft.dao.impl;

import com.hisoft.dao.CategoryDao;
import com.hisoft.entity.Category;
import com.hisoft.entity.Product;
import com.hisoft.entity.User;
import com.hisoft.util.JdbcConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl extends ConnectionHolder implements CategoryDao {


    public CategoryDaoImpl(ThreadLocal<Connection> threadLocal) {

        super(threadLocal);
    }

    /*    实现BaseDao的部分开始,注释请查看BaseDao    */

    @Override
    public Integer remove(Integer integer) {
        Connection connection = this.threadLocal.get();
        PreparedStatement pstat = null;
        String sql = null;
        ResultSet resultSet = null;
        Integer key = null;
        try {
            sql = "delete from easybuy_product_category where id=?";
            pstat = connection.prepareStatement(sql);
            pstat.setInt(1, integer);
            key = pstat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(pstat, resultSet, null);
        }
        return key;
    }

    @Override
    public List<Category> queryAll() {
        Connection connection = this.threadLocal.get();
        String sql = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Category category = null;
        List<Category> list = new ArrayList<>();
        try {
            sql = "select id,name,parentId,type,iconClass from easybuy_product_category";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                category = new Category();
                category.setC_id(resultSet.getInt("id"));
                category.setC_name(resultSet.getString("name"));
                category.setC_parentId(resultSet.getInt("parentId"));
                category.setC_type(resultSet.getInt("type"));
                category.setC_iconClass(resultSet.getString("iconClass"));
                list.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(statement, resultSet, null);
        }
        return list;
    }

    @Override
    public Category queryOneByPrimaryKey(Integer integer) {
        Connection connection = this.threadLocal.get();
        String sql = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Category category = null;
        try {
            sql = "select id,name,parentId,type,iconClass from easybuy_product_category where id=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, integer.intValue());
            resultSet = statement.executeQuery();
            category = new Category();
            if (resultSet.next()) {
                category.setC_id(resultSet.getInt("id"));
                category.setC_name(resultSet.getString("name"));
                category.setC_parentId(resultSet.getInt("parentId"));
                category.setC_type(resultSet.getInt("type"));
                category.setC_iconClass(resultSet.getString("iconClass"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(statement, resultSet, null);
        }
        return category;
    }

    @Override
    public Integer queryCount() {
        Connection connection = this.threadLocal.get();
        String sql = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Integer key = null;
        try {
            sql = "select count(1) num from easybuy_product_category";
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
    public Integer insertOne(Category category) {
        Connection connection = this.threadLocal.get();
        String sql = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Integer key = null;
        try {
            sql = "insert into easybuy_product_category (name,parentId,type,iconClass)values(?,?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, category.getC_name());
            statement.setInt(2, category.getC_parentId());
            statement.setInt(3, category.getC_type());
            statement.setString(4, category.getC_iconClass());
            key = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(statement, resultSet, null);
            return key;
        }
    }

    @Override
    public Integer updateOne(Category category, Integer integer) {
        Connection connection = this.threadLocal.get();
        String sql = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Integer key = null;
        try {
            sql = "update set easybuy_product_category name=(?),parentId=?,type=(?),iconClass=?  where id=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, category.getC_name());
            statement.setInt(2, category.getC_parentId());
            statement.setInt(3, category.getC_type());
            statement.setString(4, category.getC_iconClass());
            statement.setInt(5, category.getC_id());
            key = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(statement, resultSet, null);
            return key;
        }
    }



    /*    实现BaseDao的部分结束,注释请查看BaseDao    */






    /*    实现CategoryDao的部分开始,注释请查看CategoryDao相关的方法    */

    @Override
    public List<Category> queryCategoriesByParentId(Integer id) {
        Connection connection = this.threadLocal.get();
        String sql = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Category> list = new ArrayList<>();
        try {
            sql = "select id,name,parentId,type,iconClass from easybuy_product_category where parentId=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id.intValue());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setC_id(resultSet.getInt("id"));
                category.setC_name(resultSet.getString("name"));
                category.setC_parentId(resultSet.getInt("parentId"));
                category.setC_type(resultSet.getInt("type"));
                category.setC_iconClass(resultSet.getString("iconClass"));
                List<Product> products = queryProductsByCategoryLevel1Id(category.getC_id());
                list.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(statement, resultSet, null);
        }
        return list;
    }

    //      查询所有等级为1的category,并且将等级为2的和等级为3的也查询出来
//     * 也就是Category的c_children属性必须有值(最后一级的category的c_children属性为null)
//     * @return
    @Override
    public List<Category> queryLevel_1_Categories() {
        Connection connection = this.threadLocal.get();
        String sql = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Category> listTypeOfOne = new ArrayList<>();
        try {
            sql = "select id,name,parentId,type,iconClass from easybuy_product_category where type=(1)";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setC_id(resultSet.getInt("id"));
                category.setC_name(resultSet.getString("name"));
                category.setC_parentId(resultSet.getInt("parentId"));
                category.setC_type(resultSet.getInt("type"));
                category.setC_iconClass(resultSet.getString("iconClass"));
                //设置当前一级分类的绑定的产品
                category.setC_products(queryProductsByCategoryLevel1Id(category.getC_id()));
                //设置当前一级分类的下一级集合
                category.setC_children(queryLevel_2_CategoriesByParentId(category.getC_id()));

                listTypeOfOne.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(statement, resultSet, null);
        }
        return listTypeOfOne;
    }

    /**
     * 根据父类的id查级别为2的分类
     *
     * @return
     */
    public List<Category> queryLevel_2_CategoriesByParentId(Integer parentId) {
        Connection connection = this.threadLocal.get();
        String sql = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Category> listOfTwo = new ArrayList<>();
        try {
            sql = "select id,name,parentId,type,iconClass from easybuy_product_category where type=2 and parentId=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, parentId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setC_id(resultSet.getInt("id"));
                category.setC_name(resultSet.getString("name"));
                category.setC_parentId(resultSet.getInt("parentId"));
                category.setC_type(resultSet.getInt("type"));
                category.setC_iconClass(resultSet.getString("iconClass"));
                //设置当前分类的绑定的产品.  二级分类不需要绑定产品
//                category.setC_products(queryProductsByCategoryLevel1Id(category.getC_id()));
                category.setC_products(null);
                //设置当前分类的下一级集合
                category.setC_children(queryLevel_3_CategoriesByParentId(category.getC_id()));
                listOfTwo.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(statement, resultSet, null);
        }
        return listOfTwo;
    }

    /**
     * 根据父类的id查级别为3的分类
     *
     * @return
     */
    public List<Category> queryLevel_3_CategoriesByParentId(Integer parentId) {
        Connection connection = this.threadLocal.get();
        String sql = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Category> listOfThree = new ArrayList<>();
        try {
            sql = "select id,name,parentId,type,iconClass from easybuy_product_category where type=3 and parentId=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, parentId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setC_id(resultSet.getInt("id"));
                category.setC_name(resultSet.getString("name"));
                category.setC_parentId(resultSet.getInt("parentId"));
                category.setC_type(resultSet.getInt("type"));
                category.setC_iconClass(resultSet.getString("iconClass"));
                //设置当前分类的绑定的产品.  三级级分类不需要绑定产品
//                category.setC_products(queryProductsByCategoryLevel1Id(category.getC_id()));
                category.setC_products(null);
                //设置当前分类的下一级集合,三级分类没有四级分类了
                category.setC_children(null);
                listOfThree.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(statement, resultSet, null);
        }
        return listOfThree;
    }

    /**
     * 根据当前类别的id查询子产品
     *
     * @return 一级分类绑定的子产品
     * @integer 一级分类的id值
     */
    public List<Product> queryProductsByCategoryLevel1Id(Integer integer) {
        Connection connection = this.threadLocal.get();
        String sql = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Product> listProduct = new ArrayList<>();
        Product product = null;
        try {
            sql = "select id,name,description,price,stock,categoryLevel1Id,categoryLevel2Id,categoryLevel3Id,fileName,isDelete from easybuy_product where categoryLevel1Id=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, integer.intValue());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                product = new Product();
                product.setP_id(resultSet.getInt("id"));
                product.setP_name(resultSet.getString("name"));
                product.setP_description(resultSet.getString("description"));
                product.setP_price(resultSet.getFloat("price"));
                product.setP_stock(resultSet.getInt("stock"));
                product.setP_categoryLevel1(resultSet.getInt("categoryLevel1Id"));
                product.setP_categoryLevel2(resultSet.getInt("categoryLevel2Id"));
                product.setP_categoryLevel3(resultSet.getInt("categoryLevel3Id"));
                product.setP_fileName(resultSet.getString("fileName"));
                int isDelete = resultSet.getInt("isDelete");
                if (isDelete == 0) {
                    product.setP_isDelete(false);
                } else {
                    product.setP_isDelete(true);
                }
                listProduct.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(statement, resultSet, null);
        }
        return listProduct;
    }

    /**
     * 分页查询，根据页面大小以及当前页面查询相关数据
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public List<Category> queryCategoryByPage(Integer currentPage, Integer pageSize) {
        Connection connection = this.threadLocal.get();
        String sql = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Category> list = new ArrayList<>();
        try {
            sql = "select id,name,parentId,type,iconClass from easybuy_product_category limit ?,?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, (currentPage - 1) * pageSize);
            statement.setInt(2, pageSize);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setC_id(resultSet.getInt("id"));
                category.setC_name(resultSet.getString("name"));
                category.setC_parentId(resultSet.getInt("parentId"));
                category.setC_type(resultSet.getInt("type"));
                category.setC_iconClass(resultSet.getString("iconClass"));
                list.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectionUtil.close(statement, resultSet, null);
        }
        return list;
    }


    /*    实现CategoryDao的部分结束,注释请查看CategoryDao相关的方法    */
}
