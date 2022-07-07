package com.hae.domain;

import com.hae.domain.enumeration.Availability;
import com.hae.domain.enumeration.ChargerStatus;
import java.io.Serializable;
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
@Embeddable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ChargerMeta implements Serializable {

    private static final long serialVersionUID = 1L;

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

    @ElementCollection @CollectionTable(name="tb_cdge_meta_socket", joinColumns = @JoinColumn(name = "charger_id"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<String> sockets;
}
