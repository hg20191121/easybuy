package com.hisoft.service;

import com.hisoft.entity.Category;
import com.hisoft.entity.User;

import java.util.List;

public interface UserService extends BaseService<User, Integer> {

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    User queryUserByUserNameAndPassword(String username, String password);

    /**
     * 根据用户名查询用户名是否存在
     *
     * @param username
     * @return
     */
    User queryUserByUserName(String username);

    /**
     * 根据登录名查询登录明是否存在
     *
     * @param loginName
     * @return
     */
    User queryUserByLoginName(String loginName);


    List<User> queryUsersByPage(Integer currentPage, Integer pageSize);

}
