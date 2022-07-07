package com.hae.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 충전기
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_cdge_evse")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Evse extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 그룹명
     */
    @Size(max = 128)
    @Column(name = "group_name", length = 128)
    private String groupName;

    /**
     * 사용가능여부 Whether this EVSE is available for usage
     */
    @NotNull
    @Column(name = "available", nullable = false)
    private Boolean available;

    /**
     * 예약가능여부 Whether this EVSE is available for reservation
     */
    @NotNull
    @Column(name = "reservable", nullable = false)
    private Boolean reservable;

    /**
     * 일시불여부 Currently GET stations/{id} uses this field. Won't be added in that endpoint for Hubject stations.
     */
    @NotNull
    @Column(name = "one_time_payment", nullable = false)
    private Boolean oneTimePayment;

    /**
     * 상태
     */
    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @OneToMany(mappedBy = "evseId")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Connector> connectors;

    @ElementCollection @CollectionTable(name="tb_cdge_evse_pricing", joinColumns = @JoinColumn(name = "evse_id"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Pricing> pricings;

    /**
     * 충전소ID
     */
    @Column(name = "station_id")
    private Long stationId;
}
