package com.hisoft.util;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.hisoft.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class MemCachedUtil {

    static MemCachedClient client = null;

    static String[] connectUrls = new String[]{"127.0.0.1:11211"};

    private static final String FAVORITE_PRODUCTS_LABEL = "favorite_products";

    static {
        String[] attr = connectUrls;
        client = new MemCachedClient();
        SockIOPool pool = SockIOPool.getInstance();
        pool.setServers(attr);
        pool.setWeights(new Integer[]{3});
        pool.setInitConn(5);
        pool.setMinConn(5);
        pool.setMaxConn(200);
        pool.setMaxIdle(1000 * 30 * 30);
        pool.setMaintSleep(30);
        pool.setNagle(false);
        pool.setSocketConnectTO(30);
        pool.initialize();
    }

    public static boolean addFavoriteProducts(Product product,String user_prefix) {
        Object o = client.get(user_prefix+FAVORITE_PRODUCTS_LABEL);
        ArrayList<Product> products;
        if (o == null) {
            products = new ArrayList<>();
        }else {
            products = (ArrayList<Product>) o;
        }
        products.add(product);
       return  client.set(user_prefix+FAVORITE_PRODUCTS_LABEL, products);
    }

    public static List<Product> getFavoriteProducts(String user_prefix) {
        List<Product> products = (List<Product>) client.get(user_prefix + FAVORITE_PRODUCTS_LABEL);
        return products == null?new ArrayList<>(): products;
    }

    public static List<Product> getFavoriteProductsByPage(String user_prefix,Integer currentPage,Integer pageSize) {
        List<Product> products = (List<Product>) client.get(user_prefix + FAVORITE_PRODUCTS_LABEL);
        if (products == null || products.size() == 0) {
            return null;
        }
        Integer begin = (currentPage-1) * pageSize;
        return products.subList(begin,begin+pageSize);
    }

    public static Integer getTotalFavoriteProductsCount(String user_prefix) {
        return getFavoriteProducts(user_prefix).size();
    }
}
