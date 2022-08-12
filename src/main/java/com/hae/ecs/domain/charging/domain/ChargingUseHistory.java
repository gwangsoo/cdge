package com.hae.ecs.domain.charging.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 충전 이력 정보
 */
@Entity
@Table(name = "tb_charging_use_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ChargingUseHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 256)
    @Column(name = "charging_use_display_id", length = 256, nullable = false, unique = true)
    private String chargingUseDisplayId;

    @NotNull
    @Size(max = 40)
    @Column(name = "location_id", length = 40, nullable = false)
    private String locationId;

    @NotNull
    @Size(max = 50)
    @Column(name = "evse_id", length = 50, nullable = false)
    private String evseId;

    @NotNull
    @Size(max = 40)
    @Column(name = "connector_id", length = 40, nullable = false)
    private String connectorId;

    @Size(max = 20)
    @Column(name = "mem_no", length = 20)
    private String memNo;

    @Size(max = 20)
    @Column(name = "car_cd", length = 20)
    private String carCd;

    @Column(name = "start_date_time")
    private ZonedDateTime startDateTime;

    @Column(name = "end_date_time")
    private ZonedDateTime endDateTime;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "first_soc")
    private Double firstSoc;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "final_soc")
    private Double finalSoc;

    @Min(value = 0)
    @Column(name = "first_distance")
    private Integer firstDistance;

    @Min(value = 0)
    @Column(name = "final_distance")
    private Integer finalDistance;

    @Size(max = 256)
    @Column(name = "name", length = 256)
    private String name;

    @NotNull
    @Size(max = 50)
    @Column(name = "address", length = 50, nullable = false)
    private String address;

    @NotNull
    @Size(max = 50)
    @Column(name = "city", length = 50, nullable = false)
    private String city;

    @Size(max = 10)
    @Column(name = "postal_code", length = 10)
    private String postalCode;

    @NotNull
    @Size(max = 20)
    @Column(name = "cpo_name", length = 20, nullable = false)
    private String cpoName;

    @NotNull
    @Size(max = 5)
    @Column(name = "power_type", length = 5, nullable = false)
    private String powerType;

    @Min(value = 0)
    @Column(name = "total_cost")
    private Integer totalCost;

    @Min(value = 0)
    @Column(name = "total_energy")
    private Integer totalEnergy;

    @Min(value = 0)
    @Column(name = "total_time")
    private Integer totalTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ChargingUseHistory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChargingUseDisplayId() {
        return this.chargingUseDisplayId;
    }

    public ChargingUseHistory chargingUseDisplayId(String chargingUseDisplayId) {
        this.setChargingUseDisplayId(chargingUseDisplayId);
        return this;
    }

    public void setChargingUseDisplayId(String chargingUseDisplayId) {
        this.chargingUseDisplayId = chargingUseDisplayId;
    }

    public String getLocationId() {
        return this.locationId;
    }

    public ChargingUseHistory locationId(String locationId) {
        this.setLocationId(locationId);
        return this;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getEvseId() {
        return this.evseId;
    }

    public ChargingUseHistory evseId(String evseId) {
        this.setEvseId(evseId);
        return this;
    }

    public void setEvseId(String evseId) {
        this.evseId = evseId;
    }

    public String getConnectorId() {
        return this.connectorId;
    }

    public ChargingUseHistory connectorId(String connectorId) {
        this.setConnectorId(connectorId);
        return this;
    }

    public void setConnectorId(String connectorId) {
        this.connectorId = connectorId;
    }

    public String getMemNo() {
        return this.memNo;
    }

    public ChargingUseHistory memNo(String memNo) {
        this.setMemNo(memNo);
        return this;
    }

    public void setMemNo(String memNo) {
        this.memNo = memNo;
    }

    public String getCarCd() {
        return this.carCd;
    }

    public ChargingUseHistory carCd(String carCd) {
        this.setCarCd(carCd);
        return this;
    }

    public void setCarCd(String carCd) {
        this.carCd = carCd;
    }

    public ZonedDateTime getStartDateTime() {
        return this.startDateTime;
    }

    public ChargingUseHistory startDateTime(ZonedDateTime startDateTime) {
        this.setStartDateTime(startDateTime);
        return this;
    }

    public void setStartDateTime(ZonedDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public ZonedDateTime getEndDateTime() {
        return this.endDateTime;
    }

    public ChargingUseHistory endDateTime(ZonedDateTime endDateTime) {
        this.setEndDateTime(endDateTime);
        return this;
    }

    public void setEndDateTime(ZonedDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Double getFirstSoc() {
        return this.firstSoc;
    }

    public ChargingUseHistory firstSoc(Double firstSoc) {
        this.setFirstSoc(firstSoc);
        return this;
    }

    public void setFirstSoc(Double firstSoc) {
        this.firstSoc = firstSoc;
    }

    public Double getFinalSoc() {
        return this.finalSoc;
    }

    public ChargingUseHistory finalSoc(Double finalSoc) {
        this.setFinalSoc(finalSoc);
        return this;
    }

    public void setFinalSoc(Double finalSoc) {
        this.finalSoc = finalSoc;
    }

    public Integer getFirstDistance() {
        return this.firstDistance;
    }

    public ChargingUseHistory firstDistance(Integer firstDistance) {
        this.setFirstDistance(firstDistance);
        return this;
    }

    public void setFirstDistance(Integer firstDistance) {
        this.firstDistance = firstDistance;
    }

    public Integer getFinalDistance() {
        return this.finalDistance;
    }

    public ChargingUseHistory finalDistance(Integer finalDistance) {
        this.setFinalDistance(finalDistance);
        return this;
    }

    public void setFinalDistance(Integer finalDistance) {
        this.finalDistance = finalDistance;
    }

    public String getName() {
        return this.name;
    }

    public ChargingUseHistory name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public ChargingUseHistory address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return this.city;
    }

    public ChargingUseHistory city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public ChargingUseHistory postalCode(String postalCode) {
        this.setPostalCode(postalCode);
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCpoName() {
        return this.cpoName;
    }

    public ChargingUseHistory cpoName(String cpoName) {
        this.setCpoName(cpoName);
        return this;
    }

    public void setCpoName(String cpoName) {
        this.cpoName = cpoName;
    }

    public String getPowerType() {
        return this.powerType;
    }

    public ChargingUseHistory powerType(String powerType) {
        this.setPowerType(powerType);
        return this;
    }

    public void setPowerType(String powerType) {
        this.powerType = powerType;
    }

    public Integer getTotalCost() {
        return this.totalCost;
    }

    public ChargingUseHistory totalCost(Integer totalCost) {
        this.setTotalCost(totalCost);
        return this;
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }

    public Integer getTotalEnergy() {
        return this.totalEnergy;
    }

    public ChargingUseHistory totalEnergy(Integer totalEnergy) {
        this.setTotalEnergy(totalEnergy);
        return this;
    }

    public void setTotalEnergy(Integer totalEnergy) {
        this.totalEnergy = totalEnergy;
    }

    public Integer getTotalTime() {
        return this.totalTime;
    }

    public ChargingUseHistory totalTime(Integer totalTime) {
        this.setTotalTime(totalTime);
        return this;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChargingUseHistory)) {
            return false;
        }
        return id != null && id.equals(((ChargingUseHistory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChargingUseHistory{" +
            "id=" + getId() +
            ", chargingUseDisplayId='" + getChargingUseDisplayId() + "'" +
            ", locationId='" + getLocationId() + "'" +
            ", evseId='" + getEvseId() + "'" +
            ", connectorId='" + getConnectorId() + "'" +
            ", memNo='" + getMemNo() + "'" +
            ", carCd='" + getCarCd() + "'" +
            ", startDateTime='" + getStartDateTime() + "'" +
            ", endDateTime='" + getEndDateTime() + "'" +
            ", firstSoc=" + getFirstSoc() +
            ", finalSoc=" + getFinalSoc() +
            ", firstDistance=" + getFirstDistance() +
            ", finalDistance=" + getFinalDistance() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", cpoName='" + getCpoName() + "'" +
            ", powerType='" + getPowerType() + "'" +
            ", totalCost=" + getTotalCost() +
            ", totalEnergy=" + getTotalEnergy() +
            ", totalTime=" + getTotalTime() +
            "}";
    }
}
