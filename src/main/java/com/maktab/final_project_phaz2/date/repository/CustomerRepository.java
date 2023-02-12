package com.maktab.final_project_phaz2.date.repository;
import com.maktab.final_project_phaz2.date.model.Customer;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    Optional<Customer> findByEmailAddress(String emailAddress);
    Optional<Customer> findById(Long id);

 /*    static Specification<Customer>getSpecification(Customer customer){
    return ((root, query, criteriaBuilder) -> {
        query.distinct(true);
        Predicate predicateForCustomer = criteriaBuilder.equal(root.get("customerId"), customer.getId());

    })
    }*/
}
