package com.hae.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.hae.domain.Socket} entity.
 */
@Schema(description = "Socket")
public class SocketDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private MetaDTO meta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MetaDTO getMeta() {
        return meta;
    }

    public void setMeta(MetaDTO meta) {
        this.meta = meta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SocketDTO)) {
            return false;
        }

        SocketDTO socketDTO = (SocketDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, socketDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SocketDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", meta=" + getMeta() +
            "}";
    }
}
