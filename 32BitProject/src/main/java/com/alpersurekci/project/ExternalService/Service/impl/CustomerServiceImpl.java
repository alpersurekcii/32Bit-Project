package com.alpersurekci.project.ExternalService.Service.impl;

import com.alpersurekci.project.ExternalService.DAO.Entity.CustomerEntity;
import com.alpersurekci.project.ExternalService.DAO.Entity.UserEntity;
import com.alpersurekci.project.ExternalService.DAO.Repository.CustomerRepository;
import com.alpersurekci.project.ExternalService.Service.CustomerService;
import com.alpersurekci.project.Model.ReturnModel;
import com.alpersurekci.project.dto.CustomerDto;
import com.alpersurekci.project.mapper.CustomerMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CustomerServiceImpl implements CustomerService {

    CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository ) {
        this.customerRepository = customerRepository;

    }
    //add customer
    @Override
    public ReturnModel addCustomer(CustomerDto customerDto) {
        CustomerEntity customerEntity = CustomerMapper.dto2Entity(customerDto);
        ReturnModel returnModel = new ReturnModel();
        List<CustomerDto> customerDtos = new ArrayList<>();
        Optional<CustomerEntity> customerEntity1 = customerRepository.findByEmail(customerEntity.getCustomerEmail());
        if(customerEntity1.isPresent()){
            returnModel.setMessage("Kullanıcı kayıtlı");
            returnModel.setResult(customerDtos);
            returnModel.setSuccessful(false);
            returnModel.setCode("FAILED");
            return returnModel;
        }else{
       CustomerEntity  customer=  customerRepository.save(customerEntity); //save customer to db


        customerDtos.add(customerDto);

        if(customer.equals(customerEntity)){
            returnModel.setMessage("Müşteri eklendi.");
            returnModel.setSuccessful(true);
            returnModel.setResult(customerDtos);
            returnModel.setCode("SUCCESS");

        }
        else{
            returnModel.setSuccessful(false);
            returnModel.setMessage("Müşteri eklenemedi");
            returnModel.setResult(customerDtos);
            returnModel.setCode("FAILED");
        }
        return returnModel;
    }}

    //list all customers
    @Override
    public ReturnModel getCustomers() {
       List<CustomerEntity> customerEntity = customerRepository.findAll();
       List<CustomerDto> customerDtos= customerEntity.stream().map(CustomerMapper :: entity2Dto).collect(Collectors.toList());
        return ReturnModel.builder().result(customerDtos).code("SUCCESS").message("Müşteriler").successful(true).build();
    }

    //delete customer by id
    @Override
    public ReturnModel deleteCustomerById(Long id) {
        Optional<CustomerEntity> customerEntity = customerRepository.findById(id); //find customer by id to db
        if(customerEntity.isPresent()){

            customerRepository.deleteById(id); // delete customer
               return ReturnModel.builder()
                        .message("Müşteri başarıyla silindi")
                        .successful(true)
                        .code("SUCCESS")
                        .result(customerEntity.stream().map(CustomerMapper::entity2Dto).collect(Collectors.toList()))
                        .build();

        }
        else{
             return ReturnModel.builder()
                    .message("Müşteri silinemedi")
                    .successful(false)
                    .code("FAILED")
                    .result(customerEntity.stream().map(CustomerMapper::entity2Dto).collect(Collectors.toList()))
                     .build();
        }


    }

    //get customer by id
    @Override
    public ReturnModel getCustomerById(Long id) {
         Optional<CustomerEntity> customerEntity = customerRepository.findById(id); //find customer
        if(customerEntity.isPresent()) {
            return ReturnModel
                    .builder()
                    .code("")
                    .result(customerEntity.stream().map(CustomerMapper::entity2Dto).collect(Collectors.toList()))
                    .successful(true)
                    .message("Müşteri bulundu")
                    .code("SUCCESS")
                    .build();
        }
        else{
            return ReturnModel
                    .builder()
                    .code("")
                    .result(customerEntity.stream().map(CustomerMapper::entity2Dto).collect(Collectors.toList()))
                    .successful(false)
                    .message("Müşteri bulunamadı")
                    .build();
        }
    }

    //search customers
    @Override
    public ReturnModel searchCustomer(String query) {
         List<CustomerEntity> customer = customerRepository.searchCustomer(query);//search db
         ReturnModel returnModel = new ReturnModel();
         returnModel.setResult((customer.stream().map(CustomerMapper::entity2Dto).collect(Collectors.toList())));
         returnModel.setMessage("Arama basarili");
         returnModel.setSuccessful(true);
         returnModel.setCode("SUCCESS");
         return returnModel;
    }


}
