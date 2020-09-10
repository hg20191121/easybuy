package com.hisoft.dao;

import com.hisoft.entity.Category;
import com.hisoft.entity.User;

import java.util.List;

public interface UserDao extends BaseDao<User,Integer> {

    /**
     * 根据用户名和密码查询用户,常用来登录
     * @param username
     * @param password
     * @return
     */
    User queryUserByUserNameAndPassword(String username,String password);

    /**
     * 根据用户名查询用户名是否存在
     * @param username
     * @return
     */
    User queryUserByUserName(String username);

    /**
     * 根据登录名查询登录明是否存在
     * @param loginName
     * @return
     */
    User queryUserByLoginName(String loginName);


    /**
     * 分页查询
     * @param currentPage   当前查询的第几页
     * @param pageSize      查询的数量
     * @return
     */
    List<User> queryUsersByPage(Integer currentPage, Integer pageSize);


}
