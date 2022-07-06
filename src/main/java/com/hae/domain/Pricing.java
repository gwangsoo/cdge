package com.hae.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 요금정책
 */
@Entity
@Table(name = "tb_cdge_pricing")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Pricing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * Translation key for the price name
     */
    @NotNull
    @Size(max = 128)
    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "price_cents", nullable = false)
    private Double priceCents;

    @NotNull
    @Size(max = 3)
    @Column(name = "currency", length = 3, nullable = false)
    private String currency;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "price_cents_without_vat", nullable = false)
    private Double priceCentsWithoutVat;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "price_cents_vat", nullable = false)
    private Double priceCentsVat;

    @ManyToOne
    @JsonIgnoreProperties(value = { "connectors", "pricings", "station" }, allowSetters = true)
    private Evse evse;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Pricing id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Pricing name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPriceCents() {
        return this.priceCents;
    }

    public Pricing priceCents(Double priceCents) {
        this.setPriceCents(priceCents);
        return this;
    }

    public void setPriceCents(Double priceCents) {
        this.priceCents = priceCents;
    }

    public String getCurrency() {
        return this.currency;
    }

    public Pricing currency(String currency) {
        this.setCurrency(currency);
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getPriceCentsWithoutVat() {
        return this.priceCentsWithoutVat;
    }

    public Pricing priceCentsWithoutVat(Double priceCentsWithoutVat) {
        this.setPriceCentsWithoutVat(priceCentsWithoutVat);
        return this;
    }

    public void setPriceCentsWithoutVat(Double priceCentsWithoutVat) {
        this.priceCentsWithoutVat = priceCentsWithoutVat;
    }

    public Double getPriceCentsVat() {
        return this.priceCentsVat;
    }

    public Pricing priceCentsVat(Double priceCentsVat) {
        this.setPriceCentsVat(priceCentsVat);
        return this;
    }

    public void setPriceCentsVat(Double priceCentsVat) {
        this.priceCentsVat = priceCentsVat;
    }

    public Evse getEvse() {
        return this.evse;
    }

    public void setEvse(Evse evse) {
        this.evse = evse;
    }

    public Pricing evse(Evse evse) {
        this.setEvse(evse);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pricing)) {
            return false;
        }
        return id != null && id.equals(((Pricing) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pricing{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", priceCents=" + getPriceCents() +
            ", currency='" + getCurrency() + "'" +
            ", priceCentsWithoutVat=" + getPriceCentsWithoutVat() +
            ", priceCentsVat=" + getPriceCentsVat() +
            "}";
    }
}
