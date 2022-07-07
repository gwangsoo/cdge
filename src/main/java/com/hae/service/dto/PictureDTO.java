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
 * A DTO for the {@link com.hae.domain.Picture} entity.
 */
@Schema(description = "Picture")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PictureDTO implements Serializable {

    private Long id;

    @Size(max = 256)
    private String context;

    private StationDTO station;

}
