package com.alpersurekci.project;


import com.alpersurekci.project.ExternalService.DAO.Entity.CustomerEntity;
import com.alpersurekci.project.ExternalService.DAO.Repository.CustomerRepository;

import com.alpersurekci.project.ExternalService.Rest.impl.CustomerRestServiceImpl;
import com.alpersurekci.project.ExternalService.Service.CustomerService;
import com.alpersurekci.project.ExternalService.Service.impl.CustomerServiceImpl;
import com.alpersurekci.project.Model.ReturnModel;
import com.alpersurekci.project.dto.CustomerDto;
import com.alpersurekci.project.mapper.CustomerMapper;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;


import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class ApplicationTests {

	@Mock
	private CustomerRepository customerRepository;

	@InjectMocks
	private CustomerServiceImpl customerService;

	private CustomerEntity customer;

	@BeforeEach
	public void setup(){
		customer = CustomerEntity.builder().customerEmail("test@gmail.com")
				.customerName("test")
				.customerSurname("testsurname")
				.id(1L).build();
	}

	@DisplayName("JUnit test for save customer method")
	@Test
	public void saveCustomerTest(){
		given(customerRepository.findByEmail(customer.getCustomerEmail()))
				.willReturn(Optional.empty());

		given(customerRepository.save(customer)).willReturn(customer);

		System.out.println(customerRepository);
		System.out.println(customerService);

		// when -  action or the behaviour that we are going test
		ReturnModel savedcustomer = customerService.addCustomer(CustomerMapper.entity2Dto(customer));

		System.out.println(savedcustomer.getResult());
		// then - verify the output
		CustomerDto customerDto = savedcustomer.getResult().get(0);
		assertThat(customerDto).isNotNull();
	}

	@DisplayName("JUnit test for listCustomers method")
	@Test
	public void listCustomersTest(){
		// given - precondition or setup

		CustomerEntity customer1 = CustomerEntity.builder()
				.id(2L)
				.customerName("Tony")
				.customerSurname("Stark")
				.customerEmail("tony@gmail.com")
				.build();

		given(customerRepository.findAll()).willReturn(List.of(customer,customer1));

		// when -  action or the behaviour that we are going test
		ReturnModel returnModel = customerService.getCustomers();

		// then - verify the output
		assertThat(returnModel.getResult()).isNotNull();
		assertThat(returnModel.getResult().size()>0);
	}

	@DisplayName("JUnit test for getCustomerById method")
	@Test
	public void getCustomerByIdTest(){
		// given
		given(customerRepository.findById(1L)).willReturn(Optional.of(customer));

		// when
		ReturnModel savedCustomer = customerService.getCustomerById(customer.getId());

		// then
		assertThat(savedCustomer.getResult()).isNotNull();

	}

}
