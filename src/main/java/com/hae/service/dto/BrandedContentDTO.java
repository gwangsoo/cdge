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
@Schema(description = "BrandedContent")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrandedContentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * logo
     */
    @Size(max = 256)
    private String logo;

    /**
     * color
     */
    @Size(max = 256)
    private String color;

    /**
     * brightElements
     */
    @Size(max = 256)
    private String brightElements;
}
