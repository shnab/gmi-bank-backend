package com.gmibank.app.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;
import javax.transaction.TransactionalException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.gmibank.app.domain.TPAccount;
import com.gmibank.app.domain.TPCustomer;
import com.gmibank.app.domain.TPTransactionLog;
import com.gmibank.app.domain.TPTransfer;
import com.gmibank.app.domain.User;
import com.gmibank.app.service.TPAccountService;
import com.gmibank.app.service.TPCustomerService;
import com.gmibank.app.service.TPTransactionLogService;
import com.gmibank.app.service.UserService;
import com.gmibank.app.service.dto.CustomerAccountsDTO;
import com.gmibank.app.web.rest.errors.BadRequestAlertException;
import com.gmibank.app.web.rest.errors.NoAuthorizedRequest;
import com.gmibank.app.web.rest.errors.SSNNotFoundException;
import com.gmibank.app.web.rest.errors.TransferBalanceExceedsException;
import com.gmibank.app.web.rest.errors.TransferException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.gmibank.app.domain.TPCustomer}.
 */
@RestController
@RequestMapping("/api")
public class TPCustomerResource {

	private final Logger log = LoggerFactory.getLogger(TPCustomerResource.class);

	private static final String ENTITY_NAME = "tPCustomer";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final TPCustomerService tPCustomerService;
	private final UserService userService;
	private final TPAccountService accountService;
	private final TPTransactionLogService transactionService;
	public TPCustomerResource(TPCustomerService tPCustomerService, UserService userService, TPAccountService accountService,TPTransactionLogService transactionService) {
		this.tPCustomerService = tPCustomerService;
		this.userService = userService;
		this.accountService=accountService;
		this.transactionService=transactionService;
	}

	/**
	 * {@code POST  /tp-customers} : Create a new tPCustomer.
	 *
	 * @param tPCustomer the tPCustomer to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new tPCustomer, or with status {@code 400 (Bad Request)} if
	 *         the tPCustomer has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/tp-customers")
	public ResponseEntity<TPCustomer> createTPCustomer(@Valid @RequestBody TPCustomer tPCustomer)
			throws URISyntaxException {
		log.debug("REST request to save TPCustomer : {}", tPCustomer);
		if (tPCustomer.getId() != null) {
			throw new BadRequestAlertException("A new tPCustomer cannot already have an ID", ENTITY_NAME, "idexists");
		}
		TPCustomer result = tPCustomerService.save(tPCustomer);
		return ResponseEntity
				.created(new URI("/api/tp-customers/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /tp-customers} : Updates an existing tPCustomer.
	 *
	 * @param tPCustomer the tPCustomer to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated tPCustomer, or with status {@code 400 (Bad Request)} if
	 *         the tPCustomer is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the tPCustomer couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/tp-customers")
	public ResponseEntity<TPCustomer> updateTPCustomer(@Valid @RequestBody TPCustomer tPCustomer)
			throws URISyntaxException {
		log.debug("REST request to update TPCustomer : {}", tPCustomer);
		if (tPCustomer.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		TPCustomer result = tPCustomerService.save(tPCustomer);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tPCustomer.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /tp-customers} : get all the tPCustomers.
	 *
	 * @param pageable  the pagination information.
	 * @param eagerload flag to eager load entities from relationships (This is
	 *                  applicable for many-to-many).
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of tPCustomers in body.
	 */
	@GetMapping("/tp-customers")
	public ResponseEntity<List<TPCustomer>> getAllTPCustomers(Pageable pageable,
			@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
		log.debug("REST request to get a page of TPCustomers");
		Page<TPCustomer> page;
		if (eagerload) {
			page = tPCustomerService.findAllWithEagerRelationships(pageable);
		} else {
			page = tPCustomerService.findAll(pageable);
		}
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /tp-customers/:id} : get the "id" tPCustomer.
	 *
	 * @param id the id of the tPCustomer to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the tPCustomer, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/tp-customers/{id}")
	public ResponseEntity<TPCustomer> getTPCustomer(@PathVariable Long id) {
		log.debug("REST request to get TPCustomer : {}", id);
		Optional<TPCustomer> tPCustomer = tPCustomerService.findOne(id);
		return ResponseUtil.wrapOrNotFound(tPCustomer);
	}

