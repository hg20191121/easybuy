package com.hisoft.dao;

import com.hisoft.entity.Category;
import com.hisoft.entity.Order;
import com.hisoft.entity.Product;

import java.util.List;

public interface ProductDao extends BaseDao<Product,Integer> {
    /**
     * 根据CategoryLevel1Id查找到该商品类别下的所有产品
     *
     * @param integer categoryLevel1Id
     * @return 返回一个产品集合
     */
    default List<Product> qurryProductsByCategoryLevel1Id(Integer integer){return null;}


    /**
     * 根据order的id查询所有product
     *  在easybuy_order_detail中查询
     * @param id
     * @return
     */
    default List<Product> queryProductsByOrderId(Integer id) {
        return null;
    }


    /**
     * 分页查询
     * @param iCurrentPage
     * @param pageSize
     * @return
     */
    default List<Product> queryProductsByPage(Integer iCurrentPage, Integer pageSize) {
        return null;
    }



    /**
     * 根据关键字查找product
     * @param keywork
     * @return
     */
    default List<Product> queryProductsByKeyWord(String keywork) {
        return null;
    }
}
