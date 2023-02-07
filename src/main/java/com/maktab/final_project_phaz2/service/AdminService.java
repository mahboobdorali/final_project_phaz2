package com.maktab.final_project_phaz2.service;

import com.maktab.final_project_phaz2.date.model.Expert;
import com.maktab.final_project_phaz2.date.model.MainTask;
import com.maktab.final_project_phaz2.date.model.UnderService;
import com.maktab.final_project_phaz2.date.model.enumuration.ApprovalStatus;
import com.maktab.final_project_phaz2.exception.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final ServiceUnderService serviceUnderService;
    private final MainTaskService mainTaskService;
    private final ExpertService expertService;


    public List<MainTask> showAllOfMainServiceByAdmin() {

        return mainTaskService.getAllService();
    }

    public List<UnderService> showAllOfUnderServiceByAdmin() {

       return serviceUnderService.getAllUnderService();
    }

    public Expert convertStatus(String emailAddress) {
        Expert expert1 = expertService.findExpertByEmail(emailAddress);
        if (expert1.getApprovalStatus().equals(ApprovalStatus.ACCEPTED)) {
            throw new NoResultException("you have already been approved by the administrator");
        } else
            expert1.setApprovalStatus(ApprovalStatus.ACCEPTED);
        expertService.updateExpert(expert1);
        return expert1;
    }

    @Transactional
    public Expert addExpertToUnderService(Long idUnderService, Long idExpert) {
        UnderService underService = serviceUnderService.findUnderServiceById(idUnderService);
        Expert expertById = expertService.findExpertById(idExpert);
        expertById.getUnderServiceList().add(underService);
        return expertService.updateExpert(expertById);
    }

    @Transactional
    public Expert deleteExpertFromUnderService(Long idUnderService, Long idExpert) {
        UnderService underService = serviceUnderService.findUnderServiceById(idUnderService);
        Expert expertById = expertService.findExpertById(idExpert);
        expertById.getUnderServiceList().remove(underService);
        return expertService.updateExpert(expertById);
    }

    public UnderService changeDescription(Long idUnderService, String newDescription) {
        UnderService underServiceById = serviceUnderService.findUnderServiceById(idUnderService);
        underServiceById.setBriefExplanation(newDescription);
        serviceUnderService.saveAllUnderService(underServiceById);
        return underServiceById;
    }

    public UnderService changePriceUnderService(double newPrice, String nameOfUnderService) {
        UnderService underServiceByName = serviceUnderService.findUnderServiceByName(nameOfUnderService);
        underServiceByName.setBasePrice(newPrice);
        serviceUnderService.saveAllUnderService(underServiceByName);
        return underServiceByName;
    }
}

