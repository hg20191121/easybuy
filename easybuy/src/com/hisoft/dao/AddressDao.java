package com.hisoft.dao;

import com.hisoft.entity.Address;

import java.util.List;

public interface AddressDao extends BaseDao<Address,Integer> {

    default List<Address> queryAddressByUserId(Integer id) {
        return null;
    }



}