	/**
	 * {@code DELETE  /tp-customers/:id} : delete the "id" tPCustomer.
	 *
	 * @param id the id of the tPCustomer to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/tp-customers/{id}")
	public ResponseEntity<Void> deleteTPCustomer(@PathVariable Long id) {
		log.debug("REST request to delete TPCustomer : {}", id);
		tPCustomerService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}

	/**
	 * {@code GET  /tp-accounts/:id} : get the "id" tPAccount.
	 *
	 * @param id the id of the tPAccount to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the tPAccount, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/tp-customers/accounts/{id}")
	@Transactional
	public List<CustomerAccountsDTO> getTPAccounts(@PathVariable Long id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedUserName="";

		if (authentication != null) {
			loggedUserName = authentication.getName();
		}

		log.debug("REST request to get TPAccount : {}", id);
		Optional<User> user = this.userService.getUserByLoginName(id);
		List<CustomerAccountsDTO> list = new ArrayList<CustomerAccountsDTO>();
		if (user.get().getLogin().equals(loggedUserName)) {
			Set<TPAccount> customerAccounts = tPCustomerService.getCustomerAccounts(user.get().getId());

			for (TPAccount tpAccount : customerAccounts) {
				CustomerAccountsDTO ca = new CustomerAccountsDTO();
				ca.setId(tpAccount.getId());
				ca.setBalance(tpAccount.getBalance());
				ca.setDescription(tpAccount.getDescription());
				ca.setCreateDate(tpAccount.getCreateDate());
				ca.setAccountType(tpAccount.getAccountType());
				ca.setAccountStatusType(tpAccount.getAccountStatusType());
				list.add(ca);
			}
		}
		return list;
	}
	
	@PostMapping("/tp-customers/transfer")
	@ResponseStatus(HttpStatus.CREATED)
	@Transactional
	public void getMakeTransfer(@Valid @RequestBody TPTransfer tPTransfer)  {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedUserName="";

		if (authentication != null) {
			loggedUserName = authentication.getName();
		}
		Optional<User> user = this.userService.getUserByLoginName(tPTransfer.getUserId());
		if (user.get().getLogin().equals(loggedUserName)) {
		Optional<TPAccount> from = accountService.findOne(tPTransfer.getFromAccountId());
		Optional<TPAccount> to = accountService.findOne(tPTransfer.getToAccountId());
		
		double transferBalance=((double)tPTransfer.getBalance())/(double)100;
		
		if(tPTransfer.getBalance()>from.get().getBalance()) {
			throw new TransferBalanceExceedsException();
		}
		
		Integer balance = from.get().getBalance();
		balance=balance-tPTransfer.getBalance();
		from.get().setBalance(balance);
		
		TPTransactionLog logFrom=new TPTransactionLog();
		logFrom.setNewBalance(balance);
		logFrom.setDescription(String.format("Transfer %f to account ID: %d ", transferBalance,tPTransfer.getToAccountId()));
		logFrom.setTransactionAmount(tPTransfer.getBalance());
		logFrom.setTransactionDate(Instant.now());
		logFrom.setTPAccount(from.get());
		
		
		Integer balanceto = to.get().getBalance();
		balanceto=balanceto+tPTransfer.getBalance();
		to.get().setBalance(balanceto);
		
		TPTransactionLog logTo=new TPTransactionLog();
		logTo.setNewBalance(balanceto);
		logTo.setDescription(String.format("Transfer %f from account ID: %d ", transferBalance,tPTransfer.getFromAccountId()));
		logTo.setTransactionAmount(tPTransfer.getBalance());
		logTo.setTransactionDate(Instant.now());
		logTo.setTPAccount(to.get());
		
	
		TPTransactionLog transaction = transactionService.save(logFrom);
		transactionService.save(logTo);
		TPAccount fromAccount = accountService.save(from.get());
		accountService.save(to.get());
		
		tPTransfer.setCreateDate(Instant.now());
		
//		return ResponseEntity.ok(tPTransfer);
		
		}
		else {
            throw new NoAuthorizedRequest();
        }

	}

}
