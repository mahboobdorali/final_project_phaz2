
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

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void addServiceByAdmin() throws DuplicateEntryException {
        mainTaskService.addServiceByAdmin(mainTask1);
        org.junit.jupiter.api.Assertions.assertTrue(true, "***exist this service***");
    }

    @Test
    @Order(2)
    void getListOfMainTask() {
        List<MainTask> allMainTask = mainTaskService.getAllService();
        org.assertj.core.api.Assertions.assertThat(allMainTask.size()).isGreaterThan(0);
    }

    @Test
    @Order(3)
    void editMainTask() throws NoResultException {
        MainTask mainTask2 = mainTaskService.findServiceByName(mainTask1.getName());
        mainTask2.setName("sweep the room!!");
        MainTask task = mainTaskService.editService(mainTask2);
        Assertions.assertThat(task.getName()).isEqualTo("sweep the room!!");
    }

    @Test
    @Order(4)
    void deleteMainTask() {
        try {
            MainTask serviceById = mainTaskService.findServiceById(1L);
            mainTaskService.deleteAllService(serviceById);
            mainTaskService.findServiceById(1L);
            Assertions.assertThat(mainTask1).isNull();
        } catch (NoResultException e) {
            assertEquals("this service is not exist", e.getMessage());
        }
    }
}


