package com.hae.ecs.domain.charging.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.hae.ecs.domain.charging.domain.ChargingUseHistory} entity.
 */
@Schema(description = "충전 이력 정보")
public class ChargingUseHistoryDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 256)
    private String chargingUseDisplayId;

    @NotNull
    @Size(max = 40)
    private String locationId;

    @NotNull
    @Size(max = 50)
    private String evseId;

    @NotNull
    @Size(max = 40)
    private String connectorId;

    @Size(max = 20)
    private String memNo;

    @Size(max = 20)
    private String carCd;

    private ZonedDateTime startDateTime;

    private ZonedDateTime endDateTime;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private Double firstSoc;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private Double finalSoc;

    @Min(value = 0)
    private Integer firstDistance;

    @Min(value = 0)
    private Integer finalDistance;

    @Size(max = 256)
    private String name;

    @NotNull
    @Size(max = 50)
    private String address;

    @NotNull
    @Size(max = 50)
    private String city;

    @Size(max = 10)
    private String postalCode;

    @NotNull
    @Size(max = 20)
    private String cpoName;

    @NotNull
    @Size(max = 5)
    private String powerType;

    @Min(value = 0)
    private Integer totalCost;

    @Min(value = 0)
    private Integer totalEnergy;

    @Min(value = 0)
    private Integer totalTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChargingUseDisplayId() {
        return chargingUseDisplayId;
    }

    public void setChargingUseDisplayId(String chargingUseDisplayId) {
        this.chargingUseDisplayId = chargingUseDisplayId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getEvseId() {
        return evseId;
    }

    public void setEvseId(String evseId) {
        this.evseId = evseId;
    }

    public String getConnectorId() {
        return connectorId;
    }

    public void setConnectorId(String connectorId) {
        this.connectorId = connectorId;
    }

    public String getMemNo() {
        return memNo;
    }

    public void setMemNo(String memNo) {
        this.memNo = memNo;
    }

    public String getCarCd() {
        return carCd;
    }

    public void setCarCd(String carCd) {
        this.carCd = carCd;
    }

    public ZonedDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(ZonedDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public ZonedDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(ZonedDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Double getFirstSoc() {
        return firstSoc;
    }

    public void setFirstSoc(Double firstSoc) {
        this.firstSoc = firstSoc;
    }

    public Double getFinalSoc() {
        return finalSoc;
    }

    public void setFinalSoc(Double finalSoc) {
        this.finalSoc = finalSoc;
    }

    public Integer getFirstDistance() {
        return firstDistance;
    }

    public void setFirstDistance(Integer firstDistance) {
        this.firstDistance = firstDistance;
    }

    public Integer getFinalDistance() {
        return finalDistance;
    }

    public void setFinalDistance(Integer finalDistance) {
        this.finalDistance = finalDistance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCpoName() {
        return cpoName;
    }

    public void setCpoName(String cpoName) {
        this.cpoName = cpoName;
    }

    public String getPowerType() {
        return powerType;
    }

    public void setPowerType(String powerType) {
        this.powerType = powerType;
    }

    public Integer getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }

    public Integer getTotalEnergy() {
        return totalEnergy;
    }

    public void setTotalEnergy(Integer totalEnergy) {
        this.totalEnergy = totalEnergy;
    }

    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChargingUseHistoryDTO)) {
            return false;
        }

        ChargingUseHistoryDTO chargingUseHistoryDTO = (ChargingUseHistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, chargingUseHistoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChargingUseHistoryDTO{" +
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
