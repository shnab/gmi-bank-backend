package com.gmibank.app.service.dto;

import java.time.Instant;

import com.gmibank.app.domain.enumeration.TPAccountStatusType;
import com.gmibank.app.domain.enumeration.TPAccountType;

public class CustomerAccountsDTO {
	   
	    private Long id;

	    private String description;
	
	    private Integer balance;

	    private TPAccountType accountType;

	    private TPAccountStatusType accountStatusType;

	    private Instant createDate;

		/**
		 * @return the id
		 */
		public Long getId() {
			return id;
		}

		/**
		 * @param id the id to set
		 */
		public void setId(Long id) {
			this.id = id;
		}

		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}

		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}

		/**
		 * @return the balance
		 */
		public Integer getBalance() {
			return balance;
		}

		/**
		 * @param balance the balance to set
		 */
		public void setBalance(Integer balance) {
			this.balance = balance;
		}

		/**
		 * @return the accountType
		 */
		public TPAccountType getAccountType() {
			return accountType;
		}

		/**
		 * @param accountType the accountType to set
		 */
		public void setAccountType(TPAccountType accountType) {
			this.accountType = accountType;
		}

		/**
		 * @return the accountStatusType
		 */
		public TPAccountStatusType getAccountStatusType() {
			return accountStatusType;
		}

		/**
		 * @param accountStatusType the accountStatusType to set
		 */
		public void setAccountStatusType(TPAccountStatusType accountStatusType) {
			this.accountStatusType = accountStatusType;
		}

		/**
		 * @return the createDate
		 */
		public Instant getCreateDate() {
			return createDate;
		}

		/**
		 * @param createDate the createDate to set
		 */
		public void setCreateDate(Instant createDate) {
			this.createDate = createDate;
		}


	    // prettier-ignore
	    @Override
	    public String toString() {
	        return "CustomerAccount{" +
	            "id=" + getId() +
	            ", description='" + getDescription() + "'" +
	            ", balance=" + getBalance() +
	            ", accountType='" + getAccountType() + "'" +
	            ", accountStatusType='" + getAccountStatusType() + "'" +
	            ", createDate='" + getCreateDate() + "'" +
	            "}";
	    }
}
