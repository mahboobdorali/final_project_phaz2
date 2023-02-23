package com.maktab.final_project_phaz2.service;

import com.maktab.final_project_phaz2.date.dto.SearchExpertDto;
import com.maktab.final_project_phaz2.date.model.*;
import com.maktab.final_project_phaz2.date.model.enumuration.ApprovalStatus;
import com.maktab.final_project_phaz2.date.model.enumuration.Role;
import com.maktab.final_project_phaz2.date.repository.AdminRepository;
import com.maktab.final_project_phaz2.date.repository.ExpertRepository;
import com.maktab.final_project_phaz2.exception.DuplicateEntryException;
import com.maktab.final_project_phaz2.exception.RequestIsNotValidException;
import com.maktab.final_project_phaz2.exception.SourceUsageRestrictionsException;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final ServiceUnderService serviceUnderService;
    private final MainTaskService mainTaskService;
    private final ExpertService expertService;
    private final ExpertRepository expertRepository;
    private final AdminRepository adminRepository;

    public void registerAdmin(Admin admin) {
        Optional<Admin> byEmailAddress = adminRepository.findByEmail(admin.getEmail());
        if (byEmailAddress.isPresent())
            throw new DuplicateEntryException("you have already registered once in the system");
        admin.setRole(Role.ADMIN);
        adminRepository.save(admin);
    }

    public List<MainTask> showAllOfMainServiceByAdmin() {

        return mainTaskService.getAllService();
    }

    public List<UnderService> showAllOfUnderServiceByAdmin() {

        return serviceUnderService.getAllUnderService();
    }

    public void convertStatus(String emailAddress) {
        Expert expert1 = expertService.findExpertByEmail(emailAddress);
        if (expert1.getApprovalStatus().equals(ApprovalStatus.ACCEPTED)) {
            throw new RequestIsNotValidException("you have already been approved by the administrator");
        } else
            expert1.setApprovalStatus(ApprovalStatus.ACCEPTED);
        expertService.updateExpert(expert1);
    }

    @Transactional
    public void addExpertToUnderService(Long idUnderService, Long idExpert) {
        UnderService underService = serviceUnderService.findUnderServiceById(idUnderService);
        Expert expertById = expertService.findExpertById(idExpert);
        if (expertById.getApprovalStatus().equals(ApprovalStatus.AWAITING_CONFIRMATION))
            throw new SourceUsageRestrictionsException("first admin should confirmed you!!");
        expertById.getUnderServiceList().add(underService);
        expertService.updateExpert(expertById);
    }

    @Transactional
    public void deleteExpertFromUnderService(Long idUnderService, Long idExpert) {
        UnderService underService = serviceUnderService.findUnderServiceById(idUnderService);
        Expert expertById = expertService.findExpertById(idExpert);
        expertById.getUnderServiceList().remove(underService);
        expertService.updateExpert(expertById);
    }

    public void changeDescription(Long idUnderService, String newDescription) {
        UnderService underServiceById = serviceUnderService.findUnderServiceById(idUnderService);
        underServiceById.setBriefExplanation(newDescription);
        serviceUnderService.saveAllUnderService(underServiceById);
    }

    public void changePriceUnderService(double newPrice, String nameOfUnderService) {
        UnderService underServiceByName = serviceUnderService.findUnderServiceByName(nameOfUnderService);
        underServiceByName.setBasePrice(newPrice);
        serviceUnderService.saveAllUnderService(underServiceByName);
    }

    public List<Expert> filterExpertByCondition(SearchExpertDto expert) {
        return expertRepository.findAll((Specification<Expert>) (root, query, criteriaBuilder) -> {
            Join<Expert, UnderService> expertUnderServiceJoin = root.join("underServiceList");
            List<Predicate> predicateList = new ArrayList<>();
            if (expert.getName() != null && expert.getName().length() != 0)
                predicateList.add(criteriaBuilder.equal(root.get("name"), expert.getName()));
            if (expert.getFamily() != null && expert.getFamily().length() != 0)
                predicateList.add(criteriaBuilder.equal(root.get("family"), expert.getFamily()));
            if (expert.getRole() != null)
                predicateList.add(criteriaBuilder.equal(root.get("role"), expert.getRole()));
            if (expert.getEmailAddress() != null && expert.getEmailAddress().length() != 0)
                predicateList.add(criteriaBuilder.equal(root.get("emailAddress"), expert.getEmailAddress()));
            if (expert.getNameSubService() != null && expert.getNameSubService().length() != 0)
                predicateList.add(criteriaBuilder.equal(expertUnderServiceJoin.get("nameSubService"),
                        expert.getNameSubService()));
            if (expert.getMinScore() == 0 && expert.getMaxScore() != 0)
                predicateList.add(criteriaBuilder.lt(root.get("averageScore"), expert.getMaxScore()));
            if (expert.getMinScore() != 0 && expert.getMaxScore() == 0)
                predicateList.add(criteriaBuilder.gt(root.get("averageScore"), expert.getMinScore()));
            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        });
    }
}

