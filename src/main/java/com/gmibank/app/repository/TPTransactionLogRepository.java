package com.gmibank.app.repository;

import com.gmibank.app.domain.TPCustomer;
import com.gmibank.app.domain.TPTransactionLog;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the TPTransactionLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TPTransactionLogRepository extends JpaRepository<TPTransactionLog, Long> {

	@Query(value = "select tPTransactionLog from TPTransactionLog tPTransactionLog  where tPTransactionLog.tPAccount.id =:id")
	List<TPTransactionLog> findAllByAccountId(@Param("id") Long id);

}
