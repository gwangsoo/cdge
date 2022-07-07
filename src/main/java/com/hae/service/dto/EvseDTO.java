package com.hae.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO for the {@link com.hae.domain.Evse} entity.
 */
@Schema(description = "충전기")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvseDTO implements Serializable {

    /**
     * id
     */
    @NotNull
    @Schema(description = "id", required = true)
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

}
