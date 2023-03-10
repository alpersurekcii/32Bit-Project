package com.alpersurekci.project.ExternalService.SOAP;
/**
 * SOAP web servisinin oluşturulduğu sınıftır.
 * @author Alper Sürekçi
 */

import com.alpersurekci.project.ExternalService.Service.CustomerService;
import com.alpersurekci.project.Model.ReturnModel;
import com.alpersurekci.project.customers.*;
import com.alpersurekci.project.dto.CustomerDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.ArrayList;
import java.util.List;
//SOAP SERVICE
@Log4j2
@Endpoint
public class CustomerEndpoint {
  private static final String NAMESPACE_URI = "http://localhost:8080/soap/customers";


    @Autowired
    CustomerService customerService;

    /**
     * Soap için müşteri id'sine göre müşteri çağrılması
     * @param request
     * @return
     */
    //get customer by id
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCustomerByIdRequest")
    @ResponsePayload
    public GetCustomerByIdResponse getCustomer(@RequestPayload GetCustomerByIdRequest request){
        GetCustomerByIdResponse response = new GetCustomerByIdResponse();
        CustomerInfo customerInfo = new CustomerInfo();
        ReturnModel returnModel = customerService.getCustomerById(request.getCustomerId());
        if(returnModel.isSuccessful()){
            log.info("Customer found successfully in SOAP web service");
            BeanUtils.copyProperties(returnModel.getResult().get(0), customerInfo);

            response.setCustomerInfo(customerInfo);
            return response;
        }else{

            response.setCustomerInfo(customerInfo);
        return response;
        }


    }

    /**
     * Soap web servisi için müşterileri listeleme yapılmıştır.
     * @return
     */
    //list all customers
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllCustomersRequest")
    @ResponsePayload
    public GetAllCustomersResponse getAllCustomers(){
        GetAllCustomersResponse response = new GetAllCustomersResponse();
        List<CustomerInfo> customerInfoList = new ArrayList<>();
        ReturnModel returnModel = customerService.getCustomers();
        if(returnModel.isSuccessful()) {
         log.info("List all customer CustomerEndpoint class");
            for (int i = 0; i < returnModel.getResult().size(); i++) {
                CustomerInfo customerInfos = new CustomerInfo();
                BeanUtils.copyProperties(returnModel.getResult().get(i), customerInfos);
                customerInfoList.add(customerInfos);
            }
            response.getCustomerInfo().addAll(customerInfoList);
            return response;
        }else{
            log.info("list all customer error customerEndpoint class");
            return response;
        }
    }

    /**
     * Müşteri ekleme işlemi yapılmıştır.
     * @param request
     * @return
     */
    //save customer
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addCustomerRequest")
    @ResponsePayload
    public AddCustomerResponse addCustomer(@RequestPayload AddCustomerRequest request){
        AddCustomerResponse addCustomerResponse = new AddCustomerResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerEmail(request.getEmail());
        customerDto.setCustomerName(request.getName());
        customerDto.setCustomerSurname(request.getSurname());

        ReturnModel returnModel = customerService.addCustomer(customerDto);
        if(returnModel.isSuccessful()){
            log.info("Customer added Successfully");
            CustomerInfo customerInfo = new CustomerInfo();
            BeanUtils.copyProperties(customerDto, customerInfo);
            addCustomerResponse.setCustomerInfo(customerInfo);
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Customer added successfully");
            addCustomerResponse.setServiceStatus(serviceStatus);
        }else{
            log.info("Customer already added");
            serviceStatus.setStatusCode("CONFLICT");
            serviceStatus.setMessage("Customer Already Added");
            addCustomerResponse.setServiceStatus(serviceStatus);
        }
        return addCustomerResponse;
    }

    /**
     * Müşteri id'sine göre müşteriyi silme işlemi yapılmıştır.
     * @param request
     * @return
     */
    //delete customer by id
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteCustomerRequest")
    @ResponsePayload
    public DeleteCustomerResponse deleteCustomer(@RequestPayload DeleteCustomerRequest request){
       ReturnModel returnModel = customerService.deleteCustomerById(request.getCustomerId());
        ServiceStatus serviceStatus = new ServiceStatus();
        if(returnModel.isSuccessful()){
            log.info("Customer deleted successfully");
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Customer deleted");
        }else{
            log.info("Customer didn't delete SOAP web service");
            serviceStatus.setStatusCode("FAILED");
            serviceStatus.setMessage("Customer not deleted");
        }
        DeleteCustomerResponse deleteCustomerResponse = new DeleteCustomerResponse();
        deleteCustomerResponse.setServiceStatus(serviceStatus);
        return deleteCustomerResponse;
    }

    /**
     * Müşterileri sayfada arama işlemi yapılmıştır
     * @param request
     * @return
     */
    //search customer
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "searchCustomersRequest")
    @ResponsePayload
    public SearchCustomersResponse searchCustomer(@RequestPayload SearchCustomersRequest request){
        ReturnModel returnModel = customerService.searchCustomer(request.getQuery());
        List<CustomerInfo> customerInfoList = new ArrayList<>();
        SearchCustomersResponse response = new SearchCustomersResponse();
        for(int i = 0 ; i< returnModel.getResult().size(); i++){
            CustomerInfo customerInfos = new CustomerInfo();
            BeanUtils.copyProperties(returnModel.getResult().get(i), customerInfos);
            customerInfoList.add(customerInfos);
        }
        response.getCustomerInfo().addAll(customerInfoList);
        return response;

    }

}
