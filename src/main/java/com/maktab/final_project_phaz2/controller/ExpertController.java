package com.maktab.final_project_phaz2.controller;

import com.maktab.final_project_phaz2.date.dto.*;
import com.maktab.final_project_phaz2.date.model.Expert;
import com.maktab.final_project_phaz2.date.model.Offer;
import com.maktab.final_project_phaz2.date.model.enumuration.ApprovalStatus;
import com.maktab.final_project_phaz2.date.model.enumuration.CurrentSituation;
import com.maktab.final_project_phaz2.exception.NoResultException;
import com.maktab.final_project_phaz2.service.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/expert")
@RequiredArgsConstructor
public class ExpertController {
    private final ModelMapper mapper;
    private final ExpertService expertService;
    private final AdminService adminService;
    private final OpinionService opinionService;
    private final OrderService orderService;
    private final ImageService imageService;
    private final OfferService offerService;

    @PostMapping("/add_expert")
    public ResponseEntity<String> addExpert(@Valid @RequestBody ExpertDto expertDto) {
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
    public ResponseEntity<ExpertDto> update(@Valid @RequestBody ExpertDto expertDto) {
        Expert expert = mapper.map(expertDto, Expert.class);
        Expert expert1 = expertService.updateExpert(expert);
        ExpertDto expertDto1 = mapper.map(expert1, ExpertDto.class);
        return ResponseEntity.ok().body(expertDto1);
    }

    @DeleteMapping("/delete_expert")
    public ResponseEntity<String> delete(@Valid @RequestBody ExpertDto expertDto) {
        Expert expert = mapper.map(expertDto, Expert.class);
        expertService.deleteExpert(expert);
        return ResponseEntity.ok().body("expert delete :)");
    }

    @GetMapping("/get-all_expert")
    public ResponseEntity<List<UserDto>> getAllExpert() {
        return ResponseEntity.ok().body(expertService.getAllExpert().stream().
                map(expert -> mapper.map(expert, UserDto.class)).collect(Collectors.toList()));
    }

    @GetMapping("/find-expert-by-id")
    public ResponseEntity<UserDto> getById(@RequestParam("id") @Min(1) Long id) {
        Expert expertById = expertService.findExpertById(id);
        UserDto userDto = mapper.map(expertById, UserDto.class);
        return ResponseEntity.ok().body(userDto);
    }

    @GetMapping("/find-expert-by-email")
    public ResponseEntity<UserDto> getByEmail(@RequestParam("emailAddress") String emailAddress) {
        Expert expertByEmail = expertService.findExpertByEmail(emailAddress);
        UserDto userDto = mapper.map(expertByEmail, UserDto.class);
        return ResponseEntity.ok().body(userDto);
    }

    @PutMapping("/change_password")
    public ResponseEntity<String> changePassword(@RequestParam("emailAddress") String emailAddress,
                                                 @RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) {
        expertService.changePassword(emailAddress, oldPassword, newPassword);
        return ResponseEntity.ok().body("password changed :)");
    }

    @PostMapping("/save-offer")
    public ResponseEntity<String> offerAnSubmitByExpert(@Valid @RequestBody OfferDto offerDto, @RequestParam("idUnderService") Long idUnderService,
                                                        @RequestParam("idOrder") Long idOrder) {
        Offer offer = mapper.map(offerDto, Offer.class);
        expertService.OfferAnSubmit(offer, idUnderService, idOrder);
        return ResponseEntity.ok().body("this offer saved :)");
    }

    @PostMapping("/set-expert-id")
    public ResponseEntity<String> setExpert(@RequestParam Long idOffer, @RequestParam Long idExpert) {
        expertService.setExpertToOffer(idOffer, idExpert);
        return ResponseEntity.ok().body("this expert offer an submit");
    }

    @PutMapping("/change-status")
    public ResponseEntity<String> convertExpertStatus(@RequestParam("emailAddress") String emailAddress) {
        adminService.convertStatus(emailAddress);
        return ResponseEntity.ok().body("expert status changed to approved:)");
    }

    @PostMapping("/add-expert-to-under-service")
    public ResponseEntity<String> addExpertToUnderServiceByAdmin(@RequestParam("idUnderService") Long idUnderService, @RequestParam("idExpert") Long idExpert) {
        adminService.addExpertToUnderService(idUnderService, idExpert);
        return ResponseEntity.ok().body("this expert add to underService");
    }

    @DeleteMapping("/delete-expert-from-under-service")
    public ResponseEntity<String> deleteExpertFromUnderServiceByAdmin(@RequestParam("idUnderService") Long idUnderService, @RequestParam("idExpert") Long idExpert) {
        adminService.deleteExpertFromUnderService(idUnderService, idExpert);
        return ResponseEntity.ok().body("this expert deleted from underService");
    }

    @GetMapping("/under-service-by-status")
    public ResponseEntity<List<OrderDto>> ListUnderRelatedExpertInStatus(
            @RequestParam("name") String name, @RequestParam("status") CurrentSituation status,
            @RequestParam("status1") CurrentSituation status1) {
        return ResponseEntity.ok().body(orderService.findOrderByUnderServiceAndStatus(name, status, status1).stream()
                .map(orderCustomer -> mapper.map(orderCustomer, OrderDto.class)).collect(Collectors.toList()));
    }

    @GetMapping("/list-orders-for-payed")
    public ResponseEntity<List<OrderDto>> ListOrderCustomerForPayed(@RequestParam("currentSituation") CurrentSituation currentSituation
            , @RequestParam("emailAddress") String emailAddress) {
        return ResponseEntity.ok().body(orderService.findOrderByStatusForPayedByCustomer(emailAddress, currentSituation).stream()
                .map(orderCustomer -> mapper.map(orderCustomer, OrderDto.class)).collect(Collectors.toList()));
    }

    @GetMapping("/show-orders")
    public ResponseEntity<List<OpinionShowScoreDto>> ListOrder(@RequestParam("emailAddress") String emailAddress) {
        return ResponseEntity.ok().body(opinionService.showOrdersToExpert(emailAddress).stream()
                .map(opinion -> mapper.map(opinion, OpinionShowScoreDto.class)).collect(Collectors.toList()));
    }

    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadImage(@RequestParam("idExpert") Long idExpert, @RequestParam("image") MultipartFile file) throws IOException {
        imageService.uploadImage(file, idExpert);
        return ResponseEntity.ok().body("your image saved :)");
    }

    @GetMapping("/get-image")
    public ResponseEntity<byte[]> getImageFromDatabase(@RequestParam("emailAddress") String emailAddress) {
        byte[] image = imageService.getImage(emailAddress);
        return ResponseEntity.ok().contentType(MediaType.valueOf("image/jpeg")).body(image);
    }

    @GetMapping("/get-all-new-expert")
    public ResponseEntity<List<ExpertDtoForFilter>> findAllExpertNewStatus(@RequestParam("status") ApprovalStatus status) {
        return ResponseEntity.ok().body(expertService.findAllByNewStatus(status).stream()
                .map(expert -> mapper.map(expert, ExpertDtoForFilter.class)).collect(Collectors.toList()));
    }

    @GetMapping("/show-alone-score")
    public ResponseEntity<Long>showExpertScore(@RequestParam("expertEmail") String expertEmail) {
        return ResponseEntity.ok().body(expertService.showScoreWithoutDescription(expertEmail));
    }

    @GetMapping("/show-all-offer-by-expert")
    public ResponseEntity<List<OfferDto>> showAllExpertOffer(@RequestParam("emailAddress") String emailAddress) {
        return ResponseEntity.ok().body(offerService.showAllOfferByExpert(emailAddress).stream()
                .map(offer -> mapper.map(offer, OfferDto.class)).collect(Collectors.toList()));
    }
}
