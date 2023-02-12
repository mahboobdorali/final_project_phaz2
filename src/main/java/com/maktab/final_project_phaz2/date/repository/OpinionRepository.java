package com.maktab.final_project_phaz2.date.repository;

import com.maktab.final_project_phaz2.date.model.Offer;
import com.maktab.final_project_phaz2.date.model.Opinion;
import com.maktab.final_project_phaz2.date.model.OrderCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpinionRepository extends JpaRepository<Opinion,Long> {
    @Query("select o from Opinion o where o.expert.emailAddress=:emailAddress")
    List<Opinion>showAllScore (String emailAddress);

}
