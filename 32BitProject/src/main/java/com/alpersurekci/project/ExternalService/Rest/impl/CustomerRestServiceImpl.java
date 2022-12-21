package com.alpersurekci.project.ExternalService.Rest.impl;

import com.alpersurekci.project.ExternalService.Rest.CustomerRestService;
import com.alpersurekci.project.ExternalService.Service.CustomerService;
import com.alpersurekci.project.Model.ReturnModel;
import com.alpersurekci.project.dto.CustomerDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
//Rest Service
@RestController
@CrossOrigin
@RequestMapping("/api/v1")
@Api(value = "Customer Api Documentation")

public class CustomerRestServiceImpl implements CustomerRestService {


    CustomerService customerService;

    public CustomerRestServiceImpl(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ApiOperation(value = "get customer by id")
    @GetMapping("/customers/{id}")
    @Override
    public ReturnModel getCustomerById(@PathVariable("id") Long id) {
        ReturnModel returnModel = customerService.getCustomerById(id);
        return returnModel;
    }

    @Override
    @GetMapping("/customers")
    @ApiOperation(value ="List all customers")
    public ReturnModel getCustomers() {
       ReturnModel returnModel = customerService.getCustomers();
        return returnModel;
    }

    @PostMapping("/customers")
    @Override
    @ApiOperation(value = "Add new customer")
    public ReturnModel addCustomer(CustomerDto customerDto) {
        ReturnModel returnModel = customerService.addCustomer(customerDto);
        return returnModel;
    }

    @DeleteMapping("/customers/{id}")
    @Override
    @ApiOperation(value = "delete customer by id")
    public ReturnModel deleteCustomerById(@PathVariable("id") Long id) {
        ReturnModel returnModel = customerService.deleteCustomerById(id);
        return returnModel;
    }

    @ApiOperation(value = "Search customers")
    @Override
    @GetMapping("/customers/search")
    public ReturnModel searchCustomers(@RequestParam(value = "query") String query) {
        ReturnModel returnModel = customerService.searchCustomer(query);
        return returnModel;
    }
}
