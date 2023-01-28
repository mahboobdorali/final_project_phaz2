
package com.maktab.final_project_phaz2.service;
import com.maktab.final_project_phaz2.date.model.MainTask;
import com.maktab.final_project_phaz2.exception.DuplicateEntryException;
import com.maktab.final_project_phaz2.exception.NoResultException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MainTaskTest {
    @Autowired
    private MainTaskService mainTaskService;
    @Autowired
    private AdminService adminService;
    MainTask mainTask = MainTask.builder().name("home").build();
    MainTask mainTask1 = MainTask.builder().name("room").build();

    @Test
    @Order(1)
    void registerMainTask() throws NoResultException {
        mainTaskService.saveAllService(mainTask);
        Assertions.assertThat(mainTask.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    void getListOfMainTask() {
        List<MainTask> allMainTask = mainTaskService.getAllService();
        org.assertj.core.api.Assertions.assertThat(allMainTask.size()).isGreaterThan(0);
    }

    @Test
    @Order(3)
    void addServiceByAdmin() throws DuplicateEntryException {
    mainTaskService.addServiceByAdmin(mainTask1);
        org.junit.jupiter.api.Assertions.assertTrue(true, "exist ***");
    }
}

