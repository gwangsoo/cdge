package com.hae.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.hae.domain.Picture} entity.
 */
@Schema(description = "Picture")
public class PictureDTO implements Serializable {

    private Long id;

    @Size(max = 256)
    private String context;

    private StationDTO station;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
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
        if (!(o instanceof PictureDTO)) {
            return false;
        }

        PictureDTO pictureDTO = (PictureDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pictureDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PictureDTO{" +
            "id=" + getId() +
            ", context='" + getContext() + "'" +
            ", station=" + getStation() +
            "}";
    }
}
