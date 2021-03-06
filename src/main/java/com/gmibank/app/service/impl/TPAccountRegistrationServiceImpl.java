package com.gmibank.app.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmibank.app.domain.TPAccountRegistration;
import com.gmibank.app.repository.TPAccountRegistrationRepository;
import com.gmibank.app.service.TPAccountRegistrationService;

@Service
@Transactional
public class TPAccountRegistrationServiceImpl implements TPAccountRegistrationService {

    private final Logger log = LoggerFactory.getLogger(TPAccountRegistrationServiceImpl.class);

    private final TPAccountRegistrationRepository tPAccountRegistrationRepository;

    public TPAccountRegistrationServiceImpl(TPAccountRegistrationRepository tPAccountRegistrationRepository) {
        this.tPAccountRegistrationRepository = tPAccountRegistrationRepository;
    }

    @Override
    public TPAccountRegistration save(TPAccountRegistration tPAccountRegistration) {
       return tPAccountRegistrationRepository.save(tPAccountRegistration);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TPAccountRegistration> findAll() {
        log.debug("Request to get all TPAccountRegistration");
        return tPAccountRegistrationRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TPAccountRegistration> findOne(Long id) {
        log.debug("Request to get TPAccountRegistration : {}", id);
        return tPAccountRegistrationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TPAccountRegistration : {}", id);
        tPAccountRegistrationRepository.deleteById(id);
    }

	@Override
	public List<TPAccountRegistration> findOneBySSN(String ssn) {
		return tPAccountRegistrationRepository.findOneBySSN(ssn);
	}
    
    
}



