package com.gmibank.app.service.impl;

import com.gmibank.app.service.TPTransactionLogService;
import com.gmibank.app.domain.TPTransactionLog;
import com.gmibank.app.repository.TPTransactionLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link TPTransactionLog}.
 */
@Service
@Transactional
public class TPTransactionLogServiceImpl implements TPTransactionLogService {

    private final Logger log = LoggerFactory.getLogger(TPTransactionLogServiceImpl.class);

    private final TPTransactionLogRepository tPTransactionLogRepository;

    public TPTransactionLogServiceImpl(TPTransactionLogRepository tPTransactionLogRepository) {
        this.tPTransactionLogRepository = tPTransactionLogRepository;
    }

    @Override
    public TPTransactionLog save(TPTransactionLog tPTransactionLog) {
        log.debug("Request to save TPTransactionLog : {}", tPTransactionLog);
        return tPTransactionLogRepository.save(tPTransactionLog);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TPTransactionLog> findAll() {
        log.debug("Request to get all TPTransactionLogs");
        return tPTransactionLogRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TPTransactionLog> findOne(Long id) {
        log.debug("Request to get TPTransactionLog : {}", id);
        return tPTransactionLogRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TPTransactionLog : {}", id);
        tPTransactionLogRepository.deleteById(id);
    }
    
    
    @Override
    @Transactional(readOnly = true)
    public List<TPTransactionLog> findAllByAccountId(Long id) {
        log.debug("Request to get all TPTransactionLogs");
        return tPTransactionLogRepository.findAllByAccountId(id);
    }

    
}
