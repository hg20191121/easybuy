package com.hisoft.service;

import com.hisoft.entity.Address;

import java.util.List;

public interface AddressService extends BaseService<Address,Integer> {

    default List<Address> queryAddressByUserId(Integer id) {
        return null;
    }

}
