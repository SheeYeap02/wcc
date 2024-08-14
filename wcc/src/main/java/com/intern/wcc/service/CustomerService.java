package com.intern.wcc.service;

import com.intern.wcc.entity.Customer;
import com.intern.wcc.model.helper.SearchModel;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface CustomerService {

    Customer login(HttpServletRequest request, Customer customer);

    String logout(HttpServletRequest request);

    List<Customer> findAllCustomer(SearchModel searchModel);

    Customer findByUserId(String userId);

    boolean addCustomer(Customer customer);

    Customer updateCustomer(Customer customer);
}
