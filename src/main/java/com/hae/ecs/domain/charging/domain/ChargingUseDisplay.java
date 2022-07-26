package com.hae.ecs.domain.charging.domain;

import com.hae.ecs.domain.charging.domain.enumeration.ChargingStatus;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 통합 충전소 정보
 */
@Entity
@Table(name = "tb_chrging_use_display")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ChargingUseDisplay implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

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

    @NotNull
    @Column(name = "possible_charing_flag", nullable = false)
    private Boolean possibleCharingFlag;

    @Size(max = 10)
    @Column(name = "instead_charing", length = 10)
    private String insteadCharing;

    @Size(max = 255)
    @Column(name = "name", length = 255)
    private String name;

    @Size(max = 10)
    @Column(name = "latitude", length = 10)
    private String latitude;

    @Size(max = 11)
    @Column(name = "longitude", length = 11)
    private String longitude;

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

    @Size(max = 255)
    @Column(name = "opening_times", length = 255)
    private String openingTimes;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ChargingStatus status;

    @NotNull
    @Size(max = 20)
    @Column(name = "cpo_name", length = 20, nullable = false)
    private String cpoName;

    @NotNull
    @Size(max = 5)
    @Column(name = "power_type", length = 5, nullable = false)
    private String powerType;

    @NotNull
    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "vat")
    private Integer vat;

    @NotNull
    @Size(max = 20)
    @Column(name = "phone_number", length = 20, nullable = false)
    private String phoneNumber;

    @NotNull
    @Size(max = 30)
    @Column(name = "payment_method", length = 30, nullable = false)
    private String paymentMethod;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ChargingUseDisplay id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocationId() {
        return this.locationId;
    }

    public ChargingUseDisplay locationId(String locationId) {
        this.setLocationId(locationId);
        return this;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getEvseId() {
        return this.evseId;
    }

    public ChargingUseDisplay evseId(String evseId) {
        this.setEvseId(evseId);
        return this;
    }

    public void setEvseId(String evseId) {
        this.evseId = evseId;
    }

    public String getConnectorId() {
        return this.connectorId;
    }

    public ChargingUseDisplay connectorId(String connectorId) {
        this.setConnectorId(connectorId);
        return this;
    }

    public void setConnectorId(String connectorId) {
        this.connectorId = connectorId;
    }

    public Boolean getPossibleCharingFlag() {
        return this.possibleCharingFlag;
    }

    public ChargingUseDisplay possibleCharingFlag(Boolean possibleCharingFlag) {
        this.setPossibleCharingFlag(possibleCharingFlag);
        return this;
    }

    public void setPossibleCharingFlag(Boolean possibleCharingFlag) {
        this.possibleCharingFlag = possibleCharingFlag;
    }

    public String getInsteadCharing() {
        return this.insteadCharing;
    }

    public ChargingUseDisplay insteadCharing(String insteadCharing) {
        this.setInsteadCharing(insteadCharing);
        return this;
    }

    public void setInsteadCharing(String insteadCharing) {
        this.insteadCharing = insteadCharing;
    }

    public String getName() {
        return this.name;
    }

    public ChargingUseDisplay name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public ChargingUseDisplay latitude(String latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public ChargingUseDisplay longitude(String longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return this.address;
    }

    public ChargingUseDisplay address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return this.city;
    }

    public ChargingUseDisplay city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public ChargingUseDisplay postalCode(String postalCode) {
        this.setPostalCode(postalCode);
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getOpeningTimes() {
        return this.openingTimes;
    }

    public ChargingUseDisplay openingTimes(String openingTimes) {
        this.setOpeningTimes(openingTimes);
        return this;
    }

    public void setOpeningTimes(String openingTimes) {
        this.openingTimes = openingTimes;
    }

    public ChargingStatus getStatus() {
        return this.status;
    }

    public ChargingUseDisplay status(ChargingStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(ChargingStatus status) {
        this.status = status;
    }

    public String getCpoName() {
        return this.cpoName;
    }

    public ChargingUseDisplay cpoName(String cpoName) {
        this.setCpoName(cpoName);
        return this;
    }

    public void setCpoName(String cpoName) {
        this.cpoName = cpoName;
    }

    public String getPowerType() {
        return this.powerType;
    }

    public ChargingUseDisplay powerType(String powerType) {
        this.setPowerType(powerType);
        return this;
    }

    public void setPowerType(String powerType) {
        this.powerType = powerType;
    }

    public Integer getPrice() {
        return this.price;
    }

    public ChargingUseDisplay price(Integer price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getVat() {
        return this.vat;
    }

    public ChargingUseDisplay vat(Integer vat) {
        this.setVat(vat);
        return this;
    }

    public void setVat(Integer vat) {
        this.vat = vat;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public ChargingUseDisplay phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPaymentMethod() {
        return this.paymentMethod;
    }

    public ChargingUseDisplay paymentMethod(String paymentMethod) {
        this.setPaymentMethod(paymentMethod);
        return this;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChargingUseDisplay)) {
            return false;
        }
        return id != null && id.equals(((ChargingUseDisplay) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChargingUseDisplay{" +
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
