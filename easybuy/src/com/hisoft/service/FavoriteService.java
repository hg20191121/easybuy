package com.hisoft.service;

import com.hisoft.entity.Product;

import java.util.List;

public interface FavoriteService {



    default List<Product> queryFavoriteProductsFromMemcached(String user_prefix) {
        return null;
    }

    default Boolean addFavoriteProduct(Product product,String user_prefix) {
        return null;
    }

    default List<Product> queryFavoriteProductsFromMemcachedByPage(String user_prefix,Integer currentPage,Integer pageSize) {
        return null;
    }

    default Integer queryCount(String user_prefix) {
        return null;
    }
}
