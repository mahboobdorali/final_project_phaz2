package com.maktab.final_project_phaz2.service;

import com.maktab.final_project_phaz2.Util.DateUtil;
import com.maktab.final_project_phaz2.date.dto.SearchExpertDto;
import com.maktab.final_project_phaz2.date.model.*;
import com.maktab.final_project_phaz2.date.model.enumuration.ApprovalStatus;
import com.maktab.final_project_phaz2.date.model.enumuration.CurrentSituation;
import com.maktab.final_project_phaz2.date.model.enumuration.Role;
import com.maktab.final_project_phaz2.date.repository.ExpertRepository;
import com.maktab.final_project_phaz2.exception.DuplicateEntryException;
import com.maktab.final_project_phaz2.exception.InputInvalidException;
import com.maktab.final_project_phaz2.exception.NoResultException;
import com.maktab.final_project_phaz2.exception.RequestIsNotValidException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpertService {
    private final ExpertRepository expertRepository;
    private final OfferService offerService;
    private final OrderService orderService;
    private final ServiceUnderService underService;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    public void registerExpert(Expert expert, String siteURL) throws MessagingException {
        Optional<Expert> expertByEmailAddress = expertRepository.findByEmailAddress(expert.getEmailAddress());
        if (expertByEmailAddress.isPresent())
            throw new DuplicateEntryException("this expert is already registered in system");
        expert.setRole(Role.ROLE_EXPERT);
        expert.setApprovalStatus(ApprovalStatus.NEW);
        expert.setPassword(passwordEncoder.encode(expert.getPassword()));
        String randomCode = RandomString.make(64);
        expert.setVerificationCode(randomCode);
        expert.setEnabled(false);
        expertRepository.save(expert);
        sendVerificationEmail(expert, siteURL);
    }

    public void sendVerificationEmail(Expert expert, String siteURL)
            throws MessagingException {
        String toAddress = expert.getEmailAddress();
        String fromAddress = "mahboobdorali1378@gmail.com";
        String content = "Dear expert" +
                ",<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(fromAddress);
        helper.setTo(toAddress);
        String verifyURL = siteURL + "/expert/verify?code=" + expert.getVerificationCode();
        content = content.replace("[[URL]]", verifyURL);
        helper.setText(content, true);
        mailSender.send(message);

    }

    public boolean verify(String verificationCode) {
        Expert expert = expertRepository.findByVerificationCode(verificationCode);
        if (expert == null || expert.isEnabled()) {
            return false;
        } else {
            expert.setVerificationCode(null);
            expert.setEnabled(true);
            expert.setApprovalStatus(ApprovalStatus.AWAITING_CONFIRMATION);
            updateExpert(expert);
            return true;
        }
    }

    public List<Expert> findAllByNewStatus(ApprovalStatus status) {
        return expertRepository.findAllByNewStatus(status);
    }

    public void deleteExpert(Expert expert) {
        Expert expertByEmail = findExpertByEmail(expert.getEmailAddress());
        expertRepository.delete(expertByEmail);
    }

    public Expert updateExpert(Expert expert) {
        Expert expertByEmail = findExpertByEmail(expert.getEmailAddress());
        return expertRepository.save(expertByEmail);
    }

    public List<Expert> getAllExpert() {

        return expertRepository.findAll();
    }

    public Expert logInExpert(String emailAddress, String password) {
        Expert expert = expertRepository.findByEmailAddress(emailAddress).
                orElseThrow(() -> new NoResultException(" this customer dose not exist"));
        if (!(expert.getPassword().equals(password)))
            throw new InputInvalidException("is not exist password");
        return expert;
    }

    public void changePassword(String newPassword, String confirmPassword) {
        Expert expert = expertRepository.findByEmailAddress(((Person) SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal()).getEmailAddress()).
                orElseThrow(() -> new NoResultException(" this customer dose not exist"));
        if (!newPassword.equals(confirmPassword))
            throw new InputInvalidException("your password does not match!!");
        expert.setPassword(passwordEncoder.encode(newPassword));
        expertRepository.save(expert);
    }

    public Expert findExpertByEmail(String emailAddress) {
        return expertRepository.findByEmailAddress(emailAddress).
                orElseThrow(() -> new NoResultException("this expert dose not exist"));
    }

    public Expert findExpertById(Long id) {
        return expertRepository.findById(id).
                orElseThrow(() -> new NoResultException("this expert dose not exist"));
    }

    @Transactional
    public void checkConditionForOffer(Long idUnder, Offer offer) {
        UnderService underServiceById = underService.findUnderServiceById(idUnder);
        if (offer.getPriceOffer() < underServiceById.getBasePrice())
            throw new RequestIsNotValidException("your price  is not available");
        if (DateUtil.isNotDateValid(offer.getTimeProposeToStartWork())) {
            throw new RequestIsNotValidException("your date is not available");
        }
    }

    @Transactional
    public void OfferAnSubmit(Offer offer, Long idUnder, Long idOrder) {
        checkConditionForOffer(idUnder, offer);
        OrderCustomer orderById = orderService.findOrderById(idOrder);
        checkSituation(orderById.getCurrentSituation());
        orderById.setCurrentSituation(CurrentSituation.WAITING_FOR_SPECIALIST_SELECTION);
        offer.setOrderCustomer(orderById);
        offerService.saveAllOffer(offer);
    }


    public void checkSituation(CurrentSituation currentSituation) {
        List<CurrentSituation> currentSituationList = List.of(CurrentSituation.DONE,
                CurrentSituation.PAID,
                CurrentSituation.WAITING_FOR_SPECIALIST_SELECTION_TO_COME,
                CurrentSituation.STARTED);
        if (currentSituationList.contains(currentSituation))
            throw new RequestIsNotValidException("your state is not safe");
    }

    @Transactional
    public void setExpertToOffer(Long idOffer) {
        Offer offer = offerService.findById(idOffer);
        Expert expertById = findExpertById(((Person) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getId());
        offer.setExpert(expertById);
    }

    public Long showScoreWithoutDescription() {
        Expert expertByEmail = findExpertByEmail(((Person) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getEmailAddress());
        return expertByEmail.getAverageScore();
    }

    public double showAmountToExpert() {
        Expert expertByEmail = findExpertByEmail(((Person) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getEmailAddress());
        return expertByEmail.getAmount();
    }

    @Transactional
    public List<Expert> filterExpertByCondition(SearchExpertDto expert) {
        return expertRepository.findAll((Specification<Expert>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (expert.getName() != null && expert.getName().length() != 0)
                predicateList.add(criteriaBuilder.equal(root.get("name"), expert.getName()));
            if (expert.getFamily() != null && expert.getFamily().length() != 0)
                predicateList.add(criteriaBuilder.equal(root.get("family"), expert.getFamily()));
            if (expert.getRole() != null)
                predicateList.add(criteriaBuilder.equal(root.get("role"), expert.getRole()));
            if (expert.getEmailAddress() != null && expert.getEmailAddress().length() != 0)
                predicateList.add(criteriaBuilder.equal(root.get("emailAddress"), expert.getEmailAddress()));
            if (expert.getNameSubService() != null && expert.getNameSubService().length() != 0) {
                Join<Expert, UnderService> expertUnderServiceJoin = root.join("underServiceList");
                predicateList.add(criteriaBuilder.equal(expertUnderServiceJoin.get("nameSubService"),
                        expert.getNameSubService()));
            }
            if (expert.getMinScore() == 0 && expert.getMaxScore() != 0)
                predicateList.add(criteriaBuilder.lt(root.get("averageScore"), expert.getMaxScore()));
            if (expert.getMinScore() != 0 && expert.getMaxScore() == 0)
                predicateList.add(criteriaBuilder.gt(root.get("averageScore"), expert.getMinScore()));
            if (expert.getAfterTime() != null && expert.getBeforeTime() != null)
                predicateList.add(criteriaBuilder.between(root.get("dateAndTimeOfRegistration"), expert.getAfterTime(), expert.getBeforeTime()));

            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        });
    }

}

