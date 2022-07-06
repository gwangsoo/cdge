package com.hae.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.hae.domain.Evse} entity.
 */
@Schema(description = "충전기")
public class EvseDTO implements Serializable {

    private Long id;

    /**
     * 그룹명
     */
    @Size(max = 256)
    @Schema(description = "그룹명")
    private String groupName;

    /**
     * 사용가능여부 Whether this EVSE is available for usage
     */
    @NotNull
    @Schema(description = "사용가능여부 Whether this EVSE is available for usage", required = true)
    private Boolean available;

    /**
     * 예약가능여부 Whether this EVSE is available for reservation
     */
    @NotNull
    @Schema(description = "예약가능여부 Whether this EVSE is available for reservation", required = true)
    private Boolean reservable;

    /**
     * 일시불여부 Currently GET stations/{id} uses this field. Won't be added in that endpoint for Hubject stations.
     */
    @NotNull
    @Schema(
        description = "일시불여부 Currently GET stations/{id} uses this field. Won't be added in that endpoint for Hubject stations.",
        required = true
    )
    private Boolean oneTimePayment;

    /**
     * 상태
     */
    @NotNull
    @Schema(description = "상태", required = true)
    private Integer status;

    private StationDTO station;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Boolean getReservable() {
        return reservable;
    }

    public void setReservable(Boolean reservable) {
        this.reservable = reservable;
    }

    public Boolean getOneTimePayment() {
        return oneTimePayment;
    }

    public void setOneTimePayment(Boolean oneTimePayment) {
        this.oneTimePayment = oneTimePayment;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public StationDTO getStation() {
        return station;
    }

    public void setStation(StationDTO station) {
        this.station = station;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EvseDTO)) {
            return false;
        }

        EvseDTO evseDTO = (EvseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, evseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EvseDTO{" +
            "id=" + getId() +
            ", groupName='" + getGroupName() + "'" +
            ", available='" + getAvailable() + "'" +
            ", reservable='" + getReservable() + "'" +
            ", oneTimePayment='" + getOneTimePayment() + "'" +
            ", status=" + getStatus() +
            ", station=" + getStation() +
            "}";
    }
}
