package com.maktab.final_project_phaz2.date.repository;
import com.maktab.final_project_phaz2.date.model.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface ExpertRepository extends JpaRepository<Expert,Long> {
    Optional<Expert> findByEmailAddress(String emailAddress);
    Optional<Expert> findById(Long id);

}


