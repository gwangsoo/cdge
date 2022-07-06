package com.hae.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hae.domain.enumeration.ConnectorType;
import com.hae.domain.enumeration.CurrentType;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 콘넥터
 */
@Entity
@Table(name = "tb_cdge_connector")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Connector implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * 콘넥터 유형 (Mennekes)
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ConnectorType type;

    /**
     * 최대소비전력량 (18)
     */
    @DecimalMin(value = "0")
    @Column(name = "max_kwh")
    private Double maxKwh;

    /**
     * 최대소비전력 (18)
     */
    @DecimalMin(value = "0")
    @Column(name = "max_kw")
    private Double maxKw;

    /**
     * 전류유형 (AC)
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "current_type", nullable = false)
    private CurrentType currentType;

    /**
     * 상태
     */
    @Column(name = "status")
    private String status;

    @ManyToOne
    @JsonIgnoreProperties(value = { "connectors", "pricings", "station" }, allowSetters = true)
    private Evse evse;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Connector id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ConnectorType getType() {
        return this.type;
    }

    public Connector type(ConnectorType type) {
        this.setType(type);
        return this;
    }

    public void setType(ConnectorType type) {
        this.type = type;
    }

    public Double getMaxKwh() {
        return this.maxKwh;
    }

    public Connector maxKwh(Double maxKwh) {
        this.setMaxKwh(maxKwh);
        return this;
    }

    public void setMaxKwh(Double maxKwh) {
        this.maxKwh = maxKwh;
    }

    public Double getMaxKw() {
        return this.maxKw;
    }

    public Connector maxKw(Double maxKw) {
        this.setMaxKw(maxKw);
        return this;
    }

    public void setMaxKw(Double maxKw) {
        this.maxKw = maxKw;
    }

    public CurrentType getCurrentType() {
        return this.currentType;
    }

    public Connector currentType(CurrentType currentType) {
        this.setCurrentType(currentType);
        return this;
    }

    public void setCurrentType(CurrentType currentType) {
        this.currentType = currentType;
    }

    public String getStatus() {
        return this.status;
    }

    public Connector status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Evse getEvse() {
        return this.evse;
    }

    public void setEvse(Evse evse) {
        this.evse = evse;
    }

    public Connector evse(Evse evse) {
        this.setEvse(evse);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Connector)) {
            return false;
        }
        return id != null && id.equals(((Connector) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Connector{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", maxKwh=" + getMaxKwh() +
            ", maxKw=" + getMaxKw() +
            ", currentType='" + getCurrentType() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
