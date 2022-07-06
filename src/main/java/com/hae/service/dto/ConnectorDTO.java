package com.hae.service.dto;

import com.hae.domain.enumeration.ConnectorType;
import com.hae.domain.enumeration.CurrentType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.hae.domain.Connector} entity.
 */
@Schema(description = "콘넥터")
public class ConnectorDTO implements Serializable {

    private Long id;

    /**
     * 콘넥터 유형 (Mennekes)
     */
    @NotNull
    @Schema(description = "콘넥터 유형 (Mennekes)", required = true)
    private ConnectorType type;

    /**
     * 최대소비전력량 (18)
     */
    @DecimalMin(value = "0")
    @Schema(description = "최대소비전력량 (18)")
    private Double maxKwh;

    /**
     * 최대소비전력 (18)
     */
    @DecimalMin(value = "0")
    @Schema(description = "최대소비전력 (18)")
    private Double maxKw;

    /**
     * 전류유형 (AC)
     */
    @NotNull
    @Schema(description = "전류유형 (AC)", required = true)
    private CurrentType currentType;

    /**
     * 상태
     */
    @Schema(description = "상태")
    private String status;

    private EvseDTO evse;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ConnectorType getType() {
        return type;
    }

    public void setType(ConnectorType type) {
        this.type = type;
    }

    public Double getMaxKwh() {
        return maxKwh;
    }

    public void setMaxKwh(Double maxKwh) {
        this.maxKwh = maxKwh;
    }

    public Double getMaxKw() {
        return maxKw;
    }

    public void setMaxKw(Double maxKw) {
        this.maxKw = maxKw;
    }

    public CurrentType getCurrentType() {
        return currentType;
    }

    public void setCurrentType(CurrentType currentType) {
        this.currentType = currentType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public EvseDTO getEvse() {
        return evse;
    }

    public void setEvse(EvseDTO evse) {
        this.evse = evse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConnectorDTO)) {
            return false;
        }

        ConnectorDTO connectorDTO = (ConnectorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, connectorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConnectorDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", maxKwh=" + getMaxKwh() +
            ", maxKw=" + getMaxKw() +
            ", currentType='" + getCurrentType() + "'" +
            ", status='" + getStatus() + "'" +
            ", evse=" + getEvse() +
            "}";
    }
}
