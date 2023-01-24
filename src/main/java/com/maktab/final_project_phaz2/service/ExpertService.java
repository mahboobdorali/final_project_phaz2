package com.maktab.final_project_phaz2.service;
import com.maktab.final_project_phaz2.date.model.Expert;
import com.maktab.final_project_phaz2.date.repository.ExpertRepository;

import com.maktab.final_project_phaz2.exception.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpertService {
    private final ExpertRepository expertRepository;

    public void registerExpert(Expert expert) {
        expertRepository.save(expert);
    }

    public void deleteExpert(Expert expert) {
        expertRepository.delete(expert);
    }

    public List<Expert> getAllExpert() {

        return expertRepository.findAll();
    }

    public Expert logInExpert(String emailAddress, String password) throws NoResultException {
        Expert expert = expertRepository.findByEmailAddress(emailAddress).
                orElseThrow(() -> new NoResultException(" this customer dose not exist"));
        if (expert.getPassword().equals(password))
            return expert;
        throw new NoResultException("is not exist password");
    }

    public void changePassword(String emailAddress, String oldPassword, String newPassword) throws NoResultException {
        Expert expert = expertRepository.findByEmailAddress(emailAddress).
                orElseThrow(() -> new NoResultException(" this customer dose not exist"));
        if (expert.getPassword().equals(oldPassword))
            expert.setPassword(newPassword);
        expertRepository.save(expert);
        throw new NoResultException("is not exist password");
    }

    public Expert findExpertByEmail(String emailAddress) throws NoResultException {
        return expertRepository.findByEmailAddress(emailAddress).
                orElseThrow(() -> new NoResultException(" this expert dose not exist"));
    }
    public Expert updateExpert(Expert expert){
        return expertRepository.save(expert);
    }
}
