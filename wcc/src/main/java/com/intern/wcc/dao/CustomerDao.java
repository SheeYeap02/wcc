package com.intern.wcc.dao;

import com.intern.wcc.entity.Customer;
import com.intern.wcc.model.helper.SearchModel;

import java.util.List;

public interface CustomerDao  {

    List<Customer> findAllCustomer(SearchModel searchModel);
    Customer findByUserId(String userId);
    void addCustomer(Customer customer);
    Customer updateCustomer(Customer customer);
}
