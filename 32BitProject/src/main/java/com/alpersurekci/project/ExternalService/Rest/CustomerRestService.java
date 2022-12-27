package com.alpersurekci.project.ExternalService.Rest;

/**
 * Rest service için interfacedir.
 * @author Alper Sürekçi
 */

import com.alpersurekci.project.Model.ReturnModel;
import com.alpersurekci.project.dto.CustomerDto;
import com.alpersurekci.project.dto.UserDto;

public interface CustomerRestService {
    /**
     * Id'sine göre müşterinin çağrıldığı metoddur.
     * @param id
     * @return
     */
    //get customer by id
    ReturnModel getCustomerById(Long id);

    /**
     * Pagination ve sorting yöntemine göre müşteriler listelenir.
     * @param pageNo sayfa sayısı
     * @param pageSize bir sayfa da listenilen müşteri sayısı
     * @param sortBy
     * @param sortDir
     * @return
     */
    //list customers
    ReturnModel getCustomers(int pageNo,int pageSize,String sortBy,String sortDir);

    /**
     * Yeni müşteri eklenir
     * @param customerDto müşteri bilgileri
     * @return
     */
    //add customer
    ReturnModel addCustomer(CustomerDto customerDto);

    /**
     * Id'sine göre müşteri silme işlemi.
     * @param id müşteri id'si
     * @return
     */
    //delete customer by id
    ReturnModel deleteCustomerById(Long id);

    /**
     * Müşteri arama işlemi.
     * @param query aranacak sorgu kelimesi
     * @return
     */
    //search customer
    ReturnModel searchCustomers(String query);


}
