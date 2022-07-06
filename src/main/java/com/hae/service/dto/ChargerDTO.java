package com.hae.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.hae.domain.Charger} entity.
 */
@Schema(description = "충전기")
public class ChargerDTO implements Serializable {

    private Long id;

    private MetaDTO meta;

    private StationDTO station;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MetaDTO getMeta() {
        return meta;
    }

    public void setMeta(MetaDTO meta) {
        this.meta = meta;
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
        if (!(o instanceof ChargerDTO)) {
            return false;
        }

        ChargerDTO chargerDTO = (ChargerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, chargerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChargerDTO{" +
            "id=" + getId() +
            ", meta=" + getMeta() +
            ", station=" + getStation() +
            "}";
    }
}
