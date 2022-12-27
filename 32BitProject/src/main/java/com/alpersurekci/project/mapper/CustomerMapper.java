package com.alpersurekci.project.mapper;
/**
 * CustomerMapper sınıfı entityden dto veya dto'dan entity'e
 * objeleri çeviren sınıftır
 * @author Alper Sürekçi
 */

import com.alpersurekci.project.ExternalService.DAO.Entity.CustomerEntity;
import com.alpersurekci.project.dto.CustomerDto;

public class CustomerMapper {
    /**
     * Entity objesinden dto objesine dönüştürme
     * @param customerEntity müşteri entity
     * @return
     */
    // entity to dto
    public static CustomerDto entity2Dto(CustomerEntity customerEntity){
        return CustomerDto.builder().customerName(customerEntity.getCustomerName())
                .customerSurname(customerEntity.getCustomerSurname())
                .id(customerEntity.getId())
                .customerEmail(customerEntity.getCustomerEmail())
                .build();
    }

    /**
     * Dto objesinden entity objesine dönüştürme
     * @param customerDto müşteri dto
     * @return
     */
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
