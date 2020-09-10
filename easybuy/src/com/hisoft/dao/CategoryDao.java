package com.hisoft.dao;

import com.hisoft.entity.Category;

import java.util.List;

public interface CategoryDao extends BaseDao<Category,Integer>{

    /**
     * 查询父category的id为id的子category集合(首页分层展示category时)
     * 产品和子级分类都为空！
     * @param id
     * @return
     */
    default List<Category> queryCategoriesByParentId(Integer id) {
        return null;
    }


    /**
     * 查询所有等级为1的category,并且将等级为2的和等级为3的也查询出来
     * 也就是Category的c_children属性必须有值(最后一级的category的c_children属性为null)
     * @return
     */
    default List<Category> queryLevel_1_Categories() {
        return null;
    }

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @return
     */
    default List<Category> queryCategoryByPage(Integer currentPage, Integer pageSize) {
        return null;
    }
}
