package com.hisoft.service.impl;

import com.hisoft.dao.FavoriteDao;
import com.hisoft.dao.impl.FavoriteDaoImpl;
import com.hisoft.entity.Product;
import com.hisoft.service.FavoriteService;

import java.util.List;

public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public List<Product> queryFavoriteProductsFromMemcached(String user_prefix) {
        return favoriteDao.queryFavoriteProductsFromMemcached(user_prefix);
    }

    @Override
    public Boolean addFavoriteProduct(Product product, String user_prefix) {
        return favoriteDao.addFavoriteProduct(product,user_prefix);
    }

    @Override
    public List<Product> queryFavoriteProductsFromMemcachedByPage(String user_prefix, Integer currentPage, Integer pageSize) {
        return favoriteDao.queryFavoriteProductsFromMemcachedByPage(user_prefix,currentPage,pageSize);
    }

    @Override
    public Integer queryCount(String prefix) {
        return favoriteDao.queryCount(prefix);
    }
}
