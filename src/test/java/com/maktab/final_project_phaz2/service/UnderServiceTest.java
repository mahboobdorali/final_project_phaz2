package com.maktab.final_project_phaz2.service;

import com.maktab.final_project_phaz2.date.model.MainTask;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

 class UnderServiceTest {
    @Autowired
    private ServiceUnderService serviceUnderService;
    UnderService underService1=UnderService.builder().nameSubService("sweep the room").basePrice(50000).briefExplanation("sweeping the room in tehran").build();
    UnderService underService2=UnderService.builder().nameSubService("wipe thw glass").basePrice(35000).briefExplanation("cleaning the glass of a three-story house").build();

    @BeforeAll
    static void setup(@Autowired DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("UserServiceData.sql"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        @Test
        @Order(1)
        void registerUnderService()   {
           serviceUnderService.saveAllUnderService(underService1);
            org.assertj.core.api.Assertions.assertThat(underService1.getId()).isGreaterThan(0);
        }

        @Test
        @Order(2)
        void getListOfMainTask() {
            List<UnderService> allUnderService = serviceUnderService.getAllUnderService();
            org.assertj.core.api.Assertions.assertThat(allUnderService.size()).isGreaterThan(0);
        }

        @Test
        @Order(3)
        void addServiceByAdmin() throws DuplicateEntryException {
            serviceUnderService.addUnderServiceByAdmin(underService2);
            org.junit.jupiter.api.Assertions.assertTrue(true, "exist this underService ***");
        }
    }
