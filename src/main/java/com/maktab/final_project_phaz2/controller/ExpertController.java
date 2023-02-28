package com.maktab.final_project_phaz2.controller;

import com.maktab.final_project_phaz2.date.dto.*;
import com.maktab.final_project_phaz2.date.model.Expert;
import com.maktab.final_project_phaz2.date.model.Offer;
import com.maktab.final_project_phaz2.date.model.enumuration.ApprovalStatus;
import com.maktab.final_project_phaz2.date.model.enumuration.CurrentSituation;
import com.maktab.final_project_phaz2.exception.NoResultException;
import com.maktab.final_project_phaz2.service.*;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
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
    private final OpinionService opinionService;
    private final OrderService orderService;
    private final ImageService imageService;
    private final OfferService offerService;

    @PostMapping("/add_expert")
    public ResponseEntity<String> addExpert(@Valid @RequestBody ExpertDto expertDto
            , HttpServletRequest request) throws MessagingException {
        Expert expert = mapper.map(expertDto, Expert.class);
        expertService.registerExpert(expert, getSiteURL(request));
        return ResponseEntity.ok().body("**you are registered as an expert,confirm the code sent to your email to validate**");
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @GetMapping("/verify")
    public String verifyUser(@RequestParam("code") String code) {
        if (expertService.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }

    @GetMapping("/logIn_expert")
    public ResponseEntity<UserDto> getByEmailAddress(@RequestParam("emailAddress") String emailAddress,
                                                     @RequestParam("password") String password) throws NoResultException {
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
    public ResponseEntity<String> changePassword(@Valid @RequestBody PasswordDto passwordDto) {
        expertService.changePassword(passwordDto.getNewPassword(), passwordDto.getConfirmPassword());
        return ResponseEntity.ok().body("password changed :)");
    }

    @PostMapping("/save-offer")
    public ResponseEntity<String> offerAnSubmitByExpert(@Valid @RequestBody OfferDto offerDto,
                                                        @RequestParam("idUnderService") Long idUnderService,
                                                        @RequestParam("idOrder") Long idOrder) {
        Offer offer = mapper.map(offerDto, Offer.class);
        expertService.OfferAnSubmit(offer, idUnderService, idOrder);
        return ResponseEntity.ok().body("this offer saved :)");
    }

    @GetMapping("/under-service-by-status")
    public ResponseEntity<List<OrderDto>> ListUnderRelatedExpertInStatus(
            @RequestParam("name") String name, @RequestParam("status") CurrentSituation status,
            @RequestParam("status1") CurrentSituation status1) {
        return ResponseEntity.ok().body(orderService.findOrderByUnderServiceAndStatus(name, status, status1).stream()
                .map(orderCustomer -> mapper.map(orderCustomer, OrderDto.class)).collect(Collectors.toList()));
    }

    @GetMapping("/show-scores")
    public ResponseEntity<List<OpinionDto>> ListOrder() {
        return ResponseEntity.ok().body(opinionService.showOpinionToExpert().stream()
                .map(opinion -> mapper.map(opinion, OpinionDto.class)).collect(Collectors.toList()));
    }

    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadImage(@RequestParam("idExpert") Long idExpert, @RequestParam("image") MultipartFile file) throws IOException {
        imageService.uploadImage(file, idExpert);
        return ResponseEntity.ok().body("your image saved :)");
    }

    @GetMapping("/get-image")
    public ResponseEntity<byte[]> getImageFromDatabase() {
        byte[] image = imageService.getImage();
        return ResponseEntity.ok().contentType(MediaType.valueOf("image/jpeg")).body(image);
    }

    @GetMapping("/show-alone-score")
    public ResponseEntity<Long> showExpertScore() {
        return ResponseEntity.ok().body(expertService.showScoreWithoutDescription());
    }

    @GetMapping("/show-all-offer-by-expert")
    public ResponseEntity<List<OfferDto>> showAllExpertOffer(@RequestParam("emailAddress") String emailAddress) {
        return ResponseEntity.ok().body(offerService.showAllOfferByExpert(emailAddress).stream()
                .map(offer -> mapper.map(offer, OfferDto.class)).collect(Collectors.toList()));
    }

    @GetMapping("/show-credit-expert")
    public ResponseEntity<Double> showExpertAmount() {
        return ResponseEntity.ok().body(expertService.showAmountToExpert());
    }

    @GetMapping("/show-all-order-for-expert")
    public ResponseEntity<List<OrderCustomerDto>> showAllOrderForCustomer(@RequestParam("status") CurrentSituation status) {
        return ResponseEntity.ok().body(orderService.showAllOrderExpert(status).stream().
                map(orderCustomer -> mapper.map(orderCustomer, OrderCustomerDto.class)).collect(Collectors.toList()));
    }
}
