/*
package com.maktab.final_project_phaz2.controller;

import com.maktab.final_project_phaz2.date.dto.CustomerDto;
import com.maktab.final_project_phaz2.date.dto.ExpertDto;
import com.maktab.final_project_phaz2.date.model.Customer;
import com.maktab.final_project_phaz2.date.model.Expert;
import com.maktab.final_project_phaz2.exception.NoResultException;
import com.maktab.final_project_phaz2.service.CustomerService;
import com.maktab.final_project_phaz2.service.ExpertService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final ModelMapper mapper;
    private final CustomerService customerService;
    @PostMapping("/add_customer")
    public ResponseEntity<String> addCustomer(@RequestBody CustomerDto customerDto) {
        Customer customer = mapper.map(customerDto, Customer.class);
        customerService.registerCustomer(customer);
        return ResponseEntity.ok().body("**you are registered as an customer**");
    }
    @GetMapping("/logIn_customer")
    public ResponseEntity<Customer> getByEmailAddress(@RequestParam("emailAddress") String emailAddress,@RequestParam("password") String password) throws NoResultException {

        return ResponseEntity.ok().body(customerService.logIn(emailAddress,password));
    }
    @PutMapping("/update_customer")
    public ResponseEntity<Customer> update(@RequestBody CustomerDto customerDto){
        Customer customer = mapper.map(customerDto, Customer.class);
        return ResponseEntity.ok().body(customerService.updateCustomer(customer));
    }
    @DeleteMapping("/delete_customer")
    public ResponseEntity<String>delete(@RequestBody CustomerDto customerDto){
        Customer customer = mapper.map(customerDto, Customer.class);
        customerService.deleteCustomer(customer);
        return ResponseEntity.ok().body("customer does not delete");
    }
    */
/*@GetMapping("/getAll_customer")
    public ResponseEntity<Customer>getAllCustomer(){
        return customerService.getAll();
    }*//*

    @GetMapping("/findCustomerById")
    public ResponseEntity<Customer>getById(@RequestParam("id")@Min(1) Long id) throws NoResultException {
        return ResponseEntity.ok().body(customerService.findCustomerById(id));
    }
    @PutMapping("/change_Password")
    public ResponseEntity<String>changePassword(@RequestParam("emailAddress")String emailAddress,@RequestParam("oldPassword")String oldPassword,@RequestParam("newPassword")String newPassword) throws NoResultException {
        return ResponseEntity.ok().body(customerService.changePassword(emailAddress,oldPassword,newPassword));
    }
}
*/
