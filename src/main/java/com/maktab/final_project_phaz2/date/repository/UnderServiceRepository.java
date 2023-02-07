package com.maktab.final_project_phaz2.date.repository;

import com.maktab.final_project_phaz2.date.model.MainTask;
import com.maktab.final_project_phaz2.date.model.UnderService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnderServiceRepository extends JpaRepository<UnderService, Long> {
    Optional<UnderService> findByNameSubService(String nameUnderService);

    Optional<UnderService> findById(Long id);

    @Query("select u from UnderService u where u.MainTask.name=:name")
    List<UnderService> findUnderService(String name);
}
