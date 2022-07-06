package com.hae.service.dto;

import com.hae.domain.enumeration.Provider;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.hae.domain.Station} entity.
 */
@Schema(description = "충전소")
public class StationDTO implements Serializable {

    private Long id;

    /**
     * 판매자ID
     */
    @Schema(description = "판매자ID")
    private Integer sellerId;

    /**
     * 충전소명
     */
    @Size(max = 256)
    @Schema(description = "충전소명")
    private String name;

    /**
     * 위도
     */
    @Schema(description = "위도")
    private Double latitude;

    /**
     * 경도
     */
    @Schema(description = "경도")
    private Double longitide;

    /**
     * icon ID
     */
    @Schema(description = "icon ID")
    private Integer icon;

    /**
     * 주소
     */
    @Size(max = 256)
    @Schema(description = "주소")
    private String address;

    /**
     * 도시
     */
    @Size(max = 128)
    @Schema(description = "도시")
    private String city;

    /**
     * 운영시간
     */
    @Size(max = 64)
    @Schema(description = "운영시간")
    private String openHours;

    /**
     * 제공자
     */
    @Schema(description = "제공자")
    private Provider provider;

    /**
     * 알림메세지
     */
    @Size(max = 256)
    @Schema(description = "알림메세지")
    private String alertMessage;

    /**
     * 이동여부
     */
    @Schema(description = "이동여부")
    private Boolean isRemoved;

    /**
     * 사적이용여부
     */
    @Schema(description = "사적이용여부")
    private Boolean isPrivate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitide() {
        return longitide;
    }

    public void setLongitide(Double longitide) {
        this.longitide = longitide;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
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

    public String getOpenHours() {
        return openHours;
    }

    public void setOpenHours(String openHours) {
        this.openHours = openHours;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public Boolean getIsRemoved() {
        return isRemoved;
    }

    public void setIsRemoved(Boolean isRemoved) {
        this.isRemoved = isRemoved;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StationDTO)) {
            return false;
        }

        StationDTO stationDTO = (StationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, stationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StationDTO{" +
            "id=" + getId() +
            ", sellerId=" + getSellerId() +
            ", name='" + getName() + "'" +
            ", latitude=" + getLatitude() +
            ", longitide=" + getLongitide() +
            ", icon=" + getIcon() +
            ", address='" + getAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", openHours='" + getOpenHours() + "'" +
            ", provider='" + getProvider() + "'" +
            ", alertMessage='" + getAlertMessage() + "'" +
            ", isRemoved='" + getIsRemoved() + "'" +
            ", isPrivate='" + getIsPrivate() + "'" +
            "}";
    }
}
