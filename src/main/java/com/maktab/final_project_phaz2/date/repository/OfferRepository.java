package com.maktab.final_project_phaz2.date.repository;

import com.maktab.final_project_phaz2.date.model.Offer;
import com.maktab.final_project_phaz2.date.model.OrderCustomer;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findAllByOrdersCustomer(OrderCustomer orderCustomer, Sort sort);
   // Optional<Offer> findByI(Long id);
   // @Query("select e from Offer e  where e.id=")

}
