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

    public void deleteOffer(Offer offer) {

        offerRepository.delete(offer);
    }

    public List<Offer> getAllOffer() {

        return offerRepository.findAll();
    }

    public Offer updateOffer(Offer offer) {
        return offerRepository.save(offer);
    }

    public Offer findById(Long id) throws NoResultException {

        return offerRepository.findById(id).orElseThrow(() -> new NoResultException("this offer is not exist!!"));
    }
}
