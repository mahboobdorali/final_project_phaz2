package com.maktab.final_project_phaz2.date.repository;

import com.maktab.final_project_phaz2.date.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findById(Long id);

}
