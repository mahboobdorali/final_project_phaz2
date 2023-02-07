package com.maktab.final_project_phaz2.controller;

import com.maktab.final_project_phaz2.date.dto.CustomerDto;
import com.maktab.final_project_phaz2.date.dto.OrderDto;
import com.maktab.final_project_phaz2.date.dto.ServiceDto;
import com.maktab.final_project_phaz2.date.model.*;
import com.maktab.final_project_phaz2.exception.NoResultException;
import com.maktab.final_project_phaz2.service.CustomerService;
import com.maktab.final_project_phaz2.service.ServiceUnderService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final ModelMapper mapper;
    private final CustomerService customerService;
    private final ServiceUnderService underService;

    @PostMapping("/add_customer")
    public ResponseEntity<String> addCustomer(@RequestBody CustomerDto customerDto) {
        Customer customer = mapper.map(customerDto, Customer.class);
        customerService.registerCustomer(customer);
        return ResponseEntity.ok().body("**you are registered as an customer**");
    }

    @GetMapping("/logIn_customer")
    public ResponseEntity<Customer> getByEmailAddress(@RequestParam("emailAddress") String emailAddress, @RequestParam("password") String password) throws NoResultException {

        return ResponseEntity.ok().body(customerService.logIn(emailAddress, password));
    }

    @PutMapping("/update_customer")
    public ResponseEntity<Customer> update(@RequestBody CustomerDto customerDto) {
        Customer customer = mapper.map(customerDto, Customer.class);
        return ResponseEntity.ok().body(customerService.updateCustomer(customer));
    }

    @DeleteMapping("/delete_customer")
    public ResponseEntity<String> delete(@RequestBody CustomerDto customerDto) {
        Customer customer = mapper.map(customerDto, Customer.class);
        customerService.deleteCustomer(customer);
        return ResponseEntity.ok().body("customer deleted");
    }

    @GetMapping("/getAll_customer")
    public ResponseEntity<List<Customer>> getAllCustomer() {
        return ResponseEntity.ok().body(customerService.getAllCustomer());
    }

    @GetMapping("/findCustomerById")
    public ResponseEntity<Customer> getById(@RequestParam("id") @Min(1) Long id) {
        return ResponseEntity.ok().body(customerService.findCustomerById(id));
    }

    @GetMapping("/findCustomerByEmail")
    public ResponseEntity<Customer> getByEmail(@RequestParam("emailAddress") String emailAddress) {
        return ResponseEntity.ok().body(customerService.findCustomerByEmail(emailAddress));
    }

    @PutMapping("/change_Password")
    public ResponseEntity<String> changePassword(@RequestParam("emailAddress") String emailAddress, @RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) {
        customerService.changePassword(emailAddress, oldPassword, newPassword);
        return ResponseEntity.ok().body("password changed :)");
    }

    @PostMapping("/save_order")
    public ResponseEntity<String> saveOrder(@RequestBody OrderDto orderDto, @RequestParam Long idUnderService
    ,@RequestParam Long idCustomer ) {
        OrderCustomer orderCustomer = mapper.map(orderDto, OrderCustomer.class);
        customerService.Order(orderCustomer, idUnderService,idCustomer);
        return ResponseEntity.ok().body("this order saved :)");
    }

    @GetMapping("/show_mainTask")
    public ResponseEntity<List<UnderService>> showMainTaskByUnderService(@RequestParam("name") String name) {
        return ResponseEntity.ok().body(underService.showAllUnderServiceByServiceByCustomer(name));
    }

    @GetMapping("/sort")
    public ResponseEntity<List<Offer>> sortOffer(@RequestParam("idOrderCustomer") Long idOrderCustomer) {
        return ResponseEntity.ok().body(customerService.sortByPrice(idOrderCustomer));
    }

    @PutMapping("/selectOffer")
    public ResponseEntity<String> choiceOfferByCustomer(@RequestParam("idSelectOffer") Long idSelectOffer
            , @RequestParam("idOrder") Long idOrder) {
        customerService.selectOfferByCustomer(idSelectOffer, idOrder);
        return ResponseEntity.ok().body("*this offer selected by you");
    }

    /* @PostMapping("/saveScore")
    public ResponseEntity<String> saveScoreForExpert(@RequestParam("idExpert") Long idExpert, @RequestParam("score") Integer score) {
        customerService.saveComments(idExpert, score);
        return ResponseEntity.ok().body("*your score is save");

    }*/

    @PutMapping("/changeStatus")
    public ResponseEntity<String> changeStatusToStart(@RequestParam("idOffer") Long idOffer,
                                                      @RequestParam("idOrder") Long idOrder) {
        customerService.changeSituationByCustomer(idOffer, idOrder);
        return ResponseEntity.ok().body("*situation of your order changed(:");
    }
   /* @PutMapping("/switchingStatus")
    public ResponseEntity<String>changeStatusToFinish(@RequestParam("id")Long id){
        customerService.(id);
        return ResponseEntity.ok().body("*situation of your order changed to done");
    }*/
}
