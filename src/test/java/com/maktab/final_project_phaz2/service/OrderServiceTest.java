package com.maktab.final_project_phaz2.service;

import com.maktab.final_project_phaz2.Util.DateUtil;
import com.maktab.final_project_phaz2.date.model.CustomerAddress;
import com.maktab.final_project_phaz2.date.model.Offer;
import com.maktab.final_project_phaz2.date.model.OrderCustomer;
import com.maktab.final_project_phaz2.date.model.enumuration.CurrentSituation;
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
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderServiceTest {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderService orderService;
    LocalDateTime localDate = LocalDateTime.of(2023, 12, 26, 8, 15, 12);
    Date orderTimeToWork = DateUtil.changeLocalDateToDate(localDate);

    CustomerAddress customerAddress = CustomerAddress.builder().city("ben").plaque("6").street("janbazan").build();

    OrderCustomer orderCustomer = OrderCustomer.builder().proposedPrice(200000).jobDescription("electrical repair of the building").dateAndTimeOfWork(orderTimeToWork).customerAddress(customerAddress).build();

 /*   @BeforeAll
    static void setup(@Autowired DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("OrderServiceData.sql"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    @Test
    @Order(1)
    void registerOrder() throws NoResultException {
        OrderCustomer order = customerService.Order(orderCustomer, 1L);
        CurrentSituation currentSituation = CurrentSituation.WAITING_FOR_EXPERT_ADVICE;
        assertEquals(order.getCurrentSituation(), currentSituation);
    }
/*
   @Test
    @Order(2)
    void getListOfOrder() {
        List<OrderCustomer> allOrders = orderService.getAllOrders();
        org.assertj.core.api.Assertions.assertThat(allOrders.size()).isGreaterThan(0);
    }

    @Test
    @Order(3)
    void editOrderTest() throws NoResultException {
        OrderCustomer orderById = orderService.findOrderById(1L);
        orderById.setProposedPrice(170000);
        orderService.updateOrder(orderById);
        org.assertj.core.api.Assertions.assertThat(orderById.getProposedPrice()).isEqualTo(170000);

    }*/
    @Test
    @Order(4)
    void sortPriceTest(){
        List<Offer> offerList = customerService.sortByPrice(orderCustomer);
        assertEquals(10000000,offerList.get(1).getPriceOffer());
        assertEquals(20000000,offerList.get(2).getPriceOffer());
    }

  /*  @Test
    @Order(5)
    void deleteOffer() {
        try {
            OrderCustomer orderById = orderService.findOrderById(1L);
            orderService.deleteAllOrder(orderById);
            orderService.findOrderById(1L);
            org.assertj.core.api.Assertions.assertThat(orderById).isNull();
        } catch (NoResultException e) {
            assertEquals("this order is not exist", e.getMessage());
        }
    }*/
}
