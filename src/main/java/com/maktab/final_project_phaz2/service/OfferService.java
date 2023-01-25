package com.maktab.final_project_phaz2.service;

import com.maktab.final_project_phaz2.date.model.Customer;
import com.maktab.final_project_phaz2.date.model.Offer;
import com.maktab.final_project_phaz2.date.model.OrderCustomer;
import com.maktab.final_project_phaz2.date.repository.OfferRepository;
import jakarta.persistence.NoResultException;
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

    public void deleteAllOffer(Offer offer) {

        offerRepository.delete(offer);
    }

    public List<Offer> getAllOffer() {

        return offerRepository.findAll();
    }

    public Offer updateOffer(Offer offer) {
        return offerRepository.save(offer);
    }

    public Offer findById(Long id) {

        return offerRepository.findById(id).orElseThrow(() -> new NoResultException("this offer is not exist!!"));
    }
}
