package com.intern.wcc.controller;

import com.intern.wcc.entity.Customer;
import com.intern.wcc.model.helper.SearchModel;
import com.intern.wcc.service.CustomerServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("wcc/customer")
@Slf4j
public class CustomerController {
    private final CustomerServiceImpl customerService;

    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/login")
    public ResponseEntity<Customer> login(HttpServletRequest request, @RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.login(request, customer), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        return new ResponseEntity<>(customerService.logout(request), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<List<Customer>> findAllCustomer (SearchModel searchModel) {
        List<Customer> customers = customerService.findAllCustomer(searchModel);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/findByUserId")
    public ResponseEntity<Customer> findByUserId (@RequestParam("userId") String userId) {
        Customer customer = customerService.findByUserId(userId);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> addCustomer(@RequestBody Customer customer) {
        customerService.addCustomer(customer);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
        Customer updateCustomer = customerService.updateCustomer(customer);
        return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
    }


}
