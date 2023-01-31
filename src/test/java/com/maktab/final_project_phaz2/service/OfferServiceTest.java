package com.maktab.final_project_phaz2.service;

import com.maktab.final_project_phaz2.Util.DateUtil;
import com.maktab.final_project_phaz2.date.model.*;
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
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OfferServiceTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ExpertService expertService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OfferService offerService;

    LocalDateTime localDate = LocalDateTime.of(2023, 3, 2, 6, 15, 12);
    Date timeToStartWork = DateUtil.changeLocalDateToDate(localDate);
    Offer offer = Offer.builder().priceOffer(170000).ordersCustomer(null).durationOfWork(Duration.of(2, ChronoUnit.HOURS)).TimeProposeToStartWork(timeToStartWork).build();


    LocalDateTime localDateCustomer2 = LocalDateTime.of(2023, 11, 9, 8, 19);
    Date timeToStartWork2 = DateUtil.changeLocalDateToDate(localDateCustomer2);
    Offer offer2 = Offer.builder().priceOffer(70000).ordersCustomer(null).durationOfWork(Duration.of(1, ChronoUnit.HOURS)).TimeProposeToStartWork(timeToStartWork2).build();


    @BeforeAll
    static void setup(@Autowired DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("OfferServiceData.sql"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(1)
    void registerOffer() throws NoResultException {
        CurrentSituation currentSituation = CurrentSituation.WAITING_FOR_SPECIALIST_SELECTION;
        OrderCustomer orderById = orderService.findOrderById(1L);
        offer.setOrdersCustomer(orderById);
        Offer offer1 = expertService.OfferAnSubmit(offer);
        offer2.setOrdersCustomer(orderById);
        expertService.OfferAnSubmit(offer2);
        assertEquals(offer1.getOrdersCustomer().getCurrentSituation(), currentSituation);
        assertEquals(offer2.getOrdersCustomer().getCurrentSituation(), currentSituation);
    }

    @Test
    @Order(2)
    void sortPriceTest() throws NoResultException {
        List<Offer> offerList = customerService.sortByPrice(1L);
        assertEquals(170000, offerList.get(1).getPriceOffer());
        assertEquals(70000, offerList.get(0).getPriceOffer());
    }

    @Test
    @Order(3)
    void sortScoreTest() throws NoResultException {
        List<Offer> offerList = customerService.sortByScore(1L);
        assertEquals(10, offerList.get(0).getExpert().getAverageScore());
        assertEquals(23, offerList.get(1).getExpert().getAverageScore());
    }


 /*   @Test
    @Order(3)
    void choiceOfferByCustomerTest() throws NoResultException {
        CurrentSituation currentSituation = CurrentSituation.WAITING_FOR_SPECIALIST_SELECTION_TO_COME;
        Offer offerByCustomer = customerService.selectOfferByCustomer(1L);
        assertEquals(offerByCustomer.getOrdersCustomer().getCurrentSituation(), currentSituation);
    }

    @Test
    @Order(4)
    void paidMoneyToExpertTest() throws NoResultException, InputInvalidException {
        Expert expert = customerService.paidForExpert(1L, "mona@gmail.com", "zahra@gmail.com");
        assertEquals(expert.getAmount(), 200000);
    }
    @Test
    @Order(5)
    void changeSituationTest() throws NoResultException {
        Offer offer1 = customerService.changeSituationForFinish(1L);
        CurrentSituation currentSituation = CurrentSituation.DONE;
        assertEquals(offer1.getOrdersCustomer().getCurrentSituation(), currentSituation);
    }
    @Test
    @Order(6)
    void changeOrderModeTest() throws NoResultException {
        Offer offerForChangeSituation = customerService.changeSituationByCustomer(1L, 1L);
        CurrentSituation currentSituation = CurrentSituation.STARTED;
        assertEquals(offerForChangeSituation.getOrdersCustomer().getCurrentSituation(), currentSituation);
    }


    @Test
    @Order(7)
    void getListOfOffer() {
        List<Offer> allOffer = offerService.getAllOffer();
        org.assertj.core.api.Assertions.assertThat(allOffer.size()).isGreaterThan(0);
    }

    @Test
    @Order(8)
    void updateOfferTest() throws NoResultException {
        Offer byId = offerService.findById(1L);
        byId.setPriceOffer(120000);
        Offer offer1 = offerService.updateOffer(byId);
        org.assertj.core.api.Assertions.assertThat(offer1.getPriceOffer()).isEqualTo(120000);

    }

    @Test
    @Order(9)
    void deleteOffer() {
        try {
            Offer offer1 = offerService.findById(1L);
            offerService.deleteOffer(offer1);
            offerService.findById(1L);
            org.assertj.core.api.Assertions.assertThat(offer1).isNull();
        } catch (NoResultException e) {
            assertEquals("this offer is not exist!!", e.getMessage());
        }
    }*/
}

