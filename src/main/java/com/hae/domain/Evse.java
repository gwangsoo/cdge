package com.hae.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 충전기
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 그룹명
     */
    @Size(max = 256)
    @Column(name = "group_name", length = 256)
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

    @OneToMany(mappedBy = "evse")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "evse" }, allowSetters = true)
    private Set<Connector> connectors;

    @OneToMany(mappedBy = "evse")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "evse" }, allowSetters = true)
    private Set<Pricing> pricings;

    @ManyToOne
    @JsonIgnoreProperties(value = { "evses", "chargers", "pictures" }, allowSetters = true)
    private Station station;

}
