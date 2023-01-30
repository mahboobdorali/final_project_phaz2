package com.maktab.final_project_phaz2.date.repository;
import com.maktab.final_project_phaz2.date.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmailAddress(String emailAddress);
    Optional<Customer> findById(Long id);
}
