package com.maktab.final_project_phaz2.date.repository;

import com.maktab.final_project_phaz2.date.model.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String emailAddress);

    @Transactional
    @Modifying
    @Query("update Customer c set c.password=:password where c.emailAddress=:emailAddress")
    void updatePasswordByEmailAddress(@Param("password") String password,@Param("emailAddress") String emailAddress);
}
