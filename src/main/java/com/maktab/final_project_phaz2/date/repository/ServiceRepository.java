package com.maktab.final_project_phaz2.date.repository;
import com.maktab.final_project_phaz2.date.model.MainTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<MainTask,Long> {
    Optional<MainTask> findByName(String name);
}
