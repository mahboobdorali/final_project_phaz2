package com.maktab.final_project_phaz2.service;

import com.maktab.final_project_phaz2.date.model.Expert;
import com.maktab.final_project_phaz2.date.model.MainTask;
import com.maktab.final_project_phaz2.date.repository.ServiceRepository;

import com.maktab.final_project_phaz2.exception.DuplicateEntryException;

import com.maktab.final_project_phaz2.exception.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MainTaskService {
    private final ServiceRepository serviceRepository;

    public void saveAllService(MainTask mainTask) {
        serviceRepository.save(mainTask);
    }

    public void deleteAllService(MainTask mainTask) {
        serviceRepository.delete(mainTask);
    }

    public MainTask editService(MainTask mainTask) {

        return serviceRepository.save(mainTask);
    }

    public List<MainTask> getAllService() {
        return serviceRepository.findAll();
    }

    public MainTask addServiceByAdmin(MainTask mainTask) {
        Optional<MainTask> byName = serviceRepository.findByName(mainTask.getName());
        if (byName.isPresent())
            throw new DuplicateEntryException("this service already exist");
        return serviceRepository.save(mainTask);
    }

    public MainTask findServiceByName(String serviceName) {
        return serviceRepository.findByName(serviceName).orElseThrow(() -> new NoResultException("this service is not exist"));
    }

    public MainTask findServiceById(Long id) {
        return serviceRepository.findById(id).orElseThrow(() -> new NoResultException("this service is not exist"));
    }
}
