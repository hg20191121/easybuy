package com.hisoft.service;

import com.hisoft.entity.Category;

import java.util.List;

public interface CategoryService extends BaseService<Category,Integer> {

    /**
     * 查找父分类id为id的子分类集合,用于index页面分层显示
     *
     * @return
     * @param id 父分类的id
     */
    default List<Category> queryCategoriesByParentId(Integer id) {
        return null;
    }


    /**
     * 查询所有等级为1的category
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
    default List<Category> queryCategoryByPage(Integer currentPage,Integer pageSize) {
        return null;
    }
}
