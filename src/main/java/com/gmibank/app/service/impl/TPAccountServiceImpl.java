package com.gmibank.app.service.impl;

import com.gmibank.app.service.TPAccountService;
import com.gmibank.app.domain.TPAccount;
import com.gmibank.app.repository.TPAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link TPAccount}.
 */
@Service
@Transactional
public class TPAccountServiceImpl implements TPAccountService {

    private final Logger log = LoggerFactory.getLogger(TPAccountServiceImpl.class);

    private final TPAccountRepository tPAccountRepository;

    public TPAccountServiceImpl(TPAccountRepository tPAccountRepository) {
        this.tPAccountRepository = tPAccountRepository;
    }

    @Override
    public TPAccount save(TPAccount tPAccount) {
        log.debug("Request to save TPAccount : {}", tPAccount);
        return tPAccountRepository.save(tPAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TPAccount> findAll() {
        log.debug("Request to get all TPAccounts");
        return tPAccountRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TPAccount> findOne(Long id) {
        log.debug("Request to get TPAccount : {}", id);
        return tPAccountRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TPAccount : {}", id);
        tPAccountRepository.deleteById(id);
    }
}
