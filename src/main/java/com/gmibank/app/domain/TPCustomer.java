package com.gmibank.app.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A TPCustomer.
 */
@Entity
@Table(name = "tp_customer")
public class TPCustomer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "middle_initial", nullable = false)
    private String middleInitial;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "mobile_phone_number")
    private String mobilePhoneNumber;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "zip_code", nullable = true)
    private String zipCode;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    
    @Column(name = "city", nullable = true)
    private String city;

    @NotNull
    @Column(name = "ssn", nullable = false, unique=true)
    private String ssn;

    @Column(name = "create_date")
    private Instant createDate;

    @Column(name = "zelle_enrolled")
    private Boolean zelleEnrolled;

    @ManyToOne(optional = false)
    @JoinColumn(name="country_id",unique=false)
    private TPCountry country;

    @Column(name = "state", nullable = true)
    private String state;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @ManyToMany
    @JoinTable(name = "tp_customer_account",
               joinColumns = @JoinColumn(name = "tpcustomer_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"))
    private Set<TPAccount> accounts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public TPCustomer firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public TPCustomer lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public TPCustomer middleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
        return this;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getEmail() {
        return email;
    }

    public TPCustomer email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public TPCustomer mobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
        return this;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public TPCustomer phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public TPCustomer zipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public TPCustomer address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public TPCustomer city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSsn() {
        return ssn;
    }

    public TPCustomer ssn(String ssn) {
        this.ssn = ssn;
        return this;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public TPCustomer createDate(Instant createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Boolean isZelleEnrolled() {
        return zelleEnrolled;
    }

    public TPCustomer zelleEnrolled(Boolean zelleEnrolled) {
        this.zelleEnrolled = zelleEnrolled;
        return this;
    }

    public void setZelleEnrolled(Boolean zelleEnrolled) {
        this.zelleEnrolled = zelleEnrolled;
    }

    public TPCountry getCountry() {
        return country;
    }

    public TPCustomer country(TPCountry tPCountry) {
        this.country = tPCountry;
        return this;
    }

    public void setCountry(TPCountry tPCountry) {
        this.country = tPCountry;
    }

    public User getUser() {
        return user;
    }

    public TPCustomer user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<TPAccount> getAccounts() {
        return accounts;
    }

    public TPCustomer accounts(Set<TPAccount> tPAccounts) {
        this.accounts = tPAccounts;
        return this;
    }

    public TPCustomer addAccount(TPAccount tPAccount) {
        this.accounts.add(tPAccount);
        tPAccount.getTpcustomers().add(this);
        return this;
    }

    public TPCustomer removeAccount(TPAccount tPAccount) {
        this.accounts.remove(tPAccount);
        tPAccount.getTpcustomers().remove(this);
        return this;
    }

    public void setAccounts(Set<TPAccount> tPAccounts) {
        this.accounts = tPAccounts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TPCustomer)) {
            return false;
        }
        return id != null && id.equals(((TPCustomer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TPCustomer{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", middleInitial='" + getMiddleInitial() + "'" +
            ", email='" + getEmail() + "'" +
            ", mobilePhoneNumber='" + getMobilePhoneNumber() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", address='" + getAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", ssn='" + getSsn() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", zelleEnrolled='" + isZelleEnrolled() + "'" +
            "}";
    }

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
}
