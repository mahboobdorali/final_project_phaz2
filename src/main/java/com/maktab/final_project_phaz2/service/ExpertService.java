package com.maktab.final_project_phaz2.service;

import com.maktab.final_project_phaz2.Util.DateUtil;
import com.maktab.final_project_phaz2.date.model.*;
import com.maktab.final_project_phaz2.date.model.enumuration.CurrentSituation;
import com.maktab.final_project_phaz2.date.repository.ExpertRepository;
import com.maktab.final_project_phaz2.exception.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpertService {
    private final ExpertRepository expertRepository;
    private final OfferService offerService;
    private final OrderService orderService;
    private final ServiceUnderService underService;

    public void registerExpert(Expert expert) {
        expertRepository.save(expert);
    }

    public void deleteExpert(Expert expert) {
        Expert expertByEmail = findExpertByEmail(expert.getEmailAddress());
        expertRepository.delete(expertByEmail);
    }

    public Expert updateExpert(Expert expert) {

        return expertRepository.save(expert);
    }

    public List<Expert> getAllExpert() {

        return expertRepository.findAll();
    }

    public Expert logInExpert(String emailAddress, String password) {
        Expert expert = expertRepository.findByEmailAddress(emailAddress).
                orElseThrow(() -> new NoResultException(" this customer dose not exist"));
        if (!(expert.getPassword().equals(password)))
            throw new NoResultException("is not exist password");
        return expert;
    }

    public void changePassword(String emailAddress, String oldPassword, String newPassword) {
        Expert expert = expertRepository.findByEmailAddress(emailAddress).
                orElseThrow(() -> new NoResultException(" this customer dose not exist"));
        if (!(expert.getPassword().equals(oldPassword)))
            throw new NoResultException("is not exist password");
        expert.setPassword(newPassword);
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
    public Offer OfferAnSubmit(Offer offer, Long idUnder, Long idOrder) {
        UnderService underServiceById = underService.findUnderServiceById(idUnder);
        if (offer.getPriceOffer() < underServiceById.getBasePrice())
            throw new NoResultException("your price  is not available");
        if (DateUtil.isNotDateValid(offer.getTimeProposeToStartWork())) {
            throw new NoResultException("your date is not available");
        }
        OrderCustomer orderById = orderService.findOrderById(idOrder);
        checkSituation(orderById.getCurrentSituation());
        orderById.setCurrentSituation(CurrentSituation.WAITING_FOR_SPECIALIST_SELECTION);
        offer.setOrdersCustomer(orderById);
        offerService.saveAllOffer(offer);
        return offer;
    }


    public void checkSituation(CurrentSituation currentSituation) {
        List<CurrentSituation> currentSituationList = List.of(CurrentSituation.DONE,
                CurrentSituation.PAID,
                CurrentSituation.WAITING_FOR_SPECIALIST_SELECTION_TO_COME,
                CurrentSituation.STARTED);
        if (currentSituationList.contains(currentSituation))
            throw new NoResultException("your state is not safe");
    }

    @Transactional
    public void setExpertToOffer(Long idOffer, Long idExpert) {
        Offer offer = offerService.findById(idOffer);
        Expert expertById = findExpertById(idExpert);
        offer.setExpert(expertById);
    }

    public Expert saveImage(String emailExpert) {
        Expert expertByEmail = findExpertByEmail(emailExpert);
        File file = new File("D:\\java\\final-project\\final_project_phaz2\\src\\main\\java\\com\\maktab\\final_project_phaz2\\date\\man22.jpg");
        byte[] file1 = new byte[(int) file.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(file1);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        expertByEmail.setImage(file1);
        registerExpert(expertByEmail);
        return expertByEmail;
    }

    public void getImage(String expertEmail) {
        Expert expertByEmail = findExpertByEmail(expertEmail);
        byte[] image = expertByEmail.getImage();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("D:\\java\\final-project\\final_project_phaz2\\src\\main\\java\\com\\maktab\\final_project_phaz2\\date\\image.jpg");
            fileOutputStream.write(image);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

