package com.hae.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Embeddable;
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
public class BrandedContent implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * logo
     */
    @Size(max = 256)
    @Column(name = "logo", length = 256)
    private String logo;

    /**
     * color
     */
    @Size(max = 256)
    @Column(name = "color", length = 256)
    private String color;

    /**
     * brightElements
     */
    @Size(max = 256)
    @Column(name = "bright_elements", length = 256)
    private String brightElements;
}
