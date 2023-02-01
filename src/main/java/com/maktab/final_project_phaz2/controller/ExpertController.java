package com.maktab.final_project_phaz2.controller;

import com.maktab.final_project_phaz2.date.dto.ExpertDto;
import com.maktab.final_project_phaz2.date.model.Expert;
import com.maktab.final_project_phaz2.exception.NoResultException;
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

    @PostMapping("/add_expert")
    public ResponseEntity<String> addExpert(@RequestBody ExpertDto expertDto) {
        Expert expert = mapper.map(expertDto, Expert.class);
        expertService.registerExpert(expert);
        return ResponseEntity.ok().body("**you are registered as an expert**");
    }
    @GetMapping("/logIn_expert")
    public ResponseEntity<Expert> getByEmailAddress(@RequestParam("emailAddress") String emailAddress,@RequestParam("password") String password) throws NoResultException {
        return ResponseEntity.ok().body(expertService.logInExpert(emailAddress,password));
    }
    @PutMapping("/update_expert")
    public ResponseEntity<Expert> update(@RequestBody ExpertDto expertDto){
        Expert expert = mapper.map(expertDto, Expert.class);
        return ResponseEntity.ok().body(expertService.updateExpert(expert));
    }
    @DeleteMapping("/delete_expert")
    public ResponseEntity<String>delete(@RequestBody ExpertDto expertDto){
        Expert expert = mapper.map(expertDto, Expert.class);
        expertService.deleteExpert(expert);
        return ResponseEntity.ok().body("expert delete :)");
    }
    @GetMapping("/getAll_expert")
    public ResponseEntity<List<Expert>>getAllExpert(){
        return ResponseEntity.ok().body(expertService.getAllExpert());
    }
    @GetMapping("/findExpertById")
    public ResponseEntity<Expert>getById(@RequestParam("id")@Min(1) Long id) throws NoResultException {
        return ResponseEntity.ok().body(expertService.findExpertById(id));
    }
    @GetMapping("/findExpertByEmail")
    public ResponseEntity<Expert>getByEmail(@RequestParam("emailAddress") String emailAddress) throws NoResultException {
        return ResponseEntity.ok().body(expertService.findExpertByEmail(emailAddress));
    }
   /* @PutMapping("/change_Password")
    public ResponseEntity<String>changePassword(@RequestParam("emailAddress")String emailAddress,@RequestParam("oldPassword")String oldPassword,@RequestParam("newPassword")String newPassword){
        return ResponseEntity.ok().body(expertService.changePassword(emailAddress,oldPassword,newPassword));
    }*/

}
