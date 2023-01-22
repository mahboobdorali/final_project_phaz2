package com.maktab.final_project_phaz2.date.repository;

import com.maktab.final_project_phaz2.date.model.UnderService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnderServiceRepository extends JpaRepository<UnderService,Long> {
    UnderService findUnderServiceByNameSubService(String nameUnderService);
}
