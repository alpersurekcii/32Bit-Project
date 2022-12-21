package com.alpersurekci.project.ExternalService.Rest;


import com.alpersurekci.project.Model.ReturnModel;
import com.alpersurekci.project.dto.CustomerDto;

public interface CustomerRestService {
    //get customer by id
    ReturnModel getCustomerById(Long id);
    //list customers
    ReturnModel getCustomers();
    //add customer
    ReturnModel addCustomer(CustomerDto customerDto);
    //delete customer by id
    ReturnModel deleteCustomerById(Long id);
    //search customer
    ReturnModel searchCustomers(String query);
}
