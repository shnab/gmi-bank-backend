package com.gmibank.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.gmibank.app.domain.enumeration.TPAccountType;

import com.gmibank.app.domain.enumeration.TPAccountStatusType;

/**
 * A TPAccount.
 */
@Entity
@Table(name = "tp_account")
public class TPAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "balance", nullable = false)
    private Integer balance;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private TPAccountType accountType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "account_status_type", nullable = false)
    private TPAccountStatusType accountStatusType;

    @Column(name = "create_date")
    private Instant createDate;

    @Column(name = "closed_date")
    private Instant closedDate;

    @OneToOne
    @JoinColumn(unique = true)
    private TPEmployee employee;

    @OneToMany(mappedBy = "tPAccount")
    private Set<TPTransactionLog> accountlogs = new HashSet<>();

    @ManyToMany(mappedBy = "accounts")
    @JsonIgnore
    private Set<TPCustomer> tpcustomers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public TPAccount description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getBalance() {
        return balance;
    }

    public TPAccount balance(Integer balance) {
        this.balance = balance;
        return this;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public TPAccountType getAccountType() {
        return accountType;
    }

    public TPAccount accountType(TPAccountType accountType) {
        this.accountType = accountType;
        return this;
    }

    public void setAccountType(TPAccountType accountType) {
        this.accountType = accountType;
    }

    public TPAccountStatusType getAccountStatusType() {
        return accountStatusType;
    }

    public TPAccount accountStatusType(TPAccountStatusType accountStatusType) {
        this.accountStatusType = accountStatusType;
        return this;
    }

    public void setAccountStatusType(TPAccountStatusType accountStatusType) {
        this.accountStatusType = accountStatusType;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public TPAccount createDate(Instant createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getClosedDate() {
        return closedDate;
    }

    public TPAccount closedDate(Instant closedDate) {
        this.closedDate = closedDate;
        return this;
    }

    public void setClosedDate(Instant closedDate) {
        this.closedDate = closedDate;
    }

    public TPEmployee getEmployee() {
        return employee;
    }

    public TPAccount employee(TPEmployee tPEmployee) {
        this.employee = tPEmployee;
        return this;
    }

    public void setEmployee(TPEmployee tPEmployee) {
        this.employee = tPEmployee;
    }

    public Set<TPTransactionLog> getAccountlogs() {
        return accountlogs;
    }

    public TPAccount accountlogs(Set<TPTransactionLog> tPTransactionLogs) {
        this.accountlogs = tPTransactionLogs;
        return this;
    }

    public TPAccount addAccountlog(TPTransactionLog tPTransactionLog) {
        this.accountlogs.add(tPTransactionLog);
        tPTransactionLog.setTPAccount(this);
        return this;
    }

    public TPAccount removeAccountlog(TPTransactionLog tPTransactionLog) {
        this.accountlogs.remove(tPTransactionLog);
        tPTransactionLog.setTPAccount(null);
        return this;
    }

    public void setAccountlogs(Set<TPTransactionLog> tPTransactionLogs) {
        this.accountlogs = tPTransactionLogs;
    }

    public Set<TPCustomer> getTpcustomers() {
        return tpcustomers;
    }

    public TPAccount tpcustomers(Set<TPCustomer> tPCustomers) {
        this.tpcustomers = tPCustomers;
        return this;
    }

    public TPAccount addTpcustomer(TPCustomer tPCustomer) {
        this.tpcustomers.add(tPCustomer);
        tPCustomer.getAccounts().add(this);
        return this;
    }

    public TPAccount removeTpcustomer(TPCustomer tPCustomer) {
        this.tpcustomers.remove(tPCustomer);
        tPCustomer.getAccounts().remove(this);
        return this;
    }

    public void setTpcustomers(Set<TPCustomer> tPCustomers) {
        this.tpcustomers = tPCustomers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TPAccount)) {
            return false;
        }
        return id != null && id.equals(((TPAccount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TPAccount{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", balance=" + getBalance() +
            ", accountType='" + getAccountType() + "'" +
            ", accountStatusType='" + getAccountStatusType() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", closedDate='" + getClosedDate() + "'" +
            "}";
    }
}
