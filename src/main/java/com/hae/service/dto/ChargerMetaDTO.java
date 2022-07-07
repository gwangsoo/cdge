package com.hae.service.dto;

import com.hae.domain.ChargerMeta;
import com.hae.domain.enumeration.Availability;
import com.hae.domain.enumeration.ChargerStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * A DTO for the {@link ChargerMeta} entity.
 */
@Schema(description = "충전기 메타")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChargerMetaDTO implements Serializable {

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

    private Set<String> sockets;
}
