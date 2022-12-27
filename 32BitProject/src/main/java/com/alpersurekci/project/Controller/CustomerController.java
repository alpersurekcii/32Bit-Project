package com.alpersurekci.project.Controller;

import com.alpersurekci.project.ExternalService.DAO.Entity.CustomerEntity;
import com.alpersurekci.project.ExternalService.Service.CustomerService;
import com.alpersurekci.project.Model.ReturnModel;
import com.alpersurekci.project.dto.CustomerDto;


import com.alpersurekci.project.mapper.CustomerMapper;
import jakarta.jws.WebParam;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Müşteri controlleri.
 * Bu sınıfta kullanıcı isteklerine göre işlemler yapılmıştır.
 * Bu işlemler arasında müşterileri listeleme, id'sine göre müşteri silme,
 * id'ye göre müşteri çağırımı, müşterileri listeleme ve müşteriyi search etme
 * olmak üzere 5 farklı özellik içermektedir.
 * @author Alper Sürekçi
 */

@Log4j2

@Controller
public class CustomerController {

    CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * http://localhost:8080/save/customer üzerinden
     * müşteri eklemek için get metodu
     * @param model Model objesi
     * @return
     */
    //Customer adding getmapping
    @GetMapping("/save/customer")
    public String customerAdd(Model model){
        log.info("Save Customer get method");
        model.addAttribute("customer", new CustomerDto());
        return "save_customer";
    }

    /**
     * http://localhost:8080/save/customer
     * Müşteri eklemek için post metodu
     * @param customerDto müşteri bilgileri
     * @param bindingResult
     * @param model Model objesi
     * @return
     */
    //Customer adding post
    @PostMapping("/save/customer")
    public String postCustomerAdd(@Valid @ModelAttribute("customer") CustomerDto customerDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            log.info("Error occurred while adding customer");
            model.addAttribute("customer", customerDto);
            return "save_customer";
        }
        ReturnModel returnModel = customerService.addCustomer(customerDto);
        log.info("Customer Added Successfully");
        return "redirect:/";
    }

    /**
     * Müşterileri listeleme metodu
     * Pagination ve sorting uygulanmıştır
     * @param pageNo sayfa sayısı
     * @param sortField sortlanacak alan
     * @param sortDir
     * @param model model objemiz
     * @return
     */
    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,  @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;

        Page < CustomerEntity > page = customerService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List < CustomerDto > listEmployees = page.getContent().stream().map(CustomerMapper::entity2Dto).collect(Collectors.toList());
        if(pageNo>page.getTotalPages()){
            log.info("total number of pages exceeded");
        }
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listCustomers", listEmployees);
        return "list_customer";
    }


    //List all customer
    @GetMapping("/")
    public String listAllCustomer(Model model){
        log.info("List all customers get method");
       return findPaginated(1, "customerName","asc",model);

    }

    /**
     * Müşteri idsine göre müşteri silme metodu
     * @param id müşteri id'si
     * @return
     */
    // Delete customer by id
    @GetMapping("/delete/{id}")
    public String deleteCustomerById(@PathVariable("id")Long id){

       ReturnModel returnModel =  customerService.deleteCustomerById(id);
       if(returnModel.isSuccessful()){
       log.info("Customer deleted successfully");
        return "redirect:/";
       }else{
           log.info("Customer can't deleted");
       return "redirect:/";
       }

    }

    /**
     * Id'ye göre müşteri çağırımı
     * @param id gösterilmek istenilen müşteri idsi
     * @param model
     * @return
     */
    //Get customer by id
    @GetMapping("/customer/{id}")
    public String getCustomerById(@PathVariable("id") Long id, Model model){
       ReturnModel returnModel = customerService.getCustomerById(id);

       if(returnModel.isSuccessful()){
           log.info("customer successfully found");
           model.addAttribute("customer", returnModel.getResult());
           return "customer_detail";

       }else{
           log.info("Customer didn't find");
           return "redirect:/";
       }
    }

    /**
     * Müşteri araması
     * @param query aranacak sorgu
     * @param model model objesi
     * @return
     */
    //Search customer
    @GetMapping("/list/all/search")
    public String searchCustomers(@RequestParam(value = "query")String query, Model model){
        List<CustomerDto> customerDtoList = customerService.searchCustomer(query).getResult();
        model.addAttribute("listCustomers", customerDtoList);
        return "list_customer";
    }
}
