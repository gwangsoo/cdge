package io.github.robwin.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.robwin.service.enumeration.ConnectorType;
import io.github.robwin.service.enumeration.CurrentType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Schema(description = "콘넥터")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConnectorDTO implements Serializable {

    /**
     * connectorID
     */
    @NotNull
    @Schema(description = "connectorID", required = true)
    @JsonProperty("connectorID")
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

    @JsonIgnore
    private Long evseId;
}
