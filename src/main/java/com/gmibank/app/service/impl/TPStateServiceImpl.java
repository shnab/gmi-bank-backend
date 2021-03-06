package com.gmibank.app.service.impl;

import com.gmibank.app.service.TPStateService;
import com.gmibank.app.domain.TPState;
import com.gmibank.app.repository.TPStateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link TPState}.
 */
@Service
@Transactional
public class TPStateServiceImpl implements TPStateService {

    private final Logger log = LoggerFactory.getLogger(TPStateServiceImpl.class);

    private final TPStateRepository tPStateRepository;

    public TPStateServiceImpl(TPStateRepository tPStateRepository) {
        this.tPStateRepository = tPStateRepository;
    }

    @Override
    public TPState save(TPState tPState) {
        log.debug("Request to save TPState : {}", tPState);
        return tPStateRepository.save(tPState);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TPState> findAll() {
        log.debug("Request to get all TPStates");
        return tPStateRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TPState> findOne(Long id) {
        log.debug("Request to get TPState : {}", id);
        return tPStateRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TPState : {}", id);
        tPStateRepository.deleteById(id);
    }
}
