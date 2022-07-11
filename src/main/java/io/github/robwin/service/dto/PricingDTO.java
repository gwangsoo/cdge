package io.github.robwin.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

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

    @JsonIgnore
    private Long evseId;
}
