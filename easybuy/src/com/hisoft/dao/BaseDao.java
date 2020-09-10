package com.hisoft.dao;

import java.sql.SQLException;
import java.util.List;


/**
 * 所有dao抽像层接口的父接口,定义了基本方法
 *
 *
 * @param <T>   数据类型
 * @param <K>   数据的主键类型
 */
public interface BaseDao<T,K> {

    /**
     * 查询所有
     * @return
     */
    default List<T> queryAll() throws SQLException {
        return null;
    }

    /**
     * 根据主键查询一条数据
     * @param k 主键
     * @return  返回一条数据
     */
    default T queryOneByPrimaryKey(K k) throws SQLException {
        return null;
    }

    /**
     * 查询总数量(select count(*)...)
     * @return
     */
    default Integer queryCount() throws SQLException {
        return null;
    }

    /**
     * 插入一条数据
     * @param t 插入的数据
     * @return
     */
    default Integer insertOne(T t) {
        return null;
    }


    /**
     * 更新一条数据
     * @param t 需要更新的数据
     * @param k 更新数据的主键
     * @return
     */
   default Integer updateOne(T t,K k) {
       return null;
   }

    /**
     * 删除一条数据
     * @param k
     * @return
     */
   default Integer remove(K k) throws SQLException {
        return null;
   }

}
