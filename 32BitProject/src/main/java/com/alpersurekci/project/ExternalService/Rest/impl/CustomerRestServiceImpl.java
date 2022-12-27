package com.alpersurekci.project.ExternalService.Rest.impl;
/**
 * Rest service'in oluşturulduğu sınıftır.
 * Bu sınıfta REST web servisi oluşturulmuş ve müşterilerle
 * ilgili işlemler yapılmıştır.
 * @author Alper Sürekçi
 */

import com.alpersurekci.project.ExternalService.DAO.Entity.CustomerEntity;
import com.alpersurekci.project.ExternalService.Rest.AppConstants;
import com.alpersurekci.project.ExternalService.Rest.CustomerRestService;
import com.alpersurekci.project.ExternalService.Service.CustomerService;
import com.alpersurekci.project.Model.ReturnModel;
import com.alpersurekci.project.Security.JwtUtils;
import com.alpersurekci.project.dto.CustomerDto;
import com.alpersurekci.project.dto.UserDto;
import com.alpersurekci.project.mapper.CustomerMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Log4j2
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

    /**
     * Id'ye göre müşteri çağırılması işlemi yapılmıştır.
     * @param id müşteri id'si
     * @return
     */
    @ApiOperation(value = "get customer by id")
    @GetMapping("/customers/{id}")
    @Override
    public ReturnModel getCustomerById(@PathVariable("id") Long id) {
        ReturnModel returnModel = customerService.getCustomerById(id);
        if(returnModel.isSuccessful()){
            log.info("Customer found successfully(rest method)");
        }else{
            log.info("Customer didn't find (rest method)");
        }
        return returnModel;

    }

    /**
     * Müşterileri listeleme işlemi.
     * @param pageNo sayfa sayısı
     * @param pageSize sayfada listelenecek müşteri sayısı
     * @param sortBy hangi alana göre müşteri sıralanacak
     * @param sortDir sıralanma yöntemi
     * @return
     */
    //http://localhost:8080/api/v1/customers?pageSize=5&pageNo=1&sortBy=customerName

    @Override
    @GetMapping("/customers")
    @ApiOperation(value ="List all customers")
    public ReturnModel getCustomers( @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                     @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                     @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                     @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        Page<CustomerEntity> page = customerService.findPaginated(pageNo, pageSize, sortBy, sortDir);
        ReturnModel returnModel = new ReturnModel();
        returnModel.setResult(page.getContent().stream().map(CustomerMapper::entity2Dto).collect(Collectors.toList()));
        returnModel.setCode("SUCCESS");
        returnModel.setSuccessful(true);
        returnModel.setMessage("Müşteriler listelendi");
        return returnModel;
    }

    /**
     * Müşteri ekleme işlemi.
     * @param customerDto müşteri bilgileri
     * @return
     */
    @PostMapping("/customers")
    @Override
    @ApiOperation(value = "Add new customer")
    public ReturnModel addCustomer(CustomerDto customerDto) {
        ReturnModel returnModel = customerService.addCustomer(customerDto);
        if(returnModel.isSuccessful()){
            log.info("Customer added successfully(rest method)");
            return returnModel;
        }else{
            log.info("Could not add customer(rest method) ");
            return returnModel;
        }

    }

    /**
     * Müşteriyi id'ye göre silme işlemi
     * @param id Müşteri id'si
     * @return
     */
    @DeleteMapping("/customers/{id}")
    @Override
    @ApiOperation(value = "delete customer by id")
    public ReturnModel deleteCustomerById(@PathVariable("id") Long id) {
        ReturnModel returnModel = customerService.deleteCustomerById(id);
        if(returnModel.isSuccessful()){
            log.info("Customer deleted successfully(rest method)");
            return returnModel;
        }else{
            log.info("Customer didn't delete(rest method)");
            return returnModel;
        }
    }

    /**
     * Müşterileri arama işlemi
     * @param query aranacak kelime
     * @return
     */
    @ApiOperation(value = "Search customers")
    @Override
    @GetMapping("/customers/search")
    public ReturnModel searchCustomers(@RequestParam(value = "query") String query) {
        ReturnModel returnModel = customerService.searchCustomer(query);
        return returnModel;
    }

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;
    @PostMapping("/auth")
    public String login(UserDto userDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getUserName(), userDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        System.out.println(jwt);
        return "redirect:/";
    }


}
