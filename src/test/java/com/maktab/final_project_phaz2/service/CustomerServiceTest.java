package com.maktab.final_project_phaz2.service;

import com.maktab.final_project_phaz2.date.model.Customer;
import com.maktab.final_project_phaz2.date.model.Expert;
import com.maktab.final_project_phaz2.date.model.enumuration.Role;
import com.maktab.final_project_phaz2.exception.NoResultException;
import com.maktab.final_project_phaz2.service.CustomerService;
import com.maktab.final_project_phaz2.service.ExpertService;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

  Customer customer= Customer.builder().name("zahra").family("naderi").emailAddress("zahraNaderi@gmail.com").
          password("xcBN23@#").role(Role.CUSTOMER).amount(2000).build();

    @BeforeAll
    static void setup(@Autowired DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("ExpertServiceData.sql"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(1)
    void registerCustomer() throws NoResultException {
        customerService.registerCustomer(customer);
        Customer customerFind = customerService.findCustomerByEmail(customer.getEmailAddress());
        assertNotNull(customerFind);
    }

    @Test
    @Order(2)
    void changePassword() throws NoResultException {
        String password = "ppPP12#!";
        customerService.changePassword(customer.getEmailAddress(), customer.getPassword(), password);
        Customer customerFind = customerService.findCustomerByEmail(customer.getEmailAddress());
        assertEquals(password, customerFind.getPassword());
    }

    @Test
    @Order(3)
    void findCustomerByEmail() throws NoResultException {
        Customer customerFind = customerService.findCustomerByEmail(customer.getEmailAddress());
        assertNotNull(customerFind);
    }
    @Test
    @Order(4)
    void getListOfCustomer() {
        List<Customer> allCustomer = customerService.getAllCustomer();
        org.assertj.core.api.Assertions.assertThat(allCustomer.size()).isGreaterThan(0);
    }

    @Test
    @Order(5)
    void updateCustomerTest() throws NoResultException {
        Customer customerFind = customerService.findCustomerByEmail(customer.getEmailAddress());
        customerFind.setEmailAddress("nahid@gmail.com");
        Customer customer1 = customerService.updateCustomer(customerFind);
        assertEquals(customer1.getEmailAddress(), customerFind.getEmailAddress());
    }
    @Test
    @Order(6)
    void deleteCustomerTest() {
        try {
            Customer customerFind = customerService.findCustomerByEmail(customer.getEmailAddress());
            customerService.deleteCustomer(customerFind);
            Customer customerFind1 = customerService.findCustomerByEmail(customer.getEmailAddress());
            org.assertj.core.api.Assertions.assertThat(customerFind1).isNull();
        } catch (NoResultException e) {
            assertEquals("this customer dose not exist", e.getMessage());
        }
    }
}