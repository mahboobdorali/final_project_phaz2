package com.maktab.final_project_phaz2.service;

import com.maktab.final_project_phaz2.date.model.Expert;
import com.maktab.final_project_phaz2.date.model.UnderService;
import com.maktab.final_project_phaz2.date.model.enumuration.ApprovalStatus;
import com.maktab.final_project_phaz2.exception.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final ServiceUnderService serviceUnderService;
    private final MainTaskService mainTaskService;
    private final ExpertService expertService;


    public void showAllOfMainServiceByAdmin() {

        mainTaskService.getAllService();
    }

    public void showAllOfUnderServiceByAdmin() {

        serviceUnderService.getAllUnderService();
    }

    public Expert convertStatus(String emailAddress) throws NoResultException {
        Expert expert1 = expertService.findExpertByEmail(emailAddress);
        if (expert1.getApprovalStatus().equals(ApprovalStatus.ACCEPTED)) {
            throw new NoResultException("you have already been approved by the administrator");
        } else
            expert1.setApprovalStatus(ApprovalStatus.ACCEPTED);
        expertService.updateExpert(expert1);
        return expert1;
    }

    public Expert addExpertToUnderService(Long idUnderService, Long idExpert) throws NoResultException {
        UnderService underService = serviceUnderService.findUnderServiceById(idUnderService);
        Expert expertById = expertService.findExpertById(idExpert);
        expertById.getUnderServiceList().add(underService);
        return expertService.updateExpert(expertById);
    }

    @Transactional
    public Expert deleteExpertFromUnderService(Long idUnderService, Long idExpert) throws NoResultException {
        UnderService underService = serviceUnderService.findUnderServiceById(idUnderService);
        Expert expertById = expertService.findExpertById(idExpert);
        expertById.getUnderServiceList().remove(underService);
        return expertService.updateExpert(expertById);
    }

    public UnderService changeDescription(String newDescription, UnderService underService) throws NoResultException {
        UnderService underServiceByName = serviceUnderService.findUnderServiceByName(underService.getNameSubService());
        underServiceByName.setBriefExplanation(newDescription);
        serviceUnderService.saveAllUnderService(underServiceByName);
        return underServiceByName;
    }

    public UnderService changePriceUnderService(double newPrice, UnderService underService) throws NoResultException {
        UnderService underServiceByName = serviceUnderService.findUnderServiceByName(underService.getNameSubService());
        underServiceByName.setBasePrice(newPrice);
        serviceUnderService.saveAllUnderService(underServiceByName);
        return underServiceByName;
    }
}

