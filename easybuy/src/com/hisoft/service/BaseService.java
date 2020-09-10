package com.hisoft.service;

import java.sql.SQLException;
import java.util.List;


/**
 * 所有service抽像层接口的父接口,定义了基本方法
 *
 *
 * @param <T>   数据类型
 * @param <K>   数据的主键类型
 */
public interface BaseService<T,K> {

        /**
         * 查询所有
         * @return
         */
        List<T> queryAll() ;

        /**
         * 根据主键查询一条数据
         * @param k 主键
         * @return  返回一条数据
         */
        T queryOneByPrimaryKey(K k);

        /**
         * 查询总数量(select count(*)...)
         * @return
         */
        Integer queryCount();

        /**
         * 插入一条数据
         * @param t 插入的数据
         * @return
         */
        Integer insertOne(T t);


        /**
         * 更新一条数据
         * @param t 需要更新的数据
         * @param k 更新数据的主键
         * @return
         */
        Integer updateOne(T t,K k);


        /**
         * 根据主键删除数据
         * @param k
         * @return
         */
        Integer remove(K k);


}
