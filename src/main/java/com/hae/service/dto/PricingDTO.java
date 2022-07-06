package com.hae.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.hae.domain.Pricing} entity.
 */
@Schema(description = "요금정책")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPriceCents() {
        return priceCents;
    }

    public void setPriceCents(Double priceCents) {
        this.priceCents = priceCents;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getPriceCentsWithoutVat() {
        return priceCentsWithoutVat;
    }

    public void setPriceCentsWithoutVat(Double priceCentsWithoutVat) {
        this.priceCentsWithoutVat = priceCentsWithoutVat;
    }

    public Double getPriceCentsVat() {
        return priceCentsVat;
    }

    public void setPriceCentsVat(Double priceCentsVat) {
        this.priceCentsVat = priceCentsVat;
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
        if (!(o instanceof PricingDTO)) {
            return false;
        }

        PricingDTO pricingDTO = (PricingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pricingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PricingDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", priceCents=" + getPriceCents() +
            ", currency='" + getCurrency() + "'" +
            ", priceCentsWithoutVat=" + getPriceCentsWithoutVat() +
            ", priceCentsVat=" + getPriceCentsVat() +
            ", evse=" + getEvse() +
            "}";
    }
}
