package com.gmibank.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A TPState.
 */
@Entity
@Table(name = "tp_state")
public class TPState implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JsonIgnoreProperties(value = "states", allowSetters = true)
    private TPCountry tPCountry;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TPState name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TPCountry getTPCountry() {
        return tPCountry;
    }

    public TPState tPCountry(TPCountry tPCountry) {
        this.tPCountry = tPCountry;
        return this;
    }

    public void setTPCountry(TPCountry tPCountry) {
        this.tPCountry = tPCountry;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TPState)) {
            return false;
        }
        return id != null && id.equals(((TPState) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TPState{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
