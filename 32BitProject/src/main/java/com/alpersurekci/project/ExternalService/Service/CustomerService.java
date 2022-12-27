package com.alpersurekci.project.ExternalService.Service;
/**
 * Müşterilerle ilgili işlemlerin yapıldığı servis interface'dir
 * @author Alper Sürekçi
 */

import com.alpersurekci.project.ExternalService.DAO.Entity.CustomerEntity;
import com.alpersurekci.project.Model.ReturnModel;
import com.alpersurekci.project.dto.CustomerDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;



public interface CustomerService {
    /**
     * Müşteri ekleme işlemi yapılır.
     * @param customerDto müşteri bilgileri
     * @return
     */
    //add customer
    ReturnModel addCustomer(CustomerDto customerDto);

    /**
     * Müşteri listeleme işlemleri yapılır.
     * @return
     */
    //list customers
    ReturnModel getCustomers();

    /**
     * Id'sine göre müşteri silme işlemi yapılır.
     * @param id
     * @return
     */
    //delete customer by id

    ReturnModel deleteCustomerById(Long id);

    /**
     * Id'sine göre müşteri çağırılır.
     * @param id müşteri id'si
     * @return
     */
    //get customer by id
    ReturnModel getCustomerById(Long id);

    /**
     * Müşteri arama işlemi yapılır
     * @param query aranacak kelime
     * @return
     */
    //search customers
    ReturnModel searchCustomer(String query);

    /**
     * Pagination ve sorting işlemleri kullanılarak müşteriler listelenir.
     * @param pageNo sayfa sayısı
     * @param pageSize sayfa da listelenecek müşteri sayısı
     * @param sortField sorting işleminin neye göre olacağı
     * @param sortDirection sortin işleminin yöntemi
     * @return
     */
    Page <CustomerEntity> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
