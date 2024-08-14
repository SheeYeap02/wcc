package com.intern.wcc.service;

import com.intern.wcc.dao.CustomerDao;
import com.intern.wcc.entity.Customer;
import com.intern.wcc.exception.UserNotFoundException;
import com.intern.wcc.model.helper.SearchModel;
import com.intern.wcc.utils.BeanCompare;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerDao customerDao;

    @Autowired
    public CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public Customer login(HttpServletRequest request, Customer customer) {
        log.info("Customer Details: {}, {}", customer.getUserId(), customer.getPassword());

        String password = customer.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        Customer loginUser = findByUserId(customer.getUserId());

        // No result, return login fail msg
        if(loginUser == null) {
            log.info("No Customer Found");
            return null;
        }

        // Compare password, unmatched return error msg
        if(!loginUser.getPassword().equals(password)) {
            log.info("Password Unmatched");
            return null;
        }

        // Login success, place applicant email into session
        request.getSession().setAttribute("customer", loginUser.getUserId());
        return loginUser;
    }

    @Override
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("customer");
        return "Logout Success";
    }

    @Override
    public List<Customer> findAllCustomer(SearchModel searchModel) {
        return customerDao.findAllCustomer(searchModel);
    }

    @Override
    public Customer findByUserId(String userId) {
        return customerDao.findByUserId(userId);
    }

    public boolean addCustomer(Customer customer) {
        String password = customer.getPassword();
        customer.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        customer.setRegisteredDate(LocalDateTime.now());
        customer.setStatus("Pending");

        log.info("Customer Data: {}", customer);
        customerDao.addCustomer(customer);
        return true;
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        Customer existingCustomer = customerDao.findByUserId(customer.getUserId());
        if (existingCustomer == null) {
            throw new UserNotFoundException("Customer not found: " + customer.getUserId());
        }

        if (BeanCompare.hasDifferences(customer, existingCustomer)) {
            BeanUtils.copyProperties(customer, existingCustomer);
            return customerDao.updateCustomer(existingCustomer);
        } else {
            return existingCustomer;
        }
    }

}
