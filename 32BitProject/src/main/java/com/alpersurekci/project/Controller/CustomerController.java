package com.alpersurekci.project.Controller;

import com.alpersurekci.project.ExternalService.DAO.Entity.CustomerEntity;
import com.alpersurekci.project.ExternalService.Service.CustomerService;
import com.alpersurekci.project.Model.ReturnModel;
import com.alpersurekci.project.dto.CustomerDto;


import jakarta.jws.WebParam;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Log4j2

@Controller
public class CustomerController {

    CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //Customer adding getmapping
    @GetMapping("/save/customer")
    public String customerAdd(Model model){

        model.addAttribute("customer", new CustomerDto());
        return "save_customer";
    }

    //Customer adding post
    @PostMapping("/save/customer")
    public String postCustomerAdd(@Valid @ModelAttribute("customer") CustomerDto customerDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            log.info("Customer ekleme sırasında hata oluştu");
            model.addAttribute("customer", customerDto);
            return "save_customer";
        }
        ReturnModel returnModel = customerService.addCustomer(customerDto);
        log.info(returnModel);
        return "redirect:/";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,  @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;

        Page < CustomerEntity > page = customerService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List < CustomerEntity > listEmployees = page.getContent();

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
       return findPaginated(1, "customerName","asc",model);

    }

    // Delete customer by id
    @GetMapping("/delete/{id}")
    public String deleteCustomerById(@PathVariable("id")Long id){

       ReturnModel returnModel =  customerService.deleteCustomerById(id);
       log.info(returnModel);
        return "redirect:/";
    }
    //Get customer by id
    @GetMapping("/customer/{id}")
    public String getCustomerById(@PathVariable("id") Long id, Model model){
       ReturnModel returnModel = customerService.getCustomerById(id);
       model.addAttribute("customer", returnModel.getResult());
       log.info(returnModel);
       return "customer_detail";
    }

    //Search customer
    @GetMapping("/list/all/search")
    public String searchCustomers(@RequestParam(value = "query")String query, Model model){
        List<CustomerDto> customerDtoList = customerService.searchCustomer(query).getResult();
        model.addAttribute("listCustomers", customerDtoList);
        return "list_customer";
    }
}
