package com.gmibank.app.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * A DTO for the {@link com.gmibank.app.domain.TPAccountRegistration} entity.
 */
public class TPAccountRegistrationDTO {
    
    private Long id;

    @NotNull
    @Pattern(regexp = "/^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$/")
    private String ssn;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String address;

    @Pattern(regexp = "[0-9]{3}-[0-9]{3}-[0-9]{4}")
    private String mobilePhoneNumber;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TPAccountRegistrationDTO)) {
            return false;
        }

        return id != null && id.equals(((TPAccountRegistrationDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TPAccountRegistrationDTO{" +
            "id=" + getId() +
            ", ssn='" + getSsn() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", address='" + getAddress() + "'" +
            ", mobilePhoneNumber='" + getMobilePhoneNumber() + "'" +
            "}";
    }
}
