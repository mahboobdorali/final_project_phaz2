package com.maktab.final_project_phaz2.controller;

import com.maktab.final_project_phaz2.date.dto.*;
import com.maktab.final_project_phaz2.date.model.Admin;
import com.maktab.final_project_phaz2.date.model.Customer;
import com.maktab.final_project_phaz2.date.model.MainTask;
import com.maktab.final_project_phaz2.date.model.UnderService;
import com.maktab.final_project_phaz2.service.AdminService;
import com.maktab.final_project_phaz2.service.MainTaskService;
import com.maktab.final_project_phaz2.service.OrderService;
import com.maktab.final_project_phaz2.service.ServiceUnderService;
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

    @PostMapping("/add_expert")
    public ResponseEntity<String> addCustomer(@Valid @RequestBody AdminDto adminDto) {
        Admin admin = mapper.map(adminDto, Admin.class);
        adminService.registerAdmin(admin);
        return ResponseEntity.ok().body("**you are registered as an admin**");
    }

    @PostMapping("/add-under-service")
    public ResponseEntity<String> addUnderService(@Valid @RequestBody UnderServiceDto underServiceDto, @RequestParam Long idService) {
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
        return ResponseEntity.ok().body(adminService.filterExpertByCondition(expert).stream().
                map(expert1 -> mapper.map(expert1, SearchExpertDto.class)).collect(Collectors.toList()));
    }

    @GetMapping("/count-of-order-customer")
    public int countOfOrderCustomer(@Valid @RequestBody CountOrderDto countOrderDto) {
        return orderService.countOrderOfCustomer(countOrderDto.getEmailAddress(), countOrderDto.getCurrentSituation());
    }

    @GetMapping("/count-of-order-expert")
    public int countOfOrderExpert(@Valid @RequestBody CountOrderDto countOrderDto) {
        return orderService.countOrderOfExpert(countOrderDto.getEmailAddress(), countOrderDto.getCurrentSituation());
    }
}
