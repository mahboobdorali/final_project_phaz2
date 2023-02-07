package com.maktab.final_project_phaz2.controller;

import com.maktab.final_project_phaz2.date.dto.ExpertDto;
import com.maktab.final_project_phaz2.date.dto.OfferDto;
import com.maktab.final_project_phaz2.date.model.Expert;
import com.maktab.final_project_phaz2.date.model.Offer;
import com.maktab.final_project_phaz2.exception.NoResultException;
import com.maktab.final_project_phaz2.service.AdminService;
import com.maktab.final_project_phaz2.service.ExpertService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/expert")
@RequiredArgsConstructor
public class ExpertController {
    private final ModelMapper mapper;
    private final ExpertService expertService;
    private final AdminService adminService;

    @PostMapping("/add_expert")
    public ResponseEntity<String> addExpert(@RequestBody ExpertDto expertDto) {
        Expert expert = mapper.map(expertDto, Expert.class);
        expertService.registerExpert(expert);
        return ResponseEntity.ok().body("**you are registered as an expert**");
    }

    @GetMapping("/logIn_expert")
    public ResponseEntity<Expert> getByEmailAddress(@RequestParam("emailAddress") String emailAddress, @RequestParam("password") String password) throws NoResultException {
        return ResponseEntity.ok().body(expertService.logInExpert(emailAddress, password));
    }

    @PutMapping("/update_expert")
    public ResponseEntity<Expert> update(@RequestBody ExpertDto expertDto) {
        Expert expert = mapper.map(expertDto, Expert.class);
        return ResponseEntity.ok().body(expertService.updateExpert(expert));
    }

    @DeleteMapping("/delete_expert")
    public ResponseEntity<String> delete(@RequestBody ExpertDto expertDto) {
        Expert expert = mapper.map(expertDto, Expert.class);
        expertService.deleteExpert(expert);
        return ResponseEntity.ok().body("expert delete :)");
    }

    @GetMapping("/getAll_expert")
    public ResponseEntity<List<Expert>> getAllExpert() {
        return ResponseEntity.ok().body(expertService.getAllExpert());
    }

    @GetMapping("/findExpertById")
    public ResponseEntity<Expert> getById(@RequestParam("id") @Min(1) Long id) {
        return ResponseEntity.ok().body(expertService.findExpertById(id));
    }

    @GetMapping("/findExpertByEmail")
    public ResponseEntity<Expert> getByEmail(@RequestParam("emailAddress") String emailAddress) {
        return ResponseEntity.ok().body(expertService.findExpertByEmail(emailAddress));
    }

    @PutMapping("/change_Password")
    public ResponseEntity<String> changePassword(@RequestParam("emailAddress") String emailAddress, @RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) {
        expertService.changePassword(emailAddress, oldPassword, newPassword);
        return ResponseEntity.ok().body("password changed :)");
    }

    @PostMapping("/saveOffer")
    public ResponseEntity<String> offerAnSubmitByExpert(@RequestBody OfferDto offerDto,@RequestParam("idUnderService")Long idUnderService,
                                                        @RequestParam("idOrder")Long idOrder) {
        Offer offer = mapper.map(offerDto, Offer.class);
        expertService.OfferAnSubmit(offer,idUnderService,idOrder);
        return ResponseEntity.ok().body("this offer saved :)");
    }
    @PostMapping("/setExpertId")
    public ResponseEntity<String>serExpert(@RequestParam Long idOffer,@RequestParam Long idExpert){
        expertService.setExpertToOffer(idOffer, idExpert);
        return ResponseEntity.ok().body("this expert offer an submit");
    }

    @PutMapping("/changeStatus")
    public ResponseEntity<String> convertExpertStatus(@RequestParam("emailAddress") String emailAddress) {
        adminService.convertStatus(emailAddress);
        return ResponseEntity.ok().body("expert status changed to approved:)");
    }
   @PostMapping("/addExpertToUnderService")
   public ResponseEntity<String> addExpertToUnderServiceByAdmin(@RequestParam("idUnderService") Long idUnderService, @RequestParam("idExpert") Long idExpert ) {
       adminService.addExpertToUnderService(idUnderService,idExpert);
       return ResponseEntity.ok().body("this expert add to underService");
   }

    @DeleteMapping("/deleteExpertFromUnderService")
   public ResponseEntity<String> deleteExpertFromUnderServiceByAdmin(@RequestParam("idUnderService") Long idUnderService, @RequestParam("idExpert") Long idExpert ) {
        adminService.deleteExpertFromUnderService(idUnderService,idExpert);
       return ResponseEntity.ok().body("this expert deleted from underService");
   }
}
