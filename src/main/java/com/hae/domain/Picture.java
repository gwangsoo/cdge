package com.hae.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Picture
 */
@Entity
@Table(name = "tb_cdge_picture")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Picture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 256)
    @Column(name = "context", length = 256)
    private String context;

    @ManyToOne
    @JsonIgnoreProperties(value = { "evses", "chargers", "pictures" }, allowSetters = true)
    private Station station;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Picture id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContext() {
        return this.context;
    }

    public Picture context(String context) {
        this.setContext(context);
        return this;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Station getStation() {
        return this.station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Picture station(Station station) {
        this.setStation(station);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Picture)) {
            return false;
        }
        return id != null && id.equals(((Picture) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Picture{" +
            "id=" + getId() +
            ", context='" + getContext() + "'" +
            "}";
    }
}
