package com.alpersurekci.project.ExternalService.Service;

import com.alpersurekci.project.Model.ReturnModel;
import com.alpersurekci.project.dto.CustomerDto;
import org.springframework.stereotype.Service;



public interface CustomerService {
    //add customer
    ReturnModel addCustomer(CustomerDto customerDto);
    //list customers
    ReturnModel getCustomers();
    //delete customer by id
    ReturnModel deleteCustomerById(Long id);
    //get customer by id
    ReturnModel getCustomerById(Long id);
    //search customers
    ReturnModel searchCustomer(String query);
}
