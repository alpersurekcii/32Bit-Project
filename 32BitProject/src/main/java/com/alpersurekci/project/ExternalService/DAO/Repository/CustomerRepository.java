package com.alpersurekci.project.ExternalService.DAO.Repository;
/**
 * Müşteri reposunun olduğu sınıftır,
 * database işlemleri bu sınıfta gerçekleşmektedir.
 * @author Alper Sürekçi
 */

import com.alpersurekci.project.ExternalService.DAO.Entity.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    /**
     * emaile göre databaseden müşteri çekme işlemi
     * @param email müşteri emaili
     * @return
     */
    //Find customer by email
    @Query(value = "Select * from customers c where c.customer_email=:email", nativeQuery = true)
    Optional<CustomerEntity> findByEmail(String email);

    /**
     * Müşteri araması için sorgu
     * @param query müşteri aramak için search sorgusu
     * @return
     */
    //Search customer query
    @Query(value = "SELECT * From customers c Where c.customer_name LIKE CONCAT('%', :query, '%') OR  c.customer_surname LIKE CONCAT('%', :query, '%')", nativeQuery = true)
    List<CustomerEntity> searchCustomer(String query);

    /**
     * Müşteri listesi sortlaması
     * @param sort hangi metoda göre sortlanacağı için parametre
     * @return
     */
    List<CustomerEntity> findAll(Sort sort);

    /**
     * Müşteri listesi pagination işlemi.
     * @param pageable
     * @return
     */
    Page<CustomerEntity> findAll(Pageable pageable);

}
