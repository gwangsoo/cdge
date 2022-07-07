package com.hae.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A DTO for the {@link com.hae.domain.Charger} entity.
 */
@Schema(description = "주소")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * street
     */
    @Size(max = 256)
    private String street;

    /**
     * city
     */
    @Size(max = 64)
    private String city;

    /**
     * country
     */
    @Size(max = 32)
    private String country;

    /**
     * moreDetails
     */
    @Size(max = 256)
    private String moreDetails;

    /**
     * zipcodeParsed
     */
    @Size(max = 32)
    private String zipcodeParsed;

    /**
     * zipcodeParsed
     */
    @Size(max = 32)
    private String cityParsed;

    /**
     * zipcodeParsed
     */
    @Size(max = 16)
    private String zipcode;
}
