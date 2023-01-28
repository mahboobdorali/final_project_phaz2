//package com.maktab.final_project_phaz2.service;
//
//
//import com.maktab.final_project_phaz2.date.model.Expert;
//import com.maktab.final_project_phaz2.date.model.enumuration.Role;
//import org.junit.jupiter.api.*;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import static org.junit.jupiter.api.Assertions.*;
//
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class ExpertServiceTest {
//    @Autowired
//    private ExpertService expertService;
//
//
//    @Order(1)
//    @Test
//    public void saveCustomer() {
//        Expert expert = Expert.builder().name("mahboob").
//                family("dorali").emailAddress("mahboobdorali@gmail.com").
//                password("12qw@AM9!").role(Role.EXPERT).build();
//        expertService.registerExpert(expert);
//    }


  /*  @Test
    @Order(2)
    public void signInUser()  {
        try {
          Expert expert= expertService.logInExpert("mahboobdorali@gmail.com", "12qw@AM9!");
        } catch (NoResultException e) {
            assertEquals("this email is not exist",e.getMessage());
        }
    }*/
//}
