package com.maktab.final_project_phaz2.service;

import com.maktab.final_project_phaz2.date.model.*;
import com.maktab.final_project_phaz2.date.model.enumuration.ApprovalStatus;
import com.maktab.final_project_phaz2.date.model.enumuration.Role;
import com.maktab.final_project_phaz2.date.repository.AdminRepository;
import com.maktab.final_project_phaz2.exception.DuplicateEntryException;
import com.maktab.final_project_phaz2.exception.RequestIsNotValidException;
import com.maktab.final_project_phaz2.exception.SourceUsageRestrictionsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final ServiceUnderService serviceUnderService;
    private final ExpertService expertService;

    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;

    public void registerAdmin(Admin admin) {
        Optional<Admin> byEmailAddress = adminRepository.findByEmailAddress(admin.getEmailAddress());
        if (byEmailAddress.isPresent())
            throw new DuplicateEntryException("you have already registered once in the system");
        admin.setRole(Role.ROLE_ADMIN);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminRepository.save(admin);
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
        if (expertById.getApprovalStatus().equals(ApprovalStatus.NEW))
            throw new SourceUsageRestrictionsException("you have not verified your email yet");
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
}

