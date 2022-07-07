package com.hae.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO for the {@link com.hae.domain.Charger} entity.
 */
@Schema(description = "충전기")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChargerDTO implements Serializable {

    private Long id;

    private ChargerMetaDTO meta;

    @JsonIgnore
    private Long stationId;
}
