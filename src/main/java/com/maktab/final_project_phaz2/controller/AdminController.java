package com.maktab.final_project_phaz2.controller;

import com.maktab.final_project_phaz2.date.dto.*;
import com.maktab.final_project_phaz2.date.model.Admin;
import com.maktab.final_project_phaz2.date.model.Customer;
import com.maktab.final_project_phaz2.date.model.MainTask;
import com.maktab.final_project_phaz2.date.model.UnderService;
import com.maktab.final_project_phaz2.date.model.enumuration.ApprovalStatus;
import com.maktab.final_project_phaz2.date.model.enumuration.CurrentSituation;
import com.maktab.final_project_phaz2.service.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final ModelMapper mapper;
    private final ServiceUnderService serviceUnderService;
    private final MainTaskService mainTaskService;
    private final AdminService adminService;
    private final OrderService orderService;
    private final CustomerService customerService;
    private final ExpertService expertService;

    @PostMapping("/add_admin")
    public ResponseEntity<String> addCustomer(@Valid @RequestBody AdminDto adminDto) {
        Admin admin = mapper.map(adminDto, Admin.class);
        adminService.registerAdmin(admin);
        return ResponseEntity.ok().body("**you are registered as an admin**");
    }

    @PostMapping("/add-under-service")
    public ResponseEntity<String> addUnderService(@Valid @RequestBody UnderServiceDto underServiceDto,
                                                  @RequestParam Long idService) {
        UnderService underService = mapper.map(underServiceDto, UnderService.class);
        serviceUnderService.addUnderServiceByAdmin(underService, idService);
        return ResponseEntity.ok().body("**this underService add By admin**");
    }


    @PostMapping("/add-Service")
    public ResponseEntity<String> addService(@Valid @RequestBody ServiceDto ServiceDto) {
        MainTask mainTask = mapper.map(ServiceDto, MainTask.class);
        mainTaskService.addServiceByAdmin(mainTask);
        return ResponseEntity.ok().body("**this Service add By admin**");
    }

    @PutMapping("/change_description")
    public ResponseEntity<String> changeDescriptionByAdmin(@RequestParam("idUnderService") Long idUnderService, @RequestParam("newDescription") String newDescription) {
        adminService.changeDescription(idUnderService, newDescription);
        return ResponseEntity.ok().body("**subService description corrected**");
    }

    @PutMapping("/change_price")
    public ResponseEntity<String> changePriceByAdmin(@RequestParam("newPrice") double newPrice, @RequestParam("nameOfUnderService") String nameOfUnderService) {
        adminService.changePriceUnderService(newPrice, nameOfUnderService);
        return ResponseEntity.ok().body("***subService price corrected***");
    }

    @GetMapping("/getAll_under-service")
    public ResponseEntity<List<UnderServiceDto>> getAllUnderService() {
        return ResponseEntity.ok().body(serviceUnderService.getAllUnderService().stream().
                map(underService -> mapper.map(underService, UnderServiceDto.class)).collect(Collectors.toList()));
    }


    @GetMapping("/getAll_main-service")
    public ResponseEntity<List<ServiceDto>> getAllService() {
        return ResponseEntity.ok().body(mainTaskService.getAllService().stream().
                map(mainTask -> mapper.map(mainTask, ServiceDto.class)).collect(Collectors.toList()));
    }

    @PostMapping("/add-expert-to-under-service")
    public ResponseEntity<String> addExpertToUnderServiceByAdmin(@RequestBody AddExpertToUnderDto addExpertToUnderDto) {
        adminService.addExpertToUnderService(addExpertToUnderDto.getIdUnderService(), addExpertToUnderDto.getIdExpert());
        return ResponseEntity.ok().body("this expert add to underService");
    }

    @DeleteMapping("/delete-expert-from-under-service")
    public ResponseEntity<String> deleteExpertFromUnderServiceByAdmin(@RequestParam("idUnderService") Long idUnderService, @RequestParam("idExpert") Long idExpert) {
        adminService.deleteExpertFromUnderService(idUnderService, idExpert);
        return ResponseEntity.ok().body("this expert deleted from underService");
    }

    @PutMapping("/change-status")
    public ResponseEntity<String> convertExpertStatus(@RequestParam("emailAddress") String emailAddress) {
        adminService.convertStatus(emailAddress);
        return ResponseEntity.ok().body("expert status changed to approved:)");
    }

    @GetMapping("/find-sub-service-by-name")
    public ResponseEntity<UnderServiceDto> getUnderServiceByName(@RequestParam("underServiceName") String underServiceName) {
        UnderService underServiceByName = serviceUnderService.findUnderServiceByName(underServiceName);
        UnderServiceDto underServiceDto = mapper.map(underServiceByName, UnderServiceDto.class);
        return ResponseEntity.ok().body(underServiceDto);
    }


    @GetMapping("/find-sub-service-by-id")
    public ResponseEntity<UnderServiceDto> getUnderServiceById(@RequestParam("id") @Min(1) Long id) {
        UnderService underServiceById = serviceUnderService.findUnderServiceById(id);
        UnderServiceDto underServiceDto = mapper.map(underServiceById, UnderServiceDto.class);
        return ResponseEntity.ok().body(underServiceDto);
    }


    @GetMapping("/find-service-by-name")
    public ResponseEntity<ServiceDto> getServiceByName(@RequestParam("serviceName") String serviceName) {
        MainTask serviceByName = mainTaskService.findServiceByName(serviceName);
        ServiceDto serviceDto = mapper.map(serviceByName, ServiceDto.class);
        return ResponseEntity.ok().body(serviceDto);
    }

    @GetMapping("/find-service-by-id")
    public ResponseEntity<ServiceDto> getServiceById(@RequestParam("id") @Min(1) Long id) {
        MainTask serviceById = mainTaskService.findServiceById(id);
        ServiceDto serviceDto = mapper.map(serviceById, ServiceDto.class);
        return ResponseEntity.ok().body(serviceDto);
    }

    @GetMapping("/search-by-admin")
    public ResponseEntity<List<SearchExpertDto>> filterAdmin(@Valid @RequestBody SearchExpertDto expert) {
        return ResponseEntity.ok().body(expertService.filterExpertByCondition(expert).stream().
                map(expert1 -> mapper.map(expert1, SearchExpertDto.class)).collect(Collectors.toList()));
    }

    @GetMapping("/count-of-order-customer")
    public int countOfOrderCustomer(@Valid @RequestBody CountOrderDto countOrderDto) {
        return orderService.countOrderOfCustomer(countOrderDto.getEmailAddress());
    }

    @GetMapping("/count-of-order-expert")
    public int countOfOrderExpert(@Valid @RequestBody CountOrderDto countOrderDto) {
        return orderService.countOrderOfExpert(countOrderDto.getEmailAddress(), countOrderDto.getCurrentSituation());
    }

    @GetMapping("/get-all_customer")
    public ResponseEntity<List<UserDto>> getAllCustomer() {
        return ResponseEntity.ok().body(customerService.getAllCustomer().stream().
                map(customer -> mapper.map(customer, UserDto.class)).collect(Collectors.toList()));
    }

    @GetMapping("/get-all_expert")
    public ResponseEntity<List<UserDto>> getAllExpert() {
        return ResponseEntity.ok().body(expertService.getAllExpert().stream().
                map(expert -> mapper.map(expert, UserDto.class)).collect(Collectors.toList()));
    }

    @GetMapping("/search-customer-by-admin")
    public ResponseEntity<List<SearchCustomerDto>> filterCustomerByAdmin(@Valid @RequestBody SearchCustomerDto customer) {
        return ResponseEntity.ok().body(customerService.filterCustomerByCondition(customer).stream().
                map(customer1 -> mapper.map(customer1, SearchCustomerDto.class)).collect(Collectors.toList()));
    }

    @GetMapping("/filter-order")
    public ResponseEntity<List<OrderCustomerFilterDto>> filterOrderByAdmin(@Valid @RequestBody OrderCustomerFilterDto order) {
        return ResponseEntity.ok().body(orderService.filterOrderByAdmin(order).stream().
                map(customer -> mapper.map(customer, OrderCustomerFilterDto.class)).collect(Collectors.toList()));
    }
    @GetMapping("/get-all-new-expert")
    public ResponseEntity<List<ExpertDtoForFilter>> findAllExpertNewStatus(@RequestParam("status") ApprovalStatus status) {
        return ResponseEntity.ok().body(expertService.findAllByNewStatus(status).stream()
                .map(expert -> mapper.map(expert, ExpertDtoForFilter.class)).collect(Collectors.toList()));
    }
    @GetMapping("/history-order-for-expert")
    public ResponseEntity<List<OrderCustomerDto>> historyOfExpertByAdmin(@RequestParam("status") CurrentSituation status,
                                                                         @RequestParam("emailExpert") String emailExpert) {
        return ResponseEntity.ok().body(orderService.showAllOrderExpertByAdmin(status,emailExpert).stream().
                map(orderCustomer -> mapper.map(orderCustomer, OrderCustomerDto.class)).collect(Collectors.toList()));
    }
    @GetMapping("/history-order-for-customer")
    public ResponseEntity<List<OrderCustomerDto>> historyOfCustomerByAdmin(@RequestParam("status") CurrentSituation status,
                                                                         @RequestParam("emailCustomer") String emailCustomer){
        return ResponseEntity.ok().body(orderService.showAllOrderCustomerByAdmin(status,emailCustomer).stream().
                map(orderCustomer -> mapper.map(orderCustomer,OrderCustomerDto.class)).collect(Collectors.toList()));
    }
}