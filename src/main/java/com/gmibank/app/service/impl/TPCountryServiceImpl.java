package com.gmibank.app.service.impl;

import com.gmibank.app.service.TPCountryService;
import com.gmibank.app.domain.TPCountry;
import com.gmibank.app.repository.TPCountryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link TPCountry}.
 */
@Service
@Transactional
public class TPCountryServiceImpl implements TPCountryService {

    private final Logger log = LoggerFactory.getLogger(TPCountryServiceImpl.class);

    private final TPCountryRepository tPCountryRepository;

    public TPCountryServiceImpl(TPCountryRepository tPCountryRepository) {
        this.tPCountryRepository = tPCountryRepository;
    }

    @Override
    public TPCountry save(TPCountry tPCountry) {
        log.debug("Request to save TPCountry : {}", tPCountry);
        return tPCountryRepository.save(tPCountry);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TPCountry> findAll() {
        log.debug("Request to get all TPCountries");
        return tPCountryRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TPCountry> findOne(Long id) {
        log.debug("Request to get TPCountry : {}", id);
        return tPCountryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TPCountry : {}", id);
        tPCountryRepository.deleteById(id);
    }
}
