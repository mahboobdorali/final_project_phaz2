package com.maktab.final_project_phaz2.service;

import com.maktab.final_project_phaz2.date.model.OrderCustomer;
import com.maktab.final_project_phaz2.date.model.enumuration.CurrentSituation;
import com.maktab.final_project_phaz2.date.repository.OrderRepository;
import com.maktab.final_project_phaz2.exception.NoResultException;
import com.maktab.final_project_phaz2.exception.RequestIsNotValidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;



    public void saveAllOrder(OrderCustomer orderCustomer) {

        orderRepository.save(orderCustomer);
    }

    public void deleteAllOrder(OrderCustomer orderCustomer) {
        orderRepository.delete(orderCustomer);
    }

    public List<OrderCustomer> getAllOrders() {

        return orderRepository.findAll();
    }

    public OrderCustomer updateOrder(OrderCustomer orderCustomer) {
        return orderRepository.save(orderCustomer);
    }

    public OrderCustomer findOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new NoResultException("this order is not exist"));
    }

    public List<OrderCustomer> findOrderByUnderServiceAndStatus(String name, CurrentSituation status, CurrentSituation status1) {
        if (!(status.equals(CurrentSituation.WAITING_FOR_SPECIALIST_SELECTION) ||
                status1.equals(CurrentSituation.WAITING_FOR_EXPERT_OFFER)))
            throw new NoResultException("your order status is not accepted for ordering!!");
        return orderRepository.findAllByUnderService(name, status, status1);
    }

    public List<OrderCustomer> findOrderByStatusForPayedByCustomer(String emailAddress, CurrentSituation currentSituation) {
        if (!currentSituation.equals(CurrentSituation.DONE))
            throw new RequestIsNotValidException("your work is not finished yet!!you can not be redirect to the payment stage :(");
        return orderRepository.findAllByStatus(emailAddress, currentSituation);
    }
}
