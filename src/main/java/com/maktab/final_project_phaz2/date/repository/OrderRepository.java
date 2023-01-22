package com.maktab.final_project_phaz2.date.repository;

import com.maktab.final_project_phaz2.date.model.OrderCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderCustomer,Long> {

}
