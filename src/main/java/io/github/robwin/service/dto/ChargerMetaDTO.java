package io.github.robwin.service.dto;

import io.github.robwin.service.enumeration.Availability;
import io.github.robwin.service.enumeration.ChargerStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

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
