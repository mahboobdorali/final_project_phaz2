package com.maktab.final_project_phaz2.service;

import com.maktab.final_project_phaz2.date.model.Opinion;
import com.maktab.final_project_phaz2.date.repository.OpinionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpinionService {
    private final OpinionRepository opinionRepository;

    public void registerOpinion(Opinion opinion) {
        opinionRepository.save(opinion);
    }

    public void deleteOpinion(Opinion opinion) {
        opinionRepository.delete(opinion);
    }

    public Opinion updateExpert(Opinion opinion) {
        return opinionRepository.save(opinion);
    }

    public List<Opinion> getAllOpinion() {
        return opinionRepository.findAll();
    }

    public List<Opinion> showOrdersToExpert(String emailAddress) {
        return opinionRepository.showAllScore(emailAddress);
    }
}
