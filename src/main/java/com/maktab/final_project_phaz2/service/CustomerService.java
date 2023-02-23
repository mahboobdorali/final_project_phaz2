package com.maktab.final_project_phaz2.service;

import com.maktab.final_project_phaz2.Util.DateUtil;
//import com.maktab.final_project_phaz2.date.dto.CreditCardDto;
import com.maktab.final_project_phaz2.date.dto.SearchCustomerDto;
import com.maktab.final_project_phaz2.date.model.*;
import com.maktab.final_project_phaz2.date.model.enumuration.ApprovalStatus;
import com.maktab.final_project_phaz2.date.model.enumuration.CurrentSituation;
import com.maktab.final_project_phaz2.date.model.enumuration.Role;
import com.maktab.final_project_phaz2.date.repository.CustomerRepository;
import com.maktab.final_project_phaz2.date.repository.OfferRepository;
import com.maktab.final_project_phaz2.exception.DuplicateEntryException;
import com.maktab.final_project_phaz2.exception.InputInvalidException;
import com.maktab.final_project_phaz2.exception.NoResultException;
import com.maktab.final_project_phaz2.exception.RequestIsNotValidException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final OrderService orderService;
    private final OfferRepository offerRepository;
    private final OfferService offerService;
    private final ServiceUnderService underService;
    private final OpinionService opinionService;
    private final ExpertService expertService;

    private final PasswordEncoder passwordEncoder;

    public void registerCustomer(Customer customer) {
        Optional<Customer> byEmailAddress = customerRepository.findByEmailAddress(customer.getEmailAddress());
        if (byEmailAddress.isPresent())
            throw new DuplicateEntryException("you have already registered once in the system");
        customer.setRole(Role.CUSTOMER);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
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
            throw new InputInvalidException("is not exist password");
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

    public void changePassword(String oldPassword, String newPassword) {
        Customer customer = customerRepository.findByEmailAddress(((Person) SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal()).getEmailAddress()).
                orElseThrow(() -> new NoResultException(" this customer dose not exist"));
        if (!(customer.getPassword().equals(oldPassword)))
            throw new RequestIsNotValidException("is not exist password");
        customer.setPassword(newPassword);
        customerRepository.save(customer);
    }

    @Transactional
    public void Order(OrderCustomer ordersCustomer, Long idOfChoiceUnderService) {
        UnderService serviceById = underService.findUnderServiceById(idOfChoiceUnderService);
        Customer customerById = findCustomerById(((Person) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getId());
        if (ordersCustomer.getProposedPrice() < serviceById.getBasePrice())
            throw new RequestIsNotValidException("the entered price is lower than the allowed limit!!");
        if (DateUtil.isNotDateValid(ordersCustomer.getDateAndTimeOfWork()))
            throw new RequestIsNotValidException("the entered date is lower than the allowed limit!!");
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
            throw new RequestIsNotValidException("time has not come to do the work!!");
        orderById.setCurrentSituation(CurrentSituation.STARTED);
        offerService.updateOffer(offerId);
    }

    public void setDoneDate(Date doneDate, Long idOffer, Long idOrder) {
        Offer offer = offerService.findById(idOffer);
        OrderCustomer order = orderService.findOrderById(idOrder);
        if (doneDate.before(offer.getTimeProposeToStartWork()))
            throw new NoResultException("the entered time is before the time suggested by the expert");
        order.setWorkDone(doneDate);
        orderService.saveAllOrder(order);
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

    public void paymentFromCredit(Long idChoiceOffer) {
        Offer offerById = offerService.findById(idChoiceOffer);
        Customer customerByEmail = findCustomerByEmail(((Person) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getEmailAddress());
        if (offerById.getPriceOffer() > customerByEmail.getAmount())
            throw new RequestIsNotValidException("price offer is more than stock!!");
        customerByEmail.setAmount(customerByEmail.getAmount() - offerById.getPriceOffer());
        double percent = (offerById.getPriceOffer() / 100) * 70;
        offerById.getExpert().setAmount(percent);
        offerById.getOrderCustomer().setCurrentSituation(CurrentSituation.PAID);
        offerService.updateOffer(offerById);
        registerCustomer(customerByEmail);
    }

    public void saveComments(Opinion opinion,Long idExpert) {
        Expert byId = expertService.findExpertById(idExpert);
        if (opinion.getScore() < 1 || opinion.getScore() > 6)
            throw new RequestIsNotValidException("the score entered must be a number between one and five(1-5");
        opinionService.registerOpinion(opinion);
        byId.setAverageScore(byId.getAverageScore() + opinion.getScore());
        expertService.updateExpert(byId);
    }

    public List<Customer> filterCustomerByCondition(SearchCustomerDto customer) {
        return customerRepository.findAll((Specification<Customer>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicate = new ArrayList<>();
            if (customer.getName() != null && customer.getName().length() != 0)
                predicate.add(criteriaBuilder.equal(root.get("name"), customer.getName()));
            if (customer.getFamily() != null && customer.getFamily().length() != 0)
                predicate.add(criteriaBuilder.equal(root.get("family"), customer.getFamily()));
            if (customer.getRole() != null)
                predicate.add(criteriaBuilder.equal(root.get("role"), customer.getRole()));
            if (customer.getEmailAddress() != null && customer.getEmailAddress().length() != 0)
                predicate.add(criteriaBuilder.equal(root.get("emailAddress"), customer.getEmailAddress()));
            if (customer.getAfterTime() != null && customer.getBeforeTime() != null)
                predicate.add(criteriaBuilder.between(root.get("dateAndTimeOfRegistration"), customer.getAfterTime(), customer.getBeforeTime()));
            return criteriaBuilder.and(predicate.toArray(new Predicate[0]));
        });
    }
    public double showAmountToCustomer() {
        Customer customer = findCustomerByEmail(((Person) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getEmailAddress());
        return customer.getAmount();
    }
}
