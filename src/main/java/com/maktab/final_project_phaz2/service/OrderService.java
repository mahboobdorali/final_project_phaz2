package com.maktab.final_project_phaz2.service;

import com.maktab.final_project_phaz2.date.dto.OrderCustomerFilterDto;
import com.maktab.final_project_phaz2.date.model.*;
import com.maktab.final_project_phaz2.date.model.enumuration.CurrentSituation;
import com.maktab.final_project_phaz2.date.repository.OrderRepository;
import com.maktab.final_project_phaz2.exception.NoResultException;
import com.maktab.final_project_phaz2.exception.RequestIsNotValidException;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ServiceUnderService serviceUnderService;

    private final MainTaskService mainTaskService;


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

    @Transactional
    public List<OrderCustomer> filterOrderByAdmin(OrderCustomerFilterDto orderDto) {
        return orderRepository.findAll((Specification<OrderCustomer>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (orderDto.getCurrentSituation() != null)
                predicateList.add(criteriaBuilder.equal(root.get("currentSituation"), orderDto.getCurrentSituation()));
            if (orderDto.getNameSubService() != null && orderDto.getNameSubService().length() != 0) {
                UnderService service = serviceUnderService.findUnderServiceByName(orderDto.getNameSubService());
                Join<OrderCustomer, UnderService> join = root.join("underService");
                predicateList.add(criteriaBuilder.equal(join.get("nameSubService"), service.getNameSubService()));
            }
            if (orderDto.getBeforeTime() != null && orderDto.getAfterTime() != null)
                predicateList.add(criteriaBuilder.between(root.get("dateAndTimeOfWork"), orderDto.getAfterTime(), orderDto.getBeforeTime()));

            if (orderDto.getName() != null && orderDto.getName().length() != 0) {
                MainTask mainTask = mainTaskService.findServiceByName(orderDto.getName());
                Root<OrderCustomer> orderCustomerRoot = query.from(OrderCustomer.class);
                Root<UnderService> underServiceRoot = query.from(UnderService.class);
                Root<MainTask> mainTaskRoot = query.from(MainTask.class);
                query.select(orderCustomerRoot.get("underService"));
                query.where(criteriaBuilder.and(
                        criteriaBuilder.equal(orderCustomerRoot.get("underService"), underServiceRoot.get("nameSubService")),
                        criteriaBuilder.equal(mainTaskRoot.get("name"), underServiceRoot.get("mainTask")),
                        criteriaBuilder.equal(mainTaskRoot.get("name"), mainTask.getName())));
            }
            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        });
    }

    public int countOrderOfCustomer(String emailAddress, CurrentSituation currentSituation) {
        return orderRepository.countOrderCustomer(emailAddress, currentSituation);
    }

    public int countOrderOfExpert(String emailAddress, CurrentSituation currentSituation) {
        return orderRepository.countOrderExpert(emailAddress, currentSituation);
    }

    public List<OrderCustomer> showAllOrderCustomer(String emailCustomer, CurrentSituation currentSituation) {
        return orderRepository.findAllByStatus(emailCustomer,currentSituation);
    }
    public List<OrderCustomer> showAllOrderExpert(String emailExpert, CurrentSituation currentSituation) {
        return orderRepository.findAllByStatusByExpert(emailExpert,currentSituation);
    }

}
