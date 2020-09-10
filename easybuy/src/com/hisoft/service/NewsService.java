package com.hisoft.service;

import com.hisoft.entity.Category;
import com.hisoft.entity.News;

import java.sql.SQLException;
import java.util.List;

public interface NewsService extends BaseService<News,Integer> {
    List<News> queryNewsByDateByOrder(Integer n) ;

    default List<News> queryNewsByPage(Integer currentPage,Integer pageSize){return null;}
}
