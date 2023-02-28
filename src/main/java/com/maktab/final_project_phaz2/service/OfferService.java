package com.maktab.final_project_phaz2.service;

import com.maktab.final_project_phaz2.date.model.Offer;
import com.maktab.final_project_phaz2.date.repository.OfferRepository;
import com.maktab.final_project_phaz2.exception.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;


    public void saveAllOffer(Offer offer) {

        offerRepository.save(offer);
    }

    public void updateOffer(Offer offer) {
        offerRepository.save(offer);
    }

    public Offer findById(Long id) {

        return offerRepository.findById(id).orElseThrow(() -> new NoResultException("this offer is not exist!!"));
    }

    public List<Offer> showAllOfferByExpert(String emailAddress) {
        return offerRepository.listOfferByExpert(emailAddress);
    }

}
