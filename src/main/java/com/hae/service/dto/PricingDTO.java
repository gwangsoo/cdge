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
 * A DTO for the {@link com.hae.domain.Pricing} entity.
 */
@Schema(description = "요금정책")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PricingDTO implements Serializable {

    private Long id;

    /**
     * Translation key for the price name
     */
    @NotNull
    @Size(max = 128)
    @Schema(description = "Translation key for the price name", required = true)
    private String name;

    @NotNull
    @DecimalMin(value = "0")
    private Double priceCents;

    @NotNull
    @Size(max = 3)
    private String currency;

    @NotNull
    @DecimalMin(value = "0")
    private Double priceCentsWithoutVat;

    @NotNull
    @DecimalMin(value = "0")
    private Double priceCentsVat;

    private EvseDTO evse;

}
