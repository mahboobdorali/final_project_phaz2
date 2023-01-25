package com.maktab.final_project_phaz2.service;
import com.maktab.final_project_phaz2.date.model.Expert;
import com.maktab.final_project_phaz2.date.model.MainTask;
import com.maktab.final_project_phaz2.date.model.UnderService;
import com.maktab.final_project_phaz2.date.model.enumuration.ApprovalStatus;
import com.maktab.final_project_phaz2.exception.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final ServiceUnderService serviceUnderService;
    private final MainTaskService mainTaskService;
    private final ExpertService expertService;
  //  private final Expert expert;
    private final UnderService underService;

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

    public void addExpertToUnderService(String underServiceName,Expert expert) throws NoResultException {
        UnderService underService = serviceUnderService.underServiceByName(underServiceName);
        expert.getUnderServiceList().add(underService);
        expertService.updateExpert(expert);
    }

    public void deleteExpertFromUnderService(UnderService underService,Expert expert) {
        expert.getUnderServiceList().remove(underService);
        expertService.updateExpert(expert);
    }

    public void addServiceByAdmin(String mainTaskName) throws NoResultException {
        MainTask serviceByName = mainTaskService.getServiceByName(mainTaskName);
        mainTaskService.saveAllService(serviceByName);
    }

    public void addUnderServiceByAdmin(String underServiceName, String serviceName) throws NoResultException {
        mainTaskService.getServiceByName(serviceName);
        UnderService underService = serviceUnderService.underServiceByName(underServiceName);
        serviceUnderService.saveAllUnderService(underService);
    }

    public void updateUnderService(String newDescription, double newPrice, String nameOfUnderService) throws NoResultException {
        serviceUnderService.underServiceByName(nameOfUnderService);
        underService.setBriefExplanation(newDescription);
        underService.setBasePrice(newPrice);
        serviceUnderService.saveAllUnderService(underService);
    }
}

