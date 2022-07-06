package com.hae.service.dto;

import com.hae.domain.enumeration.Availability;
import com.hae.domain.enumeration.ChargerStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.hae.domain.Meta} entity.
 */
@Schema(description = "Meta정보")
public class MetaDTO implements Serializable {

    private Long id;

    /**
     * Whether the station is currently in active use or not
     */
    @Schema(description = "Whether the station is currently in active use or not")
    private Boolean active;

    /**
     * Who are available to see it and use it
     */
    @Schema(description = "Who are available to see it and use it")
    private Availability availability;

    /**
     * State the station is currently
     */
    @Schema(description = "State the station is currently")
    private ChargerStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    public ChargerStatus getStatus() {
        return status;
    }

    public void setStatus(ChargerStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MetaDTO)) {
            return false;
        }

        MetaDTO metaDTO = (MetaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, metaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MetaDTO{" +
            "id=" + getId() +
            ", active='" + getActive() + "'" +
            ", availability='" + getAvailability() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
