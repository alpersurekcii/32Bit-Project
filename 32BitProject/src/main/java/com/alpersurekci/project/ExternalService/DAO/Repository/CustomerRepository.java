package com.alpersurekci.project.ExternalService.DAO.Repository;

import com.alpersurekci.project.ExternalService.DAO.Entity.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    //Find customer by email
    @Query(value = "Select * from customers c where c.customer_email=:email", nativeQuery = true)
    Optional<CustomerEntity> findByEmail(String email);

    //Search customer query
    @Query(value = "SELECT * From customers c Where c.customer_name LIKE CONCAT('%', :query, '%') OR  c.customer_surname LIKE CONCAT('%', :query, '%')", nativeQuery = true)
    List<CustomerEntity> searchCustomer(String query);

    List<CustomerEntity> findAll(Sort sort);

    Page<CustomerEntity> findAll(Pageable pageable);

}
