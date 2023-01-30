package com.maktab.final_project_phaz2.service;

import com.maktab.final_project_phaz2.Util.DateUtil;
import com.maktab.final_project_phaz2.date.model.*;
import com.maktab.final_project_phaz2.date.model.enumuration.CurrentSituation;
import com.maktab.final_project_phaz2.date.repository.CustomerRepository;
import com.maktab.final_project_phaz2.date.repository.OfferRepository;
import com.maktab.final_project_phaz2.exception.InputInvalidException;
import com.maktab.final_project_phaz2.exception.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final OrderService orderService;
    private final OfferRepository offerRepository;
    private final OfferService offerService;
    private final ServiceUnderService underService;
    private final MainTaskService mainTaskService;

    private final ExpertService expertService;

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

    public Customer findCustomerById(Long id) throws NoResultException {
        return customerRepository.findById(id).
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

    public List<Offer> sortByPrice(Long idOrderCustomer) throws NoResultException {
        OrderCustomer orderCustomer = orderService.findOrderById(idOrderCustomer);
        List<Offer> priceOffer = offerRepository.findAllByOrdersCustomer(orderCustomer, Sort.by(Sort.Direction.ASC, "priceOffer"));
        return offerRepository.saveAll(priceOffer);
    }


    public Offer selectOfferByCustomer(Long idForChoiceOffer) throws NoResultException {
        Offer offerServiceById = offerService.findById(idForChoiceOffer);
        offerServiceById.getOrdersCustomer().
                setCurrentSituation(CurrentSituation.WAITING_FOR_SPECIALIST_SELECTION_TO_COME);
        return offerService.updateOffer(offerServiceById);
    }

    @Transactional
    public Offer changeSituationByCustomer(Long idForChoiceOffer, Long idOrder) throws NoResultException {
        Offer offerId = offerService.findById(idForChoiceOffer);
        OrderCustomer orderById = orderService.findOrderById(idOrder);
        if (offerId.getTimeProposeToStartWork().before(orderById.getDateAndTimeOfWork()))
            throw new NoResultException("time has not come to do the work!!");
        offerId.getOrdersCustomer().setCurrentSituation(CurrentSituation.STARTED);
        return offerService.updateOffer(offerId);
    }

    public Expert paidForExpert(Long idChoiceOffer, String emailCustomer, String emailAddress) throws NoResultException, InputInvalidException {
        Offer offerById = offerService.findById(idChoiceOffer);
        Customer customerByEmail = findCustomerByEmail(emailCustomer);
        Expert expertByEmail = expertService.findExpertByEmail(emailAddress);
        if (offerById.getPriceOffer() > customerByEmail.getAmount())
            throw new RuntimeException("price offer is more than stock!!");
        customerByEmail.setAmount(customerByEmail.getAmount() - offerById.getPriceOffer());
        expertByEmail.setAmount(expertByEmail.getAmount() + offerById.getPriceOffer());
        offerById.getOrdersCustomer().setCurrentSituation(CurrentSituation.PAID);
        offerService.saveAllOffer(offerById);
        registerCustomer(customerByEmail);
        return expertService.updateExpert(expertByEmail);
    }

    public Offer changeSituationForFinish(Long id) throws NoResultException {
        Offer offer = offerService.findById(id);
        if (!offer.getOrdersCustomer().getCurrentSituation().equals(CurrentSituation.PAID))
            throw new NoResultException("please pay the expert fee first!!");
        offer.getOrdersCustomer().setCurrentSituation(CurrentSituation.DONE);
        return offerService.updateOffer(offer);

    }
}
