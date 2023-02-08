package com.maktab.final_project_phaz2.service;

import com.maktab.final_project_phaz2.Util.DateUtil;
import com.maktab.final_project_phaz2.date.model.*;
import com.maktab.final_project_phaz2.date.model.enumuration.CurrentSituation;
import com.maktab.final_project_phaz2.date.model.enumuration.Role;
import com.maktab.final_project_phaz2.date.repository.CustomerRepository;
import com.maktab.final_project_phaz2.date.repository.OfferRepository;
import com.maktab.final_project_phaz2.exception.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final ExpertService expertService;

    public void registerCustomer(Customer customer) {
        customer.setRole(Role.CUSTOMER);
        customerRepository.save(customer);
    }

    public void deleteCustomer(Customer customer) {
        Customer customerByEmail = findCustomerByEmail(customer.getEmailAddress());
        customerRepository.delete(customerByEmail);
    }

    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer logIn(String emailAddress, String password) {
        Customer customer = customerRepository.findByEmailAddress(emailAddress).
                orElseThrow(() -> new NoResultException(" this customer dose not exist"));
        if (!(customer.getPassword().equals(password)))
            throw new NoResultException("is not exist password");
        return customer;
    }

    public Customer findCustomerByEmail(String emailAddress) {
        return customerRepository.findByEmailAddress(emailAddress).
                orElseThrow(() -> new NoResultException("this customer dose not exist"));
    }

    public Customer findCustomerById(Long id) {
        return customerRepository.findById(id).
                orElseThrow(() -> new NoResultException("this customer dose not exist"));
    }

    public void changePassword(String emailAddress, String oldPassword, String newPassword) {
        Customer customer = customerRepository.findByEmailAddress(emailAddress).
                orElseThrow(() -> new NoResultException(" this customer dose not exist"));
        if (!(customer.getPassword().equals(oldPassword)))
            throw new NoResultException("is not exist password");
        customer.setPassword(newPassword);
        customerRepository.save(customer);
    }

    public UnderService showUnderByName(String nameOfUnder) {
        return underService.findUnderServiceByName(nameOfUnder);
    }

    public MainTask showAllServiceByName(String nameOfService) {
        return mainTaskService.findServiceByName(nameOfService);
    }

    @Transactional
    public OrderCustomer Order(OrderCustomer ordersCustomer, Long idOfChoiceUnderService, Long idCustomer) {
        UnderService serviceById = underService.findUnderServiceById(idOfChoiceUnderService);
        Customer customerById = findCustomerById(idCustomer);
        if (ordersCustomer.getProposedPrice() < serviceById.getBasePrice())
            throw new NoResultException("the entered price is lower than the allowed limit!!");
        if (DateUtil.isNotDateValid(ordersCustomer.getDateAndTimeOfWork()))
            throw new NoResultException("the entered date is lower than the allowed limit!!");
        ordersCustomer.setCurrentSituation(CurrentSituation.WAITING_FOR_EXPERT_OFFER);
        ordersCustomer.setCustomer(customerById);
        ordersCustomer.setUnderService(serviceById);
        orderService.saveAllOrder(ordersCustomer);
        return ordersCustomer;
    }

    public List<Offer> sortByPrice(Long idOrderCustomer) {
        OrderCustomer orderCustomer = orderService.findOrderById(idOrderCustomer);
        return offerRepository.sortOfferByPriceOffer(orderCustomer);
    }

    public List<Offer> sortByScore(Long idOrderCustomer) {
        OrderCustomer orderCustomer = orderService.findOrderById(idOrderCustomer);
        return offerRepository.sortOfferByScore(orderCustomer);
    }


    public Offer selectOfferByCustomer(Long idForChoiceOffer, Long idOrder) {
        Offer offerServiceById = offerService.findById(idForChoiceOffer);
        OrderCustomer orderById = orderService.findOrderById(idOrder);
        orderById.setCurrentSituation(CurrentSituation.WAITING_FOR_SPECIALIST_SELECTION_TO_COME);
        return offerService.updateOffer(offerServiceById);
    }

    public void changeSituationByCustomer(Long idForChoiceOffer, Long idOrder) {
        Offer offerId = offerService.findById(idForChoiceOffer);
        OrderCustomer orderById = orderService.findOrderById(idOrder);
        if (offerId.getTimeProposeToStartWork().before(orderById.getDateAndTimeOfWork()))
            throw new NoResultException("time has not come to do the work!!");
        orderById.setCurrentSituation(CurrentSituation.STARTED);
        offerService.updateOffer(offerId);
    }

    /*public Offer changeSituationForFinish(Long id) {
        Offer offer = offerService.findById(id);
        if (.offer.getTimeProposeToStartWork())
            throw new NoResultException("you can change the status if the status is on started :)");
        offer.getOrdersCustomer().setCurrentSituation(CurrentSituation.DONE);
        return offerService.updateOffer(offer);

    }
*/

   /* @Transactional
    public void saveComments(Long idExpert, Integer score) {
        Expert expertByEmail = expertService.findExpertById(idExpert);
        if (score < 1 || score > 6)
            throw new NoResultException("the score entered must be a number between one and five(1-5");
        opinion.setScore(score);
        expertByEmail.getOpinionList().add(opinion);
        expertService.registerExpert(expertByEmail);
    }

    @Transactional
    public void saveIdea(String emailAddress, String comment) {
        Expert expertByEmail = expertService.findExpertByEmail(emailAddress);
        opinion.setComment(comment);
        expertByEmail.getOpinionList().add(opinion);
        expertService.registerExpert(expertByEmail);
    }*/


    public Expert paidForExpert(Long idChoiceOffer, String emailCustomer, String emailAddress) {
        Offer offerById = offerService.findById(idChoiceOffer);
        Customer customerByEmail = findCustomerByEmail(emailCustomer);
        Expert expertByEmail = expertService.findExpertByEmail(emailAddress);
        if (offerById.getPriceOffer() > customerByEmail.getAmount())
            throw new RuntimeException("price offer is more than stock!!");
        customerByEmail.setAmount(customerByEmail.getAmount() - offerById.getPriceOffer());
        expertByEmail.setAmount(expertByEmail.getAmount() + offerById.getPriceOffer());
        offerById.getOrderCustomer().setCurrentSituation(CurrentSituation.PAID);
        offerService.saveAllOffer(offerById);
        registerCustomer(customerByEmail);
        return expertService.updateExpert(expertByEmail);
    }


}
