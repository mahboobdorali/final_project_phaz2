package com.maktab.final_project_phaz2.controller;

import com.maktab.final_project_phaz2.Util.Validation;
import com.maktab.final_project_phaz2.date.dto.*;
import com.maktab.final_project_phaz2.date.model.*;
import com.maktab.final_project_phaz2.date.model.enumuration.CurrentSituation;
import com.maktab.final_project_phaz2.exception.NoResultException;
import com.maktab.final_project_phaz2.service.CustomerService;
import com.maktab.final_project_phaz2.service.OrderService;
import com.maktab.final_project_phaz2.service.ServiceUnderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final ModelMapper mapper;
    private final CustomerService customerService;
    private final ServiceUnderService underService;
    private final RestTemplate restTemplate;

    private final OrderService orderService;

    @PostMapping("/add_customer")
    public ResponseEntity<String> addCustomer(@Valid @RequestBody CustomerDto customerDto) {
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
    public ResponseEntity<CustomerDto> update(@Valid @RequestBody CustomerDto customerDto) {
        Customer customer = mapper.map(customerDto, Customer.class);
        Customer customer1 = customerService.updateCustomer(customer);
        CustomerDto customerDto1 = mapper.map(customer1, CustomerDto.class);
        return ResponseEntity.ok().body(customerDto1);
    }

    @DeleteMapping("/delete_customer")
    public ResponseEntity<String> delete(@Valid @RequestBody CustomerDto customerDto) {
        Customer customer = mapper.map(customerDto, Customer.class);
        customerService.deleteCustomer(customer);
        return ResponseEntity.ok().body("customer deleted");
    }

    @GetMapping("/find-customer-by-id")
    public ResponseEntity<UserDto> getById(@RequestParam("id") @Min(1) Long id) {
        Customer customerById = customerService.findCustomerById(id);
        UserDto userDto = mapper.map(customerById, UserDto.class);
        return ResponseEntity.ok().body(userDto);

    }

    @GetMapping("/find-customer-by-email")
    public ResponseEntity<UserDto> getByEmail(@RequestParam("emailAddress") String emailAddress) {
        Customer customerByEmail = customerService.findCustomerByEmail(emailAddress);
        UserDto userDto = mapper.map(customerByEmail, UserDto.class);
        return ResponseEntity.ok().body(userDto);
    }

    @PutMapping("/change_password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody PasswordDto passwordDto) {
        customerService.changePassword(passwordDto.getNewPassword(), passwordDto.getConfirmPassword());
        return ResponseEntity.ok().body("password changed :)");
    }

    @PostMapping("/save_order")
    public ResponseEntity<String> saveOrder(@RequestBody OrderDto orderDto, @RequestParam Long idUnderService) {
        OrderCustomer orderCustomer = mapper.map(orderDto, OrderCustomer.class);
        customerService.Order(orderCustomer, idUnderService);
        return ResponseEntity.ok().body("this order saved :)");
    }

    @GetMapping("/show_main-task")
    public ResponseEntity<List<UnderServiceDto>> showMainTaskByUnderService(@RequestParam("name") String name) {
        return ResponseEntity.ok().body(underService.showAllUnderServiceByServiceByCustomer(name).stream().
                map(underService -> mapper.
                        map(underService, UnderServiceDto.class)).collect(Collectors.toList()));
    }

    @GetMapping("/sort-by-price")
    public ResponseEntity<List<OfferDto>> sortOfferByPrice(@RequestParam("idOrderCustomer") Long idOrderCustomer) {
        return ResponseEntity.ok().body(customerService.sortByPrice(idOrderCustomer).stream().map(offer1 -> mapper.
                map(offer1, OfferDto.class)).collect(Collectors.toList()));
    }

    @GetMapping("/sort-by-score")
    public ResponseEntity<List<OfferDto>> sortOffer(@RequestParam("idOrderCustomer") Long idOrderCustomer) {
        return ResponseEntity.ok().body(customerService.sortByScore(idOrderCustomer).stream().map(offer2 -> mapper.
                map(offer2, OfferDto.class)).collect(Collectors.toList()));
    }

    @PutMapping("/select-offer")
    public ResponseEntity<String> choiceOfferByCustomer(@RequestParam("idSelectOffer") Long idSelectOffer
            , @RequestParam("idOrder") Long idOrder) {
        customerService.selectOfferByCustomer(idSelectOffer, idOrder);
        return ResponseEntity.ok().body("*this offer selected by you");
    }

    @PutMapping("/change-status")
    public ResponseEntity<String> changeStatusToStart(@RequestParam("idOffer") Long idOffer,
                                                      @RequestParam("idOrder") Long idOrder) {
        customerService.changeSituationByCustomer(idOffer, idOrder);
        return ResponseEntity.ok().body("*situation of your order changed to started(:");
    }

    @PutMapping("/switching-status")
    public ResponseEntity<String> changeStatusToFinish(@RequestParam("idOffer") Long idOffer,
                                                       @RequestParam("idOrder") Long idOrder      ) {
        customerService.changeSituationForFinish(idOffer, idOrder);
        return ResponseEntity.ok().body("*situation of your order changed to done");
    }
    @GetMapping("/list-orders-for-payed")
    public ResponseEntity<List<OrderDto>> ListOrderCustomerForPayed(@RequestParam("currentSituation") CurrentSituation currentSituation){
        return ResponseEntity.ok().body(orderService.findOrderByStatusForPayedByCustomer(currentSituation).stream()
                .map(orderCustomer -> mapper.map(orderCustomer, OrderDto.class)).collect(Collectors.toList()));
    }

    @PutMapping("payment-from-credit")
    public ResponseEntity<String> paidFromCustomerCredit(@RequestParam("idChoiceOffer") Long idChoiceOffer) {
        customerService.paymentFromCredit(idChoiceOffer);
        return ResponseEntity.ok().body("your payment has been successfully completed!!");
    }

    @PostMapping("/save-score")
    public ResponseEntity<String> saveScoreForExpert(@RequestParam("idExpert") Long idExpert, @RequestBody OpinionDto opinionDto) {
        Opinion opinion = mapper.map(opinionDto, Opinion.class);
        customerService.saveComments(opinion, idExpert);
        return ResponseEntity.ok().body("*your score is save");
    }

    @GetMapping("/pay/online")
    public String pay() {
        return "payment";
    }

    @PostMapping(path = "/pay/online", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public @ResponseBody String payOnline(PaymentDto paymentDTO, @RequestParam("g-recaptcha-response") String captcha) {
        String url = "https://www.google.com/recaptcha/api/siteverify";
        String params = "?secret=6LdPurMjAAAAACUGZWKWpp-nNF45GQ9Lw631Ksi8&response=" + captcha;
        ReCaptchaResponse reCaptchaResponse = restTemplate.exchange(url + params, HttpMethod.POST,
                null, ReCaptchaResponse.class).getBody();
        if (reCaptchaResponse.getSuccess()) {
            Validation.checkPayment(paymentDTO.getCardNumber(), paymentDTO.getCvv2(), paymentDTO.getExpiredTime(), paymentDTO.getPassword());
            return "done";
        }
        return "invalid captcha";
    }


    @GetMapping("/show-credit-customer")
    public ResponseEntity<Double> showCustomerAmount() {
        return ResponseEntity.ok().body(customerService.showAmountToCustomer());
    }

    @GetMapping("/show-all-order-for-customer")
    public ResponseEntity<List<OrderCustomerDto>> showAllOrderForCustomer(){
        return ResponseEntity.ok().body(orderService.showAllOrderCustomer().stream().
                map(orderCustomer -> mapper.map(orderCustomer,OrderCustomerDto.class)).collect(Collectors.toList()));
    }
}
