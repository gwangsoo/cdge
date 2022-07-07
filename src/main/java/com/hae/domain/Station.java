package com.hae.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hae.domain.enumeration.Provider;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 충전소
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_cdge_station")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Station extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 판매자ID
     */
    @Column(name = "seller_id")
    private Integer sellerId;

    /**
     * 충전소명
     */
    @Size(max = 128)
    @Column(name = "name", length = 128)
    private String name;

    /**
     * 위도
     */
    @Column(name = "latitude")
    private Double latitude;

    /**
     * 경도
     */
    @Column(name = "longitide")
    private Double longitide;

    /**
     * icon ID
     */
    @Column(name = "icon")
    private Integer icon;

    /**
     * 주소
     */
    @Size(max = 256)
    @Column(name = "address", length = 256)
    private String address;

    /**
     * 도시
     */
    @Size(max = 128)
    @Column(name = "city", length = 128)
    private String city;

    /**
     * 운영시간
     */
    @Size(max = 64)
    @Column(name = "open_hours", length = 64)
    private String openHours;

    /**
     * 제공자
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "provider")
    private Provider provider;

    /**
     * 알림메세지
     */
    @Size(max = 256)
    @Column(name = "alert_message", length = 256)
    private String alertMessage;

    /**
     * 이동여부
     */
    @Column(name = "is_removed")
    private Boolean isRemoved;

    /**
     * 사적이용여부
     */
    @Column(name = "is_private")
    private Boolean isPrivate;

    @OneToMany(mappedBy = "stationId")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Evse> evses;

    @OneToMany(mappedBy = "stationId")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Charger> chargers;

    @ElementCollection @CollectionTable(name="tb_cdge_station_picture", joinColumns = @JoinColumn(name = "station_id"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<String> pictures;

}
