package com.maktab.final_project_phaz2.controller;

import com.maktab.final_project_phaz2.date.dto.ExpertDto;
import com.maktab.final_project_phaz2.date.dto.OfferDto;
import com.maktab.final_project_phaz2.date.dto.OrderDto;
import com.maktab.final_project_phaz2.date.dto.UserDto;
import com.maktab.final_project_phaz2.date.model.Expert;
import com.maktab.final_project_phaz2.date.model.Offer;
import com.maktab.final_project_phaz2.date.model.OrderCustomer;
import com.maktab.final_project_phaz2.date.model.UnderService;
import com.maktab.final_project_phaz2.date.model.enumuration.CurrentSituation;
import com.maktab.final_project_phaz2.exception.NoResultException;
import com.maktab.final_project_phaz2.service.AdminService;
import com.maktab.final_project_phaz2.service.ExpertService;
import com.maktab.final_project_phaz2.service.OrderService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/expert")
@RequiredArgsConstructor
public class ExpertController {
    private final ModelMapper mapper;
    private final ExpertService expertService;
    private final AdminService adminService;

    private final OrderService orderService;

    @PostMapping("/add_expert")
    public ResponseEntity<String> addExpert(@RequestBody ExpertDto expertDto) {
        Expert expert = mapper.map(expertDto, Expert.class);
        expertService.registerExpert(expert);
        return ResponseEntity.ok().body("**you are registered as an expert**");
    }

    @GetMapping("/logIn_expert")
    public ResponseEntity<UserDto> getByEmailAddress(@RequestParam("emailAddress") String emailAddress, @RequestParam("password") String password) throws NoResultException {
        Expert expert = expertService.logInExpert(emailAddress, password);
        UserDto signIn = mapper.map(expert, UserDto.class);
        return ResponseEntity.ok().body(signIn);
    }

    @PutMapping("/update_expert")
    public ResponseEntity<ExpertDto> update(@RequestBody ExpertDto expertDto) {
        Expert expert = mapper.map(expertDto, Expert.class);
        Expert expert1 = expertService.updateExpert(expert);
        ExpertDto expertDto1 = mapper.map(expert1, ExpertDto.class);
        return ResponseEntity.ok().body(expertDto1);
    }

    @DeleteMapping("/delete_expert")
    public ResponseEntity<String> delete(@RequestBody ExpertDto expertDto) {
        Expert expert = mapper.map(expertDto, Expert.class);
        expertService.deleteExpert(expert);
        return ResponseEntity.ok().body("expert delete :)");
    }

    @GetMapping("/getAll_expert")
    public ResponseEntity<List<UserDto>> getAllExpert() {
        return ResponseEntity.ok().body(expertService.getAllExpert().stream().
                map(expert -> mapper.map(expert, UserDto.class)).collect(Collectors.toList()));
    }

    @GetMapping("/findExpertById")
    public ResponseEntity<UserDto> getById(@RequestParam("id") @Min(1) Long id) {
        Expert expertById = expertService.findExpertById(id);
        UserDto userDto = mapper.map(expertById, UserDto.class);
        return ResponseEntity.ok().body(userDto);
    }

    @GetMapping("/findExpertByEmail")
    public ResponseEntity<UserDto> getByEmail(@RequestParam("emailAddress") String emailAddress) {
        Expert expertByEmail = expertService.findExpertByEmail(emailAddress);
        UserDto userDto = mapper.map(expertByEmail, UserDto.class);
        return ResponseEntity.ok().body(userDto);
    }

    @PutMapping("/change_Password")
    public ResponseEntity<String> changePassword(@RequestParam("emailAddress") String emailAddress,
                                                 @RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) {
        expertService.changePassword(emailAddress, oldPassword, newPassword);
        return ResponseEntity.ok().body("password changed :)");
    }

    @PostMapping("/saveOffer")
    public ResponseEntity<String> offerAnSubmitByExpert(@RequestBody OfferDto offerDto, @RequestParam("idUnderService") Long idUnderService,
                                                        @RequestParam("idOrder") Long idOrder) {
        Offer offer = mapper.map(offerDto, Offer.class);
        expertService.OfferAnSubmit(offer, idUnderService, idOrder);
        return ResponseEntity.ok().body("this offer saved :)");
    }

    @PostMapping("/setExpertId")
    public ResponseEntity<String> serExpert(@RequestParam Long idOffer, @RequestParam Long idExpert) {
        expertService.setExpertToOffer(idOffer, idExpert);
        return ResponseEntity.ok().body("this expert offer an submit");
    }

    @PutMapping("/changeStatus")
    public ResponseEntity<String> convertExpertStatus(@RequestParam("emailAddress") String emailAddress) {
        adminService.convertStatus(emailAddress);
        return ResponseEntity.ok().body("expert status changed to approved:)");
    }

    @PostMapping("/addExpertToUnderService")
    public ResponseEntity<String> addExpertToUnderServiceByAdmin(@RequestParam("idUnderService") Long idUnderService, @RequestParam("idExpert") Long idExpert) {
        adminService.addExpertToUnderService(idUnderService, idExpert);
        return ResponseEntity.ok().body("this expert add to underService");
    }

    @DeleteMapping("/deleteExpertFromUnderService")
    public ResponseEntity<String> deleteExpertFromUnderServiceByAdmin(@RequestParam("idUnderService") Long idUnderService, @RequestParam("idExpert") Long idExpert) {
        adminService.deleteExpertFromUnderService(idUnderService, idExpert);
        return ResponseEntity.ok().body("this expert deleted from underService");
    }

    @GetMapping("/underByStatus")
    public ResponseEntity<List<OrderDto>> ListUnderRelatedExpertInStatus(
            @RequestParam("name")String name, @RequestParam("status") CurrentSituation status,
            @RequestParam("status1") CurrentSituation status1) {
        return ResponseEntity.ok().body(orderService.findOrderByUnderServiceAndStatus(name, status, status1).stream()
                .map(orderCustomer -> mapper.map(orderCustomer, OrderDto.class)).collect(Collectors.toList()));
    }

}
