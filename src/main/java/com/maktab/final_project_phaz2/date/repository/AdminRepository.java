package com.maktab.final_project_phaz2.date.repository;

import com.maktab.final_project_phaz2.date.model.Admin;
import com.maktab.final_project_phaz2.date.model.Expert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,Long>{
    Optional<Admin> findByEmailAddress(String emailAddress);
}
