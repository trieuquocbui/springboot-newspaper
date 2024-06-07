package com.bqt.newspaper.specification;

import com.bqt.newspaper.constant.PaginationConstant;
import com.bqt.newspaper.entities.Role;
import com.bqt.newspaper.entities.User;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor
@AllArgsConstructor
public class UserSpecification implements Specification<User> {

    private SearchCriteria searchCriteria;


    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        if (searchCriteria.getSearch() != null){
            predicate = criteriaBuilder.and(predicate,criteriaBuilder.like(root.get(PaginationConstant.USERNAME), "%" + searchCriteria.getSearch() + "%"));
        }
        Join<User, Role> roleJoin = root.join("role");
        predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(roleJoin.get("roleId"), PaginationConstant.USER_ROLE));

        return predicate;
    }
}
