package com.maktab.final_project_phaz2.date.repository;

import com.maktab.final_project_phaz2.date.model.Customer;
import com.maktab.final_project_phaz2.date.model.Offer;
import com.maktab.final_project_phaz2.date.model.OrderCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OfferRepository extends JpaRepository<Offer, Long>, JpaSpecificationExecutor<Offer> {
    @Query("select o from Offer o where o.orderCustomer=:orderCustomer order by o.priceOffer desc")
    List<Offer> sortOfferByPriceOffer(OrderCustomer orderCustomer);

    @Query("select o from Offer o where o.orderCustomer=:orderCustomer order by o.expert.averageScore desc")
    List<Offer> sortOfferByScore(OrderCustomer orderCustomer);

    @Query("select e from Offer e where e.expert.emailAddress=:emailAddress")
    List<Offer> listOfferByExpert(String emailAddress);
}
