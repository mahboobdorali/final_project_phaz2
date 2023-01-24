package com.maktab.final_project_phaz2.service;

import com.maktab.final_project_phaz2.date.model.UnderService;
import com.maktab.final_project_phaz2.date.repository.UnderServiceRepository;

import com.maktab.final_project_phaz2.exception.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceUnderService {
    private final UnderServiceRepository underServiceRepository;

    public void saveAllUnderService(UnderService underService) {

        underServiceRepository.save(underService);
    }

    public void deleteAllUnderService(UnderService underService) {

        underServiceRepository.delete(underService);
    }

    public List<UnderService> getAllUnderService() {

        return underServiceRepository.findAll();
    }


    public UnderService underServiceByName(String nameSubService) throws NoResultException {
        return underServiceRepository.findByNameSubService(nameSubService).orElseThrow(() -> new NoResultException("this underService is duplicate ):"));
    }
}
