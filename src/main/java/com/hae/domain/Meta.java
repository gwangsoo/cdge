package com.hae.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hae.domain.enumeration.Availability;
import com.hae.domain.enumeration.ChargerStatus;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Meta정보
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_cdge_meta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Meta extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * Whether the station is currently in active use or not
     */
    @Column(name = "active")
    private Boolean active;

    /**
     * Who are available to see it and use it
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "availability")
    private Availability availability;

    /**
     * State the station is currently
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ChargerStatus status;

    @OneToMany(mappedBy = "meta")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "meta" }, allowSetters = true)
    private Set<Socket> sockets;

}
