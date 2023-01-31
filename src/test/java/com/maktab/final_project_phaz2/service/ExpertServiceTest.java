package com.maktab.final_project_phaz2.service;

import com.maktab.final_project_phaz2.date.model.Expert;
import com.maktab.final_project_phaz2.date.model.enumuration.ApprovalStatus;
import com.maktab.final_project_phaz2.date.model.enumuration.Role;
import com.maktab.final_project_phaz2.exception.NoResultException;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExpertServiceTest {

    @Autowired
    private ExpertService expertService;
    @Autowired
    private AdminService adminService;
    Expert expert = Expert.builder().name("mahboob").
            family("dorali").emailAddress("mahboobdorali@gmail.com").
            password("12qw@AM!").role(Role.EXPERT).approvalStatus(ApprovalStatus.NEW)
            .underServiceList(null).build();

    @BeforeAll
    static void setup(@Autowired DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("ExpertServiceData.sql"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(1)
    void registerExpert() throws NoResultException {
        expertService.registerExpert(expert);

        Expert expertFind = expertService.findExpertByEmail(expert.getEmailAddress());
        assertNotNull(expertFind);
    }



    @Test
    @Order(3)
    void changePassword() throws NoResultException {
        String password = "12345";
        expertService.changePassword(expert.getEmailAddress(), expert.getPassword(), password);
        Expert expertFind = expertService.findExpertByEmail(expert.getEmailAddress());
        assertEquals(password, expertFind.getPassword());
    }

    @Test
    @Order(4)
    void changeStatus() throws NoResultException {
        ApprovalStatus approvalStatus = ApprovalStatus.ACCEPTED;
        adminService.convertStatus(expert.getEmailAddress());
        Expert expert1 = expertService.findExpertByEmail(expert.getEmailAddress());
        assertEquals(expert1.getApprovalStatus(), approvalStatus);

    }

    @Test
    @Order(5)
    void findExpertByEmail() throws NoResultException {
        Expert expertFind = expertService.findExpertByEmail(expert.getEmailAddress());
        assertNotNull(expertFind);
    }

    @Test
    @Order(6)
    void getListOfExpert() {
        List<Expert> allExpert = expertService.getAllExpert();
        org.assertj.core.api.Assertions.assertThat(allExpert.size()).isGreaterThan(0);
    }

    @Test
    @Order(7)
    void updateExpertTest() throws NoResultException {
        Expert expertFind = expertService.findExpertByEmail(expert.getEmailAddress());
        expertFind.setEmailAddress("ali@gmail.com");
        Expert expert1 = expertService.updateExpert(expertFind);
        assertEquals(expert1.getEmailAddress(), expertFind.getEmailAddress());
    }
    @Test
    @Order(8)
    void addExpertToUnderServiceTest() throws NoResultException {
        Expert expert1 = adminService.addExpertToUnderService(1L, 1L);
        org.assertj.core.api.Assertions.assertThat(expert1.getId()).isGreaterThan(0);

    }
    @Test
    @Order(9)
    void saveImageTest() throws NoResultException {
        Expert expert1 = expertService.saveImage("ali@gmail.com");
        assertNotNull(expert1);
    }
    @Test
    @Order(10)
    void deleteExpertFromUnderServiceTest() throws NoResultException {
        Expert expert1 = adminService.deleteExpertFromUnderService(1L, 1L);
        org.assertj.core.api.Assertions.assertThat(expert1.getUnderServiceList().size()).isEqualTo(0);
    }
  /*  @Test
    @Order(10)
    void deleteExpertTest() {
        try {
            Expert expertFind = expertService.findExpertByEmail(expert.getEmailAddress());
            expertService.deleteExpert(expertFind);
            Expert expertFind1 = expertService.findExpertByEmail(expert.getEmailAddress());
            org.assertj.core.api.Assertions.assertThat(expertFind1).isNull();

        } catch (NoResultException e) {
            assertEquals("this expert dose not exist", e.getMessage());
        }
    }*/
}