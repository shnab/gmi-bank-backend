package com.gmibank.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A TPTransactionLog.
 */
@Entity
@Table(name = "tp_transaction_log")
public class TPTransactionLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "transaction_date", nullable = false)
    private Instant transactionDate;

    @NotNull
    @Column(name = "transaction_amount", nullable = false)
    private Integer transactionAmount;

    @NotNull
    @Column(name = "new_balance", nullable = false)
    private Integer newBalance;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JsonIgnoreProperties(value = "accountlogs", allowSetters = true)
    private TPAccount tPAccount;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTransactionDate() {
        return transactionDate;
    }

    public TPTransactionLog transactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
        return this;
    }

    public void setTransactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Integer getTransactionAmount() {
        return transactionAmount;
    }

    public TPTransactionLog transactionAmount(Integer transactionAmount) {
        this.transactionAmount = transactionAmount;
        return this;
    }

    public void setTransactionAmount(Integer transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Integer getNewBalance() {
        return newBalance;
    }

    public TPTransactionLog newBalance(Integer newBalance) {
        this.newBalance = newBalance;
        return this;
    }

    public void setNewBalance(Integer newBalance) {
        this.newBalance = newBalance;
    }

    public String getDescription() {
        return description;
    }

    public TPTransactionLog description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TPAccount getTPAccount() {
        return tPAccount;
    }

    public TPTransactionLog tPAccount(TPAccount tPAccount) {
        this.tPAccount = tPAccount;
        return this;
    }

    public void setTPAccount(TPAccount tPAccount) {
        this.tPAccount = tPAccount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TPTransactionLog)) {
            return false;
        }
        return id != null && id.equals(((TPTransactionLog) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TPTransactionLog{" +
            "id=" + getId() +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", transactionAmount=" + getTransactionAmount() +
            ", newBalance=" + getNewBalance() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
