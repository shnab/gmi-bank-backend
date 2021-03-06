package com.gmibank.app.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TPCountry.
 */
@Entity
@Table(name = "tp_country")
public class TPCountry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "tPCountry")
    private Set<TPState> states = new HashSet<>();

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

    public TPCountry name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<TPState> getStates() {
        return states;
    }

    public TPCountry states(Set<TPState> tPStates) {
        this.states = tPStates;
        return this;
    }

    public TPCountry addState(TPState tPState) {
        this.states.add(tPState);
        tPState.setTPCountry(this);
        return this;
    }

    public TPCountry removeState(TPState tPState) {
        this.states.remove(tPState);
        tPState.setTPCountry(null);
        return this;
    }

    public void setStates(Set<TPState> tPStates) {
        this.states = tPStates;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TPCountry)) {
            return false;
        }
        return id != null && id.equals(((TPCountry) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TPCountry{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
