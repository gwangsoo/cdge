package com.hae.ecs.domain.charging.service.dto;

import com.hae.ecs.domain.charging.domain.enumeration.ChargingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.hae.ecs.domain.charging.domain.ChargingUseDisplay} entity.
 */
@Schema(description = "통합 충전소 정보")
public class ChargingUseDisplayDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 40)
    private String locationId;

    @NotNull
    @Size(max = 50)
    private String evseId;

    @NotNull
    @Size(max = 40)
    private String connectorId;

    @NotNull
    private Boolean possibleCharingFlag;

    @Size(max = 10)
    private String insteadCharing;

    @Size(max = 255)
    private String name;

    @Size(max = 10)
    private String latitude;

    @Size(max = 11)
    private String longitude;

    @NotNull
    @Size(max = 50)
    private String address;

    @NotNull
    @Size(max = 50)
    private String city;

    @Size(max = 10)
    private String postalCode;

    @Size(max = 255)
    private String openingTimes;

    private ChargingStatus status;

    @NotNull
    @Size(max = 20)
    private String cpoName;

    @NotNull
    @Size(max = 5)
    private String powerType;

    @NotNull
    private Integer price;

    private Integer vat;

    @NotNull
    @Size(max = 20)
    private String phoneNumber;

    @NotNull
    @Size(max = 30)
    private String paymentMethod;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getPossibleCharingFlag() {
        return possibleCharingFlag;
    }

    public void setPossibleCharingFlag(Boolean possibleCharingFlag) {
        this.possibleCharingFlag = possibleCharingFlag;
    }

    public String getInsteadCharing() {
        return insteadCharing;
    }

    public void setInsteadCharing(String insteadCharing) {
        this.insteadCharing = insteadCharing;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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

    public String getOpeningTimes() {
        return openingTimes;
    }

    public void setOpeningTimes(String openingTimes) {
        this.openingTimes = openingTimes;
    }

    public ChargingStatus getStatus() {
        return status;
    }

    public void setStatus(ChargingStatus status) {
        this.status = status;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getVat() {
        return vat;
    }

    public void setVat(Integer vat) {
        this.vat = vat;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChargingUseDisplayDTO)) {
            return false;
        }

        ChargingUseDisplayDTO chargingUseDisplayDTO = (ChargingUseDisplayDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, chargingUseDisplayDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChargingUseDisplayDTO{" +
            "id=" + getId() +
            ", locationId='" + getLocationId() + "'" +
            ", evseId='" + getEvseId() + "'" +
            ", connectorId='" + getConnectorId() + "'" +
            ", possibleCharingFlag='" + getPossibleCharingFlag() + "'" +
            ", insteadCharing='" + getInsteadCharing() + "'" +
            ", name='" + getName() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", address='" + getAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", openingTimes='" + getOpeningTimes() + "'" +
            ", status='" + getStatus() + "'" +
            ", cpoName='" + getCpoName() + "'" +
            ", powerType='" + getPowerType() + "'" +
            ", price=" + getPrice() +
            ", vat=" + getVat() +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", paymentMethod='" + getPaymentMethod() + "'" +
            "}";
    }
}
