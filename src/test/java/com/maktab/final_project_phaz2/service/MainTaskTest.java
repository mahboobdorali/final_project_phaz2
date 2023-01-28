
package com.maktab.final_project_phaz2.service;

import com.maktab.final_project_phaz2.date.model.Customer;
import com.maktab.final_project_phaz2.date.model.MainTask;
import com.maktab.final_project_phaz2.exception.NoResultException;
import com.sun.tools.javac.Main;
import org.assertj.core.api.Assertions;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

 class MainTaskTest {
    @Autowired
    private MainTaskService mainTaskService;
    MainTask mainTask= MainTask.builder().name("home").build();
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
    void registerMainTask() throws NoResultException {
        mainTaskService.saveAllService(mainTask);
        Assertions.assertThat(mainTask.getId()).isGreaterThan(0);
    }
}

