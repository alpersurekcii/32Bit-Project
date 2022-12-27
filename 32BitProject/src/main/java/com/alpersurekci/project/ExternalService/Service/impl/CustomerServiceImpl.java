package com.alpersurekci.project.ExternalService.Service.impl;
/**
 * Bu sınıfta müşteri ile ilgili olan işlemler veri tabanı bağlantılarıyla
 * gerçekleştirilmiştir. Bu işlemler :
 * Müşteriyi veri tabanına ekleme
 * Müşteriyi id'sine göre veri tabanından silme
 * Müşteriyi id'sine göre veri tabanından çağırma
 * Müşterileri listeleme
 * Müşterileri veri tabanında arama'dır.
 * @author Alper Sürekçi
 */

import com.alpersurekci.project.ExternalService.DAO.Entity.CustomerEntity;
import com.alpersurekci.project.ExternalService.DAO.Entity.UserEntity;
import com.alpersurekci.project.ExternalService.DAO.Repository.CustomerRepository;
import com.alpersurekci.project.ExternalService.Service.CustomerService;
import com.alpersurekci.project.Model.ReturnModel;
import com.alpersurekci.project.dto.CustomerDto;
import com.alpersurekci.project.mapper.CustomerMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class CustomerServiceImpl implements CustomerService {

    CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository ) {
        this.customerRepository = customerRepository;

    }

    /**
     * Müşteriyi veri tabanına kaydetme işlemi yapılmıştır.
     * @param customerDto Müşteri bilgileri
     * @return
     */
    //add customer
    @Override
    public ReturnModel addCustomer(CustomerDto customerDto) {
        CustomerEntity customerEntity = CustomerMapper.dto2Entity(customerDto);
        ReturnModel returnModel = new ReturnModel();
        List<CustomerDto> customerDtos = new ArrayList<>();
        Optional<CustomerEntity> customerEntity1 = customerRepository.findByEmail(customerEntity.getCustomerEmail());
        if(customerEntity1.isPresent()){
            returnModel.setMessage("customer registered");
            returnModel.setResult(customerDtos);
            returnModel.setSuccessful(false);
            returnModel.setCode("FAILED");
            log.info("customer registered");
            return returnModel;
        }else{
       CustomerEntity  customer=  customerRepository.save(customerEntity); //save customer to db


        customerDtos.add(customerDto);

        if(customer.equals(customerEntity)){
            returnModel.setMessage("Customer added successfully");
            returnModel.setSuccessful(true);
            returnModel.setResult(customerDtos);
            returnModel.setCode("SUCCESS");
            log.info("Customer added successfully");
        }
        else{
            returnModel.setSuccessful(false);
            returnModel.setMessage("Customer didn't add");
            returnModel.setResult(customerDtos);
            returnModel.setCode("FAILED");
            log.info("Customer didn't add");
        }
        return returnModel;
    }}

    /**
     * Müşterileri listeleme işlemi yapılmıştır.
     * @return
     */
    //list all customers
    @Override
    public ReturnModel getCustomers() {
       List<CustomerEntity> customerEntity = customerRepository.findAll();
       List<CustomerDto> customerDtos= customerEntity.stream().map(CustomerMapper :: entity2Dto).collect(Collectors.toList());
       log.info("List all customers");
        return ReturnModel.builder().result(customerDtos).code("SUCCESS").message("Customers").successful(true).build();
    }

    /**
     * Müşteri id'sine göre müşteriyi veritabanından
     * silme işlemi yapılmıştır.
     * @param id Müşteri id'si
     * @return
     */
    //delete customer by id
    @Override
    public ReturnModel deleteCustomerById(Long id) {
        Optional<CustomerEntity> customerEntity = customerRepository.findById(id); //find customer by id to db
        if(customerEntity.isPresent()){

            customerRepository.deleteById(id); // delete customer
            log.info("Customer deleted successfully");
            return ReturnModel.builder()
                        .message("Customer deleted successfully")
                        .successful(true)
                        .code("SUCCESS")
                        .result(customerEntity.stream().map(CustomerMapper::entity2Dto).collect(Collectors.toList()))
                        .build();

        }
        else{
            log.info("customer could not be deleted");
             return ReturnModel.builder()
                    .message("customer could not be deleted")
                    .successful(false)
                    .code("FAILED")
                    .result(customerEntity.stream().map(CustomerMapper::entity2Dto).collect(Collectors.toList()))
                     .build();
        }


    }

    /**
     * Müşteri id'sine göre müşteriyi veri tabanından
     * çekme işlemi yapılmıştır.
     * @param id Müşteri id'si
     * @return
     */
    //get customer by id
    @Override
    public ReturnModel getCustomerById(Long id) {
         Optional<CustomerEntity> customerEntity = customerRepository.findById(id); //find customer
        if(customerEntity.isPresent()) {
            log.info("Customer found successfully");
            return ReturnModel
                    .builder()

                    .result(customerEntity.stream().map(CustomerMapper::entity2Dto).collect(Collectors.toList()))
                    .successful(true)
                    .message("Customer found successfully")
                    .code("SUCCESS")
                    .build();
        }
        else{
            log.info("Customer not found");
            return ReturnModel
                    .builder()
                    .code("FAILED")
                    .result(customerEntity.stream().map(CustomerMapper::entity2Dto).collect(Collectors.toList()))
                    .successful(false)
                    .message("Customer not found")
                    .build();
        }
    }

    /**
     * Müşteriyi veri tabanında arama işlemi yapılmıştır.
     * @param query aranacak kelime
     * @return
     */
    //search customers
    @Override
    public ReturnModel searchCustomer(String query) {
         List<CustomerEntity> customer = customerRepository.searchCustomer(query);//search db
        log.info("Customers found");
         ReturnModel returnModel = new ReturnModel();
         returnModel.setResult((customer.stream().map(CustomerMapper::entity2Dto).collect(Collectors.toList())));
         returnModel.setMessage("Searched Successfull");
         returnModel.setSuccessful(true);
         returnModel.setCode("SUCCESS");
         return returnModel;
    }


    /**
     * Müşterileri paginating ve sortinge göre listeleme işlemi yapılmıştır.
     * @param pageNo sayfa numarası
     * @param pageSize sayfada bulunacak müşteri sayısı
     * @param sortField sayfada sortlanacak alan
     * @param sortDirection sortlanma metodu
     * @return
     */
    @Override
    public Page<CustomerEntity> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
        return this.customerRepository.findAll(pageable);
    }


}
