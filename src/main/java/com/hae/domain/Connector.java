package com.hae.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hae.domain.enumeration.ConnectorType;
import com.hae.domain.enumeration.CurrentType;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 콘넥터
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_cdge_connector")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Connector extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * connectorID
     */
    @NotNull
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 콘넥터 유형 (Mennekes)
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ConnectorType type;

    /**
     * 최대소비전력량 (18)
     */
    @DecimalMin(value = "0")
    @Column(name = "max_kwh")
    private Double maxKwh;

    /**
     * 최대소비전력 (18)
     */
    @DecimalMin(value = "0")
    @Column(name = "max_kw")
    private Double maxKw;

    /**
     * 전류유형 (AC)
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "current_type", nullable = false)
    private CurrentType currentType;

    /**
     * 상태
     */
    @Column(name = "status")
    private String status;

    /**
     * Evse ID
     */
    @Column(name = "evse_id")
    private Long evseId;
}
