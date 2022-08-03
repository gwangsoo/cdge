package com.hae.ecs.domain.charging.domain;

import com.hae.ecs.domain.charging.domain.enumeration.ChargingFavoriteStatus;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 충전소 즐겨찾기 정보
 */
@Entity
@Table(name = "tb_charging_favorites_spot")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ChargingFavoritesSpot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 45)
    @Column(name = "mem_no", length = 45, nullable = false)
    private String memNo;

    @NotNull
    @Size(max = 20)
    @Column(name = "cpo_name", length = 20, nullable = false)
    private String cpoName;

    @NotNull
    @Size(max = 40)
    @Column(name = "location_id", length = 40, nullable = false)
    private String locationId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ChargingFavoriteStatus status;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ChargingFavoritesSpot id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemNo() {
        return this.memNo;
    }

    public ChargingFavoritesSpot memNo(String memNo) {
        this.setMemNo(memNo);
        return this;
    }

    public void setMemNo(String memNo) {
        this.memNo = memNo;
    }

    public String getCpoName() {
        return this.cpoName;
    }

    public ChargingFavoritesSpot cpoName(String cpoName) {
        this.setCpoName(cpoName);
        return this;
    }

    public void setCpoName(String cpoName) {
        this.cpoName = cpoName;
    }

    public String getLocationId() {
        return this.locationId;
    }

    public ChargingFavoritesSpot locationId(String locationId) {
        this.setLocationId(locationId);
        return this;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public ChargingFavoriteStatus getStatus() {
        return this.status;
    }

    public ChargingFavoritesSpot status(ChargingFavoriteStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(ChargingFavoriteStatus status) {
        this.status = status;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChargingFavoritesSpot)) {
            return false;
        }
        return id != null && id.equals(((ChargingFavoritesSpot) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChargingFavoritesSpot{" +
            "id=" + getId() +
            ", memNo='" + getMemNo() + "'" +
            ", cpoName='" + getCpoName() + "'" +
            ", locationId='" + getLocationId() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
