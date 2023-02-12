package com.maktab.final_project_phaz2.date.repository;

import com.maktab.final_project_phaz2.date.dto.ExpertDto;
import com.maktab.final_project_phaz2.date.model.Expert;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpertRepository extends JpaRepository<Expert, Long>, JpaSpecificationExecutor<Expert> {
    Optional<Expert> findByEmailAddress(String emailAddress);

    Optional<Expert> findById(Long idExpert);

/*   static Specification<Expert> filterExpertByConditions(Expert expert) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (expert.getName() != null && expert.getName().length() != 0)
                predicateList.add(criteriaBuilder.equal(root.get("name"), expert.getName()));
            if (expert.getFamily() != null && expert.getFamily().length() != 0)
                predicateList.add(criteriaBuilder.equal(root.get("family"), expert.getFamily()));
            if (expert.getRole() != null)
                predicateList.add(criteriaBuilder.equal(root.get("role"), expert.getRole()));
            if (expert.getEmailAddress() != null && expert.getEmailAddress().length() != 0)
                predicateList.add(criteriaBuilder.equal(root.get("emailAddress"), expert.getEmailAddress()));
            if (expert.getUnderServiceList() != null && expert.getUnderServiceList().size() != 0)
                predicateList.add(criteriaBuilder.equal(root.get("underServiceList"), expert.getUnderServiceList()));
            if (expert.getAverageScore() < 6)
                query.orderBy(criteriaBuilder.desc(root.get("averageScore")));
            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        });
    }*/
}


