package com.maktab.final_project_phaz2.service;

import com.maktab.final_project_phaz2.Util.DateUtil;
import com.maktab.final_project_phaz2.date.model.CustomerAddress;
import com.maktab.final_project_phaz2.date.model.Expert;
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
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OfferServiceTest {
    @Autowired
    private OfferService offerService;
    @Autowired
    private ExpertService expertService;

    LocalDateTime localDate = LocalDateTime.of(2024, 2, 26, 5, 15, 12);
    LocalDateTime localDateCustomer = LocalDateTime.of(2025, 4, 6, 8, 19);
    Date timeToStartWork = DateUtil.changeLocalDateToDate(localDate);
    Date orderTimeToStartWork = DateUtil.changeLocalDateToDate(localDateCustomer);
    CustomerAddress customerAddress=CustomerAddress.builder().city("ben").plaque("6").street("janbazan").build();
    OrderCustomer orderCustomer=OrderCustomer.builder().proposedPrice(90000).jobDescription("washin carpet").dateAndTimeOfWork(orderTimeToStartWork).customerAddress(customerAddress).currentSituation(CurrentSituation.WAITING_FOR_EXPERT_ADVICE).build();
    Offer offer = Offer.builder().priceOffer(70000).ordersCustomer(orderCustomer).durationOfWork(Duration.of(2, ChronoUnit.HOURS)).TimeProposeToStartWork(timeToStartWork).build();

    /*  @BeforeAll
      static void setup(@Autowired DataSource dataSource) {
          try (Connection connection = dataSource.getConnection()) {
              ScriptUtils.executeSqlScript(connection, new ClassPathResource("OfferServiceData.sql"));
          } catch (SQLException e) {
              e.printStackTrace();
          }
      }*/
    @Test
    @Order(1)
    void registerOffer() throws NoResultException {
        CurrentSituation currentSituation=CurrentSituation.WAITING_FOR_SPECIALIST_SELECTION;
        Offer offer1 = expertService.OfferAnSubmit(offer);
        assertEquals(offer1.getOrdersCustomer().getCurrentSituation(),currentSituation);
    }
}
