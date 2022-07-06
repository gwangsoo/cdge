package com.hae.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 충전기
 */
@Entity
@Table(name = "tb_cdge_evse")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Evse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * 그룹명
     */
    @Size(max = 256)
    @Column(name = "group_name", length = 256)
    private String groupName;

    /**
     * 사용가능여부 Whether this EVSE is available for usage
     */
    @NotNull
    @Column(name = "available", nullable = false)
    private Boolean available;

    /**
     * 예약가능여부 Whether this EVSE is available for reservation
     */
    @NotNull
    @Column(name = "reservable", nullable = false)
    private Boolean reservable;

    /**
     * 일시불여부 Currently GET stations/{id} uses this field. Won't be added in that endpoint for Hubject stations.
     */
    @NotNull
    @Column(name = "one_time_payment", nullable = false)
    private Boolean oneTimePayment;

    /**
     * 상태
     */
    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @OneToMany(mappedBy = "evse")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "evse" }, allowSetters = true)
    private Set<Connector> connectors = new HashSet<>();

    @OneToMany(mappedBy = "evse")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "evse" }, allowSetters = true)
    private Set<Pricing> pricings = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "evses", "chargers", "pictures" }, allowSetters = true)
    private Station station;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Evse id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public Evse groupName(String groupName) {
        this.setGroupName(groupName);
        return this;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Boolean getAvailable() {
        return this.available;
    }

    public Evse available(Boolean available) {
        this.setAvailable(available);
        return this;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Boolean getReservable() {
        return this.reservable;
    }

    public Evse reservable(Boolean reservable) {
        this.setReservable(reservable);
        return this;
    }

    public void setReservable(Boolean reservable) {
        this.reservable = reservable;
    }

    public Boolean getOneTimePayment() {
        return this.oneTimePayment;
    }

    public Evse oneTimePayment(Boolean oneTimePayment) {
        this.setOneTimePayment(oneTimePayment);
        return this;
    }

    public void setOneTimePayment(Boolean oneTimePayment) {
        this.oneTimePayment = oneTimePayment;
    }

    public Integer getStatus() {
        return this.status;
    }

    public Evse status(Integer status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<Connector> getConnectors() {
        return this.connectors;
    }

    public void setConnectors(Set<Connector> connectors) {
        if (this.connectors != null) {
            this.connectors.forEach(i -> i.setEvse(null));
        }
        if (connectors != null) {
            connectors.forEach(i -> i.setEvse(this));
        }
        this.connectors = connectors;
    }

    public Evse connectors(Set<Connector> connectors) {
        this.setConnectors(connectors);
        return this;
    }

    public Evse addConnector(Connector connector) {
        this.connectors.add(connector);
        connector.setEvse(this);
        return this;
    }

    public Evse removeConnector(Connector connector) {
        this.connectors.remove(connector);
        connector.setEvse(null);
        return this;
    }

    public Set<Pricing> getPricings() {
        return this.pricings;
    }

    public void setPricings(Set<Pricing> pricings) {
        if (this.pricings != null) {
            this.pricings.forEach(i -> i.setEvse(null));
        }
        if (pricings != null) {
            pricings.forEach(i -> i.setEvse(this));
        }
        this.pricings = pricings;
    }

    public Evse pricings(Set<Pricing> pricings) {
        this.setPricings(pricings);
        return this;
    }

    public Evse addPricing(Pricing pricing) {
        this.pricings.add(pricing);
        pricing.setEvse(this);
        return this;
    }

    public Evse removePricing(Pricing pricing) {
        this.pricings.remove(pricing);
        pricing.setEvse(null);
        return this;
    }

    public Station getStation() {
        return this.station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Evse station(Station station) {
        this.setStation(station);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Evse)) {
            return false;
        }
        return id != null && id.equals(((Evse) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Evse{" +
            "id=" + getId() +
            ", groupName='" + getGroupName() + "'" +
            ", available='" + getAvailable() + "'" +
            ", reservable='" + getReservable() + "'" +
            ", oneTimePayment='" + getOneTimePayment() + "'" +
            ", status=" + getStatus() +
            "}";
    }
}
