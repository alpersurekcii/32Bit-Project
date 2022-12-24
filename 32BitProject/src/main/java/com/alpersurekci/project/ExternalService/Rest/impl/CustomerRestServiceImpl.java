package com.alpersurekci.project.ExternalService.Rest.impl;

import com.alpersurekci.project.ExternalService.DAO.Entity.CustomerEntity;
import com.alpersurekci.project.ExternalService.Rest.AppConstants;
import com.alpersurekci.project.ExternalService.Rest.CustomerResponse;
import com.alpersurekci.project.ExternalService.Rest.CustomerRestService;
import com.alpersurekci.project.ExternalService.Service.CustomerService;
import com.alpersurekci.project.Model.ReturnModel;
import com.alpersurekci.project.dto.CustomerDto;
import com.alpersurekci.project.mapper.CustomerMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

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
    //http://localhost:8080/api/v1/customers?pageSize=5&pageNo=1&sortBy=customerName
    @Override
    @GetMapping("/customers")
    @ApiOperation(value ="List all customers")
    public CustomerResponse getCustomers( @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                     @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                     @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                     @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        Page<CustomerEntity> page = customerService.findPaginated(pageNo, pageSize, sortBy, sortDir);
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setCustomers(page.getContent().stream().map(CustomerMapper::entity2Dto).collect(Collectors.toList()));
        customerResponse.setLast(page.isLast());
        customerResponse.setTotalPages(page.getTotalPages());
        customerResponse.setPageNo(page.getNumber());
        customerResponse.setPageSize(page.getSize());
        customerResponse.setTotalElements(page.getTotalElements());
        return customerResponse;
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
