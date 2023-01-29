package com.maktab.final_project_phaz2.service;

import com.maktab.final_project_phaz2.Util.DateUtil;
import com.maktab.final_project_phaz2.date.model.*;
import com.maktab.final_project_phaz2.date.model.enumuration.CurrentSituation;
import com.maktab.final_project_phaz2.date.repository.CustomerRepository;
import com.maktab.final_project_phaz2.date.repository.OfferRepository;
import com.maktab.final_project_phaz2.exception.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final OrderService orderService;
    private final OfferRepository offerRepository;
    private final OfferService offerService;
    private final ServiceUnderService underService;
    private final MainTaskService mainTaskService;

    public void registerCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public void deleteCustomer(Customer customer) {
        customerRepository.delete(customer);
    }

    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer logIn(String emailAddress, String password) throws NoResultException {
        Customer customer = customerRepository.findByEmailAddress(emailAddress).
                orElseThrow(() -> new NoResultException(" this customer dose not exist"));
        if (!(customer.getPassword().equals(password)))
            throw new NoResultException("is not exist password");
        return customer;
    }

    public Customer findCustomerByEmail(String emailAddress) throws NoResultException {
        return customerRepository.findByEmailAddress(emailAddress).
                orElseThrow(() -> new NoResultException("this customer dose not exist"));
    }

    public void changePassword(String emailAddress, String oldPassword, String newPassword) throws NoResultException {
        Customer customer = customerRepository.findByEmailAddress(emailAddress).
                orElseThrow(() -> new NoResultException(" this customer dose not exist"));
        if (!(customer.getPassword().equals(oldPassword)))
            throw new NoResultException("is not exist password");
        customer.setPassword(newPassword);
        customerRepository.save(customer);
    }

    public UnderService showUnderByName(String nameOfUnder) throws NoResultException {
        return underService.findUnderServiceByName(nameOfUnder);
    }

    public MainTask showAllServiceByName(String nameOfService) throws NoResultException {
        return mainTaskService.findServiceByName(nameOfService);
    }

    public OrderCustomer Order(OrderCustomer ordersCustomer, Long idOfChoiceUnderService) throws NoResultException {
        UnderService serviceById = underService.findUnderServiceById(idOfChoiceUnderService);
        if (ordersCustomer.getProposedPrice() < serviceById.getBasePrice())
            throw new NoResultException("the entered price is lower than the allowed limit!!");
        if (DateUtil.isNotDateValid(ordersCustomer.getDateAndTimeOfWork()))
            throw new NoResultException("the entered date is lower than the allowed limit!!");
        ordersCustomer.setCurrentSituation(CurrentSituation.WAITING_FOR_EXPERT_ADVICE);
        orderService.saveAllOrder(ordersCustomer);
        return ordersCustomer;
    }

    public List<Offer> sortByPrice(OrderCustomer orderCustomer) {
        return offerRepository.findAllByOrdersCustomer(orderCustomer, Sort.by(Sort.Direction.DESC, "priceOffer"));
    }


    public void selectExpertByCustomer(Offer offer) {
        offer.getOrdersCustomer().
                setCurrentSituation(CurrentSituation.WAITING_FOR_SPECIALIST_SELECTION_TO_COME);
        offerService.updateOffer(offer);
    }

    public void changeSituationByCustomer(Long id) throws NoResultException {
        Offer offerId = offerService.findById(id);
        if (!(offerId.getOrdersCustomer().getCurrentSituation().equals(CurrentSituation.WAITING_FOR_SPECIALIST_SELECTION_TO_COME)))
            throw new NoResultException("please first select your expert");
        offerId.getOrdersCustomer().setCurrentSituation(CurrentSituation.STARTED);
        offerService.updateOffer(offerId);
    }

    public void changeSituationToFinish(Long id) throws NoResultException {
        Offer offerById = offerService.findById(id);
        offerById.getOrdersCustomer().setCurrentSituation(CurrentSituation.DONE);
        offerService.updateOffer(offerById);
    }
}
