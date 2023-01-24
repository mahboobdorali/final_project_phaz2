package com.maktab.final_project_phaz2.service;
import com.maktab.final_project_phaz2.date.model.MainTask;
import com.maktab.final_project_phaz2.date.repository.ServiceRepository;

import com.maktab.final_project_phaz2.exception.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


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

    public List<MainTask> getAllService() {
        return serviceRepository.findAll();
    }

    public MainTask getServiceByName(String nameService) throws NoResultException {
        return serviceRepository.findByName(nameService).orElseThrow(() -> new NoResultException("this Service is duplicate ):"));

    }

}
