package com.maktab.final_project_phaz2.service;

import com.maktab.final_project_phaz2.date.model.UnderService;
import com.maktab.final_project_phaz2.exception.DuplicateEntryException;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UnderServiceTest {
    @Autowired
    private ServiceUnderService serviceUnderService;
    @Autowired
    private AdminService adminService;
    UnderService underService1 = UnderService.builder().nameSubService("sweep the room").basePrice(50000).briefExplanation("sweeping the room in tehran").build();
    UnderService underService2 = UnderService.builder().nameSubService("wipe the glass").basePrice(35000).briefExplanation("cleaning the glass of a three-story house").build();

    @BeforeAll
    static void setup(@Autowired DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("UserServiceData.sql"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   /* @Test
    @Order(1)
    void addUnderServiceByAdmin()  {
        serviceUnderService.addUnderServiceByAdmin(underService2);
        org.junit.jupiter.api.Assertions.assertTrue(true, "exist this underService ***");
    }*/


    @Test
    @Order(2)
    void getListOfMainTask() {
        List<UnderService> allUnderService = serviceUnderService.getAllUnderService();
        org.assertj.core.api.Assertions.assertThat(allUnderService.size()).isGreaterThan(0);
    }

  /*  @Test
    @Order(3)
    void changeDescriptionUnderServiceTest() {
        String newDescription = "washing the carpet";
        UnderService underService = adminService.changeDescription(newDescription, underService2);
        assertEquals(newDescription, underService.getBriefExplanation());
    }*/

 /*   @Test
    @Order(4)
    void changePriceUnderServiceTest()  {
        double newPrice = 400000;
        UnderService underService = adminService.changePriceUnderService(newPrice, underService2);
        assertEquals(newPrice, underService.getBasePrice());
    }
*/
    @Test
    @Order(6)
    void deleteUnderService() {
        try {
            UnderService underServiceById = serviceUnderService.findUnderServiceById(1L);
            serviceUnderService.deleteAllUnderService(underServiceById);
            serviceUnderService.findUnderServiceById(1L);
            org.assertj.core.api.Assertions.assertThat(underService2).isNull();
        } catch (NoResultException e) {
            assertEquals("this underService is not exist", e.getMessage());
        }
    }
}
