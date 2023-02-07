package com.maktab.final_project_phaz2.date.repository;

import com.maktab.final_project_phaz2.date.model.OrderCustomer;
import com.maktab.final_project_phaz2.date.model.UnderService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderCustomer, Long> {
    Optional<OrderCustomer> findById(Long id);
    List<OrderCustomer>findAllByUnderService(UnderService underService);
}
