package com.hae.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 주소
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * street
     */
    @Size(max = 256)
    @Column(name = "street", length = 256)
    private String street;

    /**
     * city
     */
    @Size(max = 64)
    @Column(name = "city", length = 64)
    private String city;

    /**
     * country
     */
    @Size(max = 32)
    @Column(name = "country", length = 32)
    private String country;

    /**
     * moreDetails
     */
    @Size(max = 256)
    @Column(name = "more_details", length = 256)
    private String moreDetails;

    /**
     * zipcodeParsed
     */
    @Size(max = 32)
    @Column(name = "zipcode_parsed", length = 32)
    private String zipcodeParsed;

    /**
     * zipcodeParsed
     */
    @Size(max = 32)
    @Column(name = "city_parsed", length = 32)
    private String cityParsed;

    /**
     * zipcodeParsed
     */
    @Size(max = 16)
    @Column(name = "zipcode", length = 16)
    private String zipcode;

}
