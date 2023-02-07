package com.maktab.final_project_phaz2.service;

import com.maktab.final_project_phaz2.date.model.MainTask;
import com.maktab.final_project_phaz2.date.model.UnderService;
import com.maktab.final_project_phaz2.date.repository.UnderServiceRepository;

import com.maktab.final_project_phaz2.exception.DuplicateEntryException;

import com.maktab.final_project_phaz2.exception.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceUnderService {
    private final UnderServiceRepository underServiceRepository;
    private final MainTaskService mainTaskService;

    public void saveAllUnderService(UnderService underService) {

        underServiceRepository.save(underService);
    }

    public void deleteAllUnderService(UnderService underService) {

        underServiceRepository.delete(underService);
    }

    public List<UnderService> getAllUnderService() {

        return underServiceRepository.findAll();
    }

    public UnderService addUnderServiceByAdmin(UnderService underService, Long serviceId) {
        Optional<UnderService> byNameSubService = underServiceRepository.findByNameSubService(underService.getNameSubService());
        MainTask serviceById = mainTaskService.findServiceById(serviceId);
        if (byNameSubService.isPresent())
            throw new DuplicateEntryException("this UnderService already exist");
        underService.setMainTask(serviceById);
        return underServiceRepository.save(underService);
    }

    public UnderService findUnderServiceByName(String underServiceName) {
        return underServiceRepository.findByNameSubService(underServiceName).orElseThrow(() -> new NoResultException("this underService is not exist"));
    }

    public UnderService findUnderServiceById(Long id) {
        return underServiceRepository.findById(id).orElseThrow(() -> new NoResultException("this underService is not exist"));
    }

    public List<UnderService> showAllUnderServiceByServiceByCustomer(String name) {
        mainTaskService.findServiceByName(name);
        return underServiceRepository.findUnderService(name);
    }
}
