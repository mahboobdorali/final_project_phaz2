package com.maktab.final_project_phaz2.date.repository;

import com.maktab.final_project_phaz2.date.model.Expert;
import com.maktab.final_project_phaz2.date.model.enumuration.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpertRepository extends JpaRepository<Expert, Long>, JpaSpecificationExecutor<Expert> {
    Optional<Expert> findByEmailAddress(String emailAddress);

    Optional<Expert> findById(Long idExpert);

    @Query("select e from Expert e where e.approvalStatus=:approvalStatus")
    List<Expert> findAllByNewStatus(ApprovalStatus approvalStatus);

}


