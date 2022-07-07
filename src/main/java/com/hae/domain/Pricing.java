package com.hae.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 요금정책
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Pricing implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Translation key for the price name
     */
    @NotNull
    @Size(max = 128)
    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "price_cents", nullable = false)
    private Double priceCents;

    @NotNull
    @Size(max = 3)
    @Column(name = "currency", length = 3, nullable = false)
    private String currency;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "price_cents_without_vat", nullable = false)
    private Double priceCentsWithoutVat;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "price_cents_vat", nullable = false)
    private Double priceCentsVat;
}
