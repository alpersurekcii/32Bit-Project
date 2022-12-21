package com.alpersurekci.project.mapper;

import com.alpersurekci.project.ExternalService.DAO.Entity.CustomerEntity;
import com.alpersurekci.project.dto.CustomerDto;

public class CustomerMapper {

    // entity to dto
    public static CustomerDto entity2Dto(CustomerEntity customerEntity){
        return CustomerDto.builder().customerName(customerEntity.getCustomerName())
                .customerSurname(customerEntity.getCustomerSurname())
                .id(customerEntity.getId())
                .customerEmail(customerEntity.getCustomerEmail())
                .build();
    }

    //dto to entity
    public static CustomerEntity dto2Entity(CustomerDto customerDto){
        return CustomerEntity.builder()
                .customerName(customerDto.getCustomerName())
                .customerSurname(customerDto.getCustomerSurname())
                .id(customerDto.getId())
                .customerEmail(customerDto.getCustomerEmail())
                .build();
    }

}
