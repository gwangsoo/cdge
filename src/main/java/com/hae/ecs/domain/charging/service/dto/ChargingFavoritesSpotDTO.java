package com.hae.ecs.domain.charging.service.dto;

import com.hae.ecs.domain.charging.domain.enumeration.ChargingFavoriteStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.hae.ecs.domain.charging.domain.ChargingFavoritesSpot} entity.
 */
@Schema(description = "충전소 즐겨찾기 정보")
public class ChargingFavoritesSpotDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 45)
    private String memNo;

    @NotNull
    @Size(max = 20)
    private String cpoName;

    @NotNull
    @Size(max = 40)
    private String locationId;

    @NotNull
    private ChargingFavoriteStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemNo() {
        return memNo;
    }

    public void setMemNo(String memNo) {
        this.memNo = memNo;
    }

    public String getCpoName() {
        return cpoName;
    }

    public void setCpoName(String cpoName) {
        this.cpoName = cpoName;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public ChargingFavoriteStatus getStatus() {
        return status;
    }

    public void setStatus(ChargingFavoriteStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChargingFavoritesSpotDTO)) {
            return false;
        }

        ChargingFavoritesSpotDTO chargingFavoritesSpotDTO = (ChargingFavoritesSpotDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, chargingFavoritesSpotDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChargingFavoritesSpotDTO{" +
            "id=" + getId() +
            ", memNo='" + getMemNo() + "'" +
            ", cpoName='" + getCpoName() + "'" +
            ", locationId='" + getLocationId() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
