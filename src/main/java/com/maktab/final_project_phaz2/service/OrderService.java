package com.maktab.final_project_phaz2.service;
import com.maktab.final_project_phaz2.date.model.OrderCustomer;
import com.maktab.final_project_phaz2.date.model.UnderService;
import com.maktab.final_project_phaz2.date.repository.OrderRepository;
import com.maktab.final_project_phaz2.exception.NoResultException;
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

    public void deleteAllOrder(OrderCustomer orderCustomer)
    {
        orderRepository.delete(orderCustomer);
    }

    public List<OrderCustomer> getAllOrders() {

        return orderRepository.findAll();
    }
    public OrderCustomer updateOrder(OrderCustomer orderCustomer) {
        return orderRepository.save(orderCustomer);
    }
    public OrderCustomer findOrderById(Long id) throws NoResultException {
        return orderRepository.findById(id).orElseThrow(() -> new NoResultException("this order is not exist"));
    }
}