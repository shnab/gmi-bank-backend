package com.gmibank.app.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmibank.app.domain.TPAccount;
import com.gmibank.app.domain.TPCustomer;
import com.gmibank.app.repository.TPCustomerRepository;
import com.gmibank.app.service.TPCustomerService;

/**
 * Service Implementation for managing {@link TPCustomer}.
 */
@Service
@Transactional
public class TPCustomerServiceImpl implements TPCustomerService {

    private final Logger log = LoggerFactory.getLogger(TPCustomerServiceImpl.class);

    private final TPCustomerRepository tPCustomerRepository;

    public TPCustomerServiceImpl(TPCustomerRepository tPCustomerRepository) {
        this.tPCustomerRepository = tPCustomerRepository;
    }

    @Override
    public TPCustomer save(TPCustomer tPCustomer) {
        log.debug("Request to save TPCustomer : {}", tPCustomer);
        return tPCustomerRepository.save(tPCustomer);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TPCustomer> findAll(Pageable pageable) {
        log.debug("Request to get all TPCustomers");
        return tPCustomerRepository.findAll(pageable);
    }


    public Page<TPCustomer> findAllWithEagerRelationships(Pageable pageable) {
        return tPCustomerRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TPCustomer> findOne(Long id) {
        log.debug("Request to get TPCustomer : {}", id);
        return tPCustomerRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TPCustomer : {}", id);
        tPCustomerRepository.deleteById(id);
    }
    

    public Set<TPAccount> getCustomerAccounts(Long id) {
    	List<TPCustomer> customerList = tPCustomerRepository.findOneByUserId(id);
    	Set<TPAccount> accounts=new HashSet<TPAccount>();
    	if(!customerList.isEmpty()) {
    	    accounts = customerList.get(0).getAccounts();
    	}
    	return accounts;
    }
}
