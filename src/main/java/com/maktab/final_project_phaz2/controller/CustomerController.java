package com.maktab.final_project_phaz2.controller;

import com.maktab.final_project_phaz2.date.dto.*;
import com.maktab.final_project_phaz2.date.model.*;
import com.maktab.final_project_phaz2.exception.NoResultException;
import com.maktab.final_project_phaz2.service.CustomerService;
import com.maktab.final_project_phaz2.service.ServiceUnderService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<UserDto> getByEmailAddress(@RequestParam("emailAddress") String emailAddress, @RequestParam("password") String password) throws NoResultException {
        Customer customer = customerService.logIn(emailAddress, password);
        UserDto sign = mapper.map(customer, UserDto.class);
        return ResponseEntity.ok().body(sign);
    }

    @PutMapping("/update_customer")
    public ResponseEntity<CustomerDto> update(@RequestBody CustomerDto customerDto) {
        Customer customer = mapper.map(customerDto, Customer.class);
        Customer customer1 = customerService.updateCustomer(customer);
        CustomerDto customerDto1 = mapper.map(customer1, CustomerDto.class);
        return ResponseEntity.ok().body(customerDto1);
    }

    @DeleteMapping("/delete_customer")
    public ResponseEntity<String> delete(@RequestBody CustomerDto customerDto) {
        Customer customer = mapper.map(customerDto, Customer.class);
        customerService.deleteCustomer(customer);
        return ResponseEntity.ok().body("customer deleted");
    }

    @GetMapping("/getAll_customer")
    public ResponseEntity<List<UserDto>> getAllCustomer() {
        return ResponseEntity.ok().body(customerService.getAllCustomer().stream().
                map(customer -> mapper.map(customer, UserDto.class)).collect(Collectors.toList()));
    }

    @GetMapping("/findCustomerById")
    public ResponseEntity<UserDto> getById(@RequestParam("id") @Min(1) Long id) {
        Customer customerById = customerService.findCustomerById(id);
        UserDto userDto = mapper.map(customerById, UserDto.class);
        return ResponseEntity.ok().body(userDto);

    }

    @GetMapping("/findCustomerByEmail")
    public ResponseEntity<UserDto> getByEmail(@RequestParam("emailAddress") String emailAddress) {
        Customer customerByEmail = customerService.findCustomerByEmail(emailAddress);
        UserDto userDto = mapper.map(customerByEmail, UserDto.class);
        return ResponseEntity.ok().body(userDto);
    }

    @PutMapping("/change_Password")
    public ResponseEntity<String> changePassword(@RequestParam("emailAddress") String emailAddress,
                                                 @RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) {
        customerService.changePassword(emailAddress, oldPassword, newPassword);
        return ResponseEntity.ok().body("password changed :)");
    }

    @PostMapping("/save_order")
    public ResponseEntity<String> saveOrder(@RequestBody OrderDto orderDto, @RequestParam Long idUnderService
            , @RequestParam Long idCustomer) {
        OrderCustomer orderCustomer = mapper.map(orderDto, OrderCustomer.class);
        customerService.Order(orderCustomer, idUnderService, idCustomer);
        return ResponseEntity.ok().body("this order saved :)");
    }

    @GetMapping("/show_mainTask")
    public ResponseEntity<List<UnderServiceDto>> showMainTaskByUnderService(@RequestParam("name") String name) {
        return ResponseEntity.ok().body(underService.showAllUnderServiceByServiceByCustomer(name).stream().
                map(underService -> mapper.
                        map(underService, UnderServiceDto.class)).collect(Collectors.toList()));
    }

    @GetMapping("/sortByPrice")
    public ResponseEntity<List<OfferDto>> sortOfferByPrice(@RequestParam("idOrderCustomer") Long idOrderCustomer) {
        return ResponseEntity.ok().body(customerService.sortByPrice(idOrderCustomer).stream().map(offer1 -> mapper.
                map(offer1, OfferDto.class)).collect(Collectors.toList()));
    }

    @GetMapping("/sortByScore")
    public ResponseEntity<List<OfferDto>> sortOffer(@RequestParam("idOrderCustomer") Long idOrderCustomer) {
        return ResponseEntity.ok().body(customerService.sortByScore(idOrderCustomer).stream().map(offer2 -> mapper.
                map(offer2, OfferDto.class)).collect(Collectors.toList()));
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
