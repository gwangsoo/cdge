package io.github.robwin.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
