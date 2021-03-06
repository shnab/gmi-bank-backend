package com.gmibank.app.repository;

import com.gmibank.app.domain.TPCustomer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the TPCustomer entity.
 */
@Repository
public interface TPCustomerRepository extends JpaRepository<TPCustomer, Long> {

    @Query(value = "select distinct tPCustomer from TPCustomer tPCustomer left join fetch tPCustomer.accounts",
        countQuery = "select count(distinct tPCustomer) from TPCustomer tPCustomer")
    Page<TPCustomer> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct tPCustomer from TPCustomer tPCustomer left join fetch tPCustomer.accounts")
    List<TPCustomer> findAllWithEagerRelationships();

    @Query("select tPCustomer from TPCustomer tPCustomer left join fetch tPCustomer.accounts where tPCustomer.id =:id")
    Optional<TPCustomer> findOneWithEagerRelationships(@Param("id") Long id);
    
    
    @Query(value ="select tPCustomer from TPCustomer tPCustomer  where tPCustomer.user.id =:id")
    List<TPCustomer> findOneByUserId(@Param("id") Long id);
}
