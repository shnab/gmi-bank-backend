package com.gmibank.app.service.impl;

import com.gmibank.app.service.TPEmployeeService;
import com.gmibank.app.domain.TPEmployee;
import com.gmibank.app.repository.TPEmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link TPEmployee}.
 */
@Service
@Transactional
public class TPEmployeeServiceImpl implements TPEmployeeService {

    private final Logger log = LoggerFactory.getLogger(TPEmployeeServiceImpl.class);

    private final TPEmployeeRepository tPEmployeeRepository;

    public TPEmployeeServiceImpl(TPEmployeeRepository tPEmployeeRepository) {
        this.tPEmployeeRepository = tPEmployeeRepository;
    }

    @Override
    public TPEmployee save(TPEmployee tPEmployee) {
        log.debug("Request to save TPEmployee : {}", tPEmployee);
        return tPEmployeeRepository.save(tPEmployee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TPEmployee> findAll() {
        log.debug("Request to get all TPEmployees");
        return tPEmployeeRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TPEmployee> findOne(Long id) {
        log.debug("Request to get TPEmployee : {}", id);
        return tPEmployeeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TPEmployee : {}", id);
        tPEmployeeRepository.deleteById(id);
    }
}
