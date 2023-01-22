package com.maktab.final_project_phaz2.date.repository;

import com.maktab.final_project_phaz2.date.model.Customer;
import com.maktab.final_project_phaz2.date.model.Expert;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ExpertRepository extends JpaRepository<Expert,Long> {
    Optional<Expert> findByEmail(String emailAddress);

    @Transactional
    @Modifying
    @Query("update Expert e set e.password=:password where e.emailAddress=:emailAddress")
    void updatePasswordByEmailAddress(@Param("password") String password, @Param("emailAddress") String emailAddress);

}


