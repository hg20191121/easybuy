package com.hisoft.dao.impl;

import com.hisoft.dao.FavoriteDao;
import com.hisoft.entity.Product;
import com.hisoft.util.MemCachedUtil;

import java.util.List;

public class FavoriteDaoImpl implements FavoriteDao {

    @Override
    public List<Product> queryFavoriteProductsFromMemcached(String user_prefix) {
        return MemCachedUtil.getFavoriteProducts(user_prefix);
    }

    @Override
    public Boolean addFavoriteProduct(Product product,String user_prefix) {
        return MemCachedUtil.addFavoriteProducts(product,user_prefix);
    }

    @Override
    public List<Product> queryFavoriteProductsFromMemcachedByPage(String user_prefix, Integer currentPage, Integer pageSize) {
        return null;
    }

    @Override
    public Integer queryCount(String user_prefix) {
        return MemCachedUtil.getTotalFavoriteProductsCount(user_prefix);
    }
}
