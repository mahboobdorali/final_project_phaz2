package com.maktab.final_project_phaz2.service;

import com.maktab.final_project_phaz2.Util.DateUtil;
import com.maktab.final_project_phaz2.date.model.*;
import com.maktab.final_project_phaz2.date.model.enumuration.CurrentSituation;
import com.maktab.final_project_phaz2.date.repository.ExpertRepository;

import com.maktab.final_project_phaz2.exception.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpertService {
    private final ExpertRepository expertRepository;
    private final UnderService underService;
    private final OfferService offerService;


    public void registerExpert(Expert expert) {
        expertRepository.save(expert);
    }

    public void deleteExpert(Expert expert) {
        expertRepository.delete(expert);
    }
    public void updateExpert(Expert expert) {

         expertRepository.save(expert);
    }

    public List<Expert> getAllExpert() {

        return expertRepository.findAll();
    }

    public Expert logInExpert(String emailAddress, String password) throws NoResultException {
        Expert expert = expertRepository.findByEmailAddress(emailAddress).
                orElseThrow(() -> new NoResultException(" this customer dose not exist"));
        if (!(expert.getPassword().equals(password)))
            throw new NoResultException("is not exist password");
        return expert;
    }

    public void changePassword(String emailAddress, String oldPassword, String newPassword) throws NoResultException {
        Expert expert = expertRepository.findByEmailAddress(emailAddress).
                orElseThrow(() -> new NoResultException(" this customer dose not exist"));
        if (!(expert.getPassword().equals(oldPassword)))
            throw new NoResultException("is not exist password");
        expert.setPassword(newPassword);
        expertRepository.save(expert);
    }

    public Expert findExpertByEmail(String emailAddress) throws NoResultException {
        return expertRepository.findByEmailAddress(emailAddress).
                orElseThrow(() -> new NoResultException(" this expert dose not exist"));
    }

    public void OfferAnSubmit(Offer offer) throws NoResultException {
        if (offer.getPriceOffer() < underService.getBasePrice() || (!(DateUtil.isDateValid(offer.getTimeProposeToStartWork()))))
            throw new NoResultException("your price or date is not available");
        if (!(offer.getOrdersCustomer().getCurrentSituation().equals(CurrentSituation.WAITING_FOR_EXPERT_ADVICE))
                || (!(offer.getOrdersCustomer().getCurrentSituation().equals(CurrentSituation.WAITING_FOR_SPECIALIST_SELECTION))))
            throw new NoResultException("your state is not safe");
        offerService.saveAllOffer(offer);
        offer.getOrdersCustomer().setCurrentSituation(CurrentSituation.WAITING_FOR_SPECIALIST_SELECTION);
    }

}

