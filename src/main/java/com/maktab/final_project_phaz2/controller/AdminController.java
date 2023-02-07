package com.maktab.final_project_phaz2.controller;

import com.maktab.final_project_phaz2.date.dto.ServiceDto;
import com.maktab.final_project_phaz2.date.dto.UnderServiceDto;
import com.maktab.final_project_phaz2.date.model.MainTask;
import com.maktab.final_project_phaz2.date.model.UnderService;
import com.maktab.final_project_phaz2.service.AdminService;
import com.maktab.final_project_phaz2.service.MainTaskService;
import com.maktab.final_project_phaz2.service.ServiceUnderService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final ModelMapper mapper;
    private final ServiceUnderService serviceUnderService;
    private final MainTaskService mainTaskService;
    private final AdminService adminService;


    @PostMapping("/add_underService")
    public ResponseEntity<String> addUnderService(@RequestBody UnderServiceDto underServiceDto,@RequestParam Long idService) {
        UnderService underService = mapper.map(underServiceDto, UnderService.class);
        serviceUnderService.addUnderServiceByAdmin(underService,idService);
        return ResponseEntity.ok().body("**this underService add By admin**");
    }


    @PostMapping("/add_Service")
    public ResponseEntity<String> addService(@RequestBody ServiceDto ServiceDto) {
        MainTask mainTask = mapper.map(ServiceDto, MainTask.class);
        mainTaskService.addServiceByAdmin(mainTask);
        return ResponseEntity.ok().body("**this Service add By admin**");
    }

    @PutMapping("/change_description")
    public ResponseEntity<String > changeDescriptionByAdmin( @RequestParam ("idUnderService")Long idUnderService,@RequestParam("newDescription") String newDescription) {
        adminService.changeDescription(idUnderService, newDescription);
        return ResponseEntity.ok().body("**subService description corrected**");
    }

    @PutMapping("/change_price")
    public ResponseEntity<String> changePriceByAdmin(@RequestParam("newPrice") double newPrice, @RequestParam("nameOfUnderService") String nameOfUnderService) {
        adminService.changePriceUnderService(newPrice, nameOfUnderService);
        return ResponseEntity.ok().body("***subService price corrected***");
    }

    @GetMapping("/getAll_underService")
    public ResponseEntity<List<UnderService>> getAllUnderService() {
        return ResponseEntity.ok().body(serviceUnderService.getAllUnderService());
    }


    @GetMapping("/getAll_mainService")
    public ResponseEntity<List<MainTask>> getAllService() {
        return ResponseEntity.ok().body(mainTaskService.getAllService());
    }

    @GetMapping("/findSubServiceByName")
    public ResponseEntity<UnderService> getUnderServiceByName(@RequestParam("underServiceName") String underServiceName) {
        return ResponseEntity.ok().body(serviceUnderService.findUnderServiceByName(underServiceName));
    }


   @GetMapping("/findSubServiceById")
   public ResponseEntity<UnderService> getUnderServiceById(@RequestParam("id") @Min(1) Long id) {
       return ResponseEntity.ok().body(serviceUnderService.findUnderServiceById(id));
   }


    @GetMapping("/findServiceByName")
    public ResponseEntity<MainTask> getServiceByName(@RequestParam("serviceName") String serviceName) {
        return ResponseEntity.ok().body(mainTaskService.findServiceByName(serviceName));
    }


    @GetMapping("/findServiceById")
    public ResponseEntity<MainTask> getServiceById(@RequestParam("id") @Min(1) Long id) {
        return ResponseEntity.ok().body(mainTaskService.findServiceById(id));
    }

}
