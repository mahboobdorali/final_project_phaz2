package com.maktab.final_project_phaz2.service;

import com.maktab.final_project_phaz2.Util.DateUtil;
import com.maktab.final_project_phaz2.date.model.*;
import com.maktab.final_project_phaz2.date.model.enumuration.CurrentSituation;
import com.maktab.final_project_phaz2.date.repository.CustomerRepository;

import com.maktab.final_project_phaz2.exception.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final ServiceUnderService serviceUnderService;
    private final MainTaskService mainTaskService;
    private final UnderService underService;
    private final OrderService orderService;

    public void registerCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public void deleteCustomer(Customer customer) {
        customerRepository.delete(customer);
    }

    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    public Customer updateExpert(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer logIn(String emailAddress, String password) throws NoResultException {
        Customer customer = customerRepository.findByEmailAddress(emailAddress).
                orElseThrow(() -> new NoResultException(" this customer dose not exist"));
        if (customer.getPassword().equals(password))
            return customer;
        throw new NoResultException("is not exist password");
    }

    public void changePassword(String emailAddress, String oldPassword, String newPassword) throws NoResultException {
        Customer customer = customerRepository.findByEmailAddress(emailAddress).
                orElseThrow(() -> new NoResultException(" this customer dose not exist"));
        if (customer.getPassword().equals(oldPassword))
            customer.setPassword(newPassword);
        customerRepository.save(customer);
        throw new NoResultException("is not exist password");
    }

    public UnderService showUnderByName(String nameOfUnder) throws NoResultException {
        return serviceUnderService.underServiceByName(nameOfUnder);
    }

    public MainTask showAllService(String nameOfService) throws NoResultException {
        return mainTaskService.getServiceByName(nameOfService);
    }

    public void Order(OrderCustomer ordersCustomer, String nameTheService, String nameTheSubService) throws NoResultException {
        showAllService(nameTheService);
        showUnderByName(nameTheSubService);
        if (ordersCustomer.getProposedPrice() >= underService.getBasePrice()) {
            if (DateUtil.isDateValid(ordersCustomer.getDateAndTimeOfWork())) {
                ordersCustomer.setCurrentSituation(CurrentSituation.WAITING_FOR_EXPERT_ADVICE);
                orderService.saveAllOrder(ordersCustomer);
            }
            throw new NoResultException("date not valid");
        }
        throw new NoResultException("invalid inputs!!!");
    }
}
