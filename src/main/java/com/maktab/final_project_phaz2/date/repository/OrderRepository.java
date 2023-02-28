package com.maktab.final_project_phaz2.date.repository;

import com.maktab.final_project_phaz2.date.model.Customer;
import com.maktab.final_project_phaz2.date.model.OrderCustomer;
import com.maktab.final_project_phaz2.date.model.UnderService;
import com.maktab.final_project_phaz2.date.model.enumuration.CurrentSituation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderCustomer, Long>, JpaSpecificationExecutor<OrderCustomer> {
    List<OrderCustomer> findAllByUnderService(UnderService underService);

    @Transactional
    @Query("select o from OrderCustomer o where o.underService.nameSubService=:nameSubService" +
            " and o.currentSituation=:currentSituation or" +
            " o.currentSituation=:currentSituation1")
    List<OrderCustomer> findAllByUnderService(String nameSubService,CurrentSituation currentSituation,
                                              CurrentSituation currentSituation1);

    @Transactional
    @Query("select o from OrderCustomer o where o.customer.emailAddress=:emailAddress and o.currentSituation=:currentSituation")
    List<OrderCustomer> findAllByStatus(String emailAddress, CurrentSituation currentSituation);


    @Transactional
    @Query("select o from OrderCustomer o where o.expert.emailAddress=:emailAddress and o.currentSituation=:currentSituation")
    List<OrderCustomer> findAllByExpert(String emailAddress,CurrentSituation currentSituation);

    @Transactional
    @Query("select count (o.customer)from OrderCustomer o where o.customer.emailAddress=:emailAddress")
    int countOrderCustomer(String emailAddress);

    @Transactional
    @Query("select count(o.expert)from OrderCustomer o where o.expert.emailAddress=:emailAddress and o.currentSituation=:currentSituation")
    int countOrderExpert(String emailAddress, CurrentSituation currentSituation);

}
