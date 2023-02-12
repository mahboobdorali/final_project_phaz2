package com.maktab.final_project_phaz2.service;

import com.maktab.final_project_phaz2.Util.DateUtil;
import com.maktab.final_project_phaz2.date.model.*;
import com.maktab.final_project_phaz2.date.model.enumuration.ApprovalStatus;
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
    private final OpinionService opinionService;
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
    public void Order(OrderCustomer ordersCustomer, Long idOfChoiceUnderService, Long idCustomer) {
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
    }

    public List<Offer> sortByPrice(Long idOrderCustomer) {
        OrderCustomer orderCustomer = orderService.findOrderById(idOrderCustomer);
        return offerRepository.sortOfferByPriceOffer(orderCustomer);
    }

    public List<Offer> sortByScore(Long idOrderCustomer) {
        OrderCustomer orderCustomer = orderService.findOrderById(idOrderCustomer);
        return offerRepository.sortOfferByScore(orderCustomer);
    }


    public void selectOfferByCustomer(Long idForChoiceOffer, Long idOrder) {
        Offer offerServiceById = offerService.findById(idForChoiceOffer);
        OrderCustomer orderById = orderService.findOrderById(idOrder);
        orderById.setCurrentSituation(CurrentSituation.WAITING_FOR_SPECIALIST_SELECTION_TO_COME);
        orderById.setExpert(offerServiceById.getExpert());
        offerService.updateOffer(offerServiceById);
    }

    public void changeSituationByCustomer(Long idForChoiceOffer, Long idOrder) {
        Offer offerId = offerService.findById(idForChoiceOffer);
        OrderCustomer orderById = orderService.findOrderById(idOrder);
        if (offerId.getTimeProposeToStartWork().before(orderById.getDateAndTimeOfWork()))
            throw new NoResultException("time has not come to do the work!!");
        orderById.setCurrentSituation(CurrentSituation.STARTED);
        offerService.updateOffer(offerId);
    }

    public void changeSituationForFinish(Long id) {
        Offer offer = offerService.findById(id);
        Long differentBetweenTwoDate = DateUtil.differentBetweenTwoDate
                (offer.getTimeProposeToStartWork(), offer.getOrderCustomer().getWorkDone());
        if (!differentBetweenTwoDate.equals(offer.getDurationOfWork())) {
            long number = differentBetweenTwoDate - offer.getDurationOfWork();
            long numbers = offer.getExpert().getAverageScore() - number;
            if (numbers < 0)
                offer.getExpert().setApprovalStatus(ApprovalStatus.NEW);
            offer.getExpert().setAverageScore(numbers);
        }
        offer.getOrderCustomer().setCurrentSituation(CurrentSituation.DONE);
        offerService.updateOffer(offer);
    }

    public void paymentFromCredit(Long idChoiceOffer, String emailCustomer) {
        Offer offerById = offerService.findById(idChoiceOffer);
        Customer customerByEmail = findCustomerByEmail(emailCustomer);
        if (offerById.getPriceOffer() > customerByEmail.getAmount())
            throw new RuntimeException("price offer is more than stock!!");
        customerByEmail.setAmount(customerByEmail.getAmount() - offerById.getPriceOffer());
        double percent = (offerById.getPriceOffer() / 100) * 70;
        offerById.getExpert().setAmount(percent);
        offerById.getOrderCustomer().setCurrentSituation(CurrentSituation.PAID);
        offerService.saveAllOffer(offerById);
        registerCustomer(customerByEmail);
    }


    public void saveComments(Long idOffer, Opinion opinion) {
        Offer byId = offerService.findById(idOffer);
        if (opinion.getScore() < 1 || opinion.getScore() > 6)
            throw new NoResultException("the score entered must be a number between one and five(1-5");
        opinion.setExpert(byId.getExpert());
        opinionService.registerOpinion(opinion);
        byId.getExpert().setAverageScore(opinion.getScore());
        expertService.updateExpert(byId.getExpert());
    }

    /*@Transactional
    public void saveIdea(String emailAddress, String comment) {
        Expert expertByEmail = expertService.findExpertByEmail(emailAddress);
        opinion.setComment(comment);
        expertByEmail.getOpinionList().add(opinion);
        expertService.registerExpert(expertByEmail);
    }
*/

}
