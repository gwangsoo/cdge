package com.hae.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
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
@Table(name = "tb_cdge_charger")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Charger extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @JsonIgnoreProperties(value = { "sockets" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Meta meta;

    @ManyToOne
    @JsonIgnoreProperties(value = { "evses", "chargers", "pictures" }, allowSetters = true)
    private Station station;

}
