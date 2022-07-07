package com.hae.service.dto;

import com.hae.domain.Address;
import com.hae.domain.BrandedContent;
import com.hae.domain.CustomerMeta;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A DTO for the {@link com.hae.domain.Charger} entity.
 */
@Schema(description = "고객")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull
    private Long id;

    /**
     * 고객명
     */
    @Size(max = 64)
    @Schema(description = "고객명")
    private String name;

    /**
     * company
     */
    @Size(max = 64)
    @Schema(description = "company")
    private String company;

    /**
     * 휴대전화번호
     */
    @Size(max = 32)
    @Schema(description = "휴대전화번호")
    private String mobile;

    /**
     * email
     */
    @Size(max = 128)
    @Schema(description = "이메일")
    private String email;

    @Schema(description = "balanceCents")
    private Integer balanceCents;

    /**
     * planName
     */
    @Size(max = 128)
    @Schema(description = "planName")
    private String planName;

    /**
     * currency
     */
    @Size(max = 16)
    @Schema(description = "통화")
    private String currency;

    private AddressDTO address;

    private CustomerMetaDTO meta;

    /**
     * nameFirst
     */
    @Size(max = 128)
    @Schema(description = "이름")
    private String nameFirst;

    /**
     * nameLast
     */
    @Size(max = 128)
    @Schema(description = "성")
    private String nameLast;

    private BrandedContentDTO brandedContent;
}
