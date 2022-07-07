package com.hae.service.dto;

import com.hae.domain.Charger;
import com.hae.domain.Evse;
import com.hae.domain.enumeration.Provider;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO for the {@link com.hae.domain.Station} entity.
 */
@Schema(description = "충전소")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StationDTO implements Serializable {

    /**
     * id
     */
    @NotNull
    @Schema(description = "id", required = true)
    private Long id;

    /**
     * 판매자ID
     */
    @Schema(description = "판매자ID")
    private Integer sellerId;

    /**
     * 충전소명
     */
    @Size(max = 128)
    @Schema(description = "충전소명")
    private String name;

    /**
     * 위도
     */
    @Schema(description = "위도")
    private Double latitude;

    /**
     * 경도
     */
    @Schema(description = "경도")
    private Double longitide;

    /**
     * icon ID
     */
    @Schema(description = "icon ID")
    private Integer icon;

    /**
     * 주소
     */
    @Size(max = 256)
    @Schema(description = "주소")
    private String address;

    /**
     * 도시
     */
    @Size(max = 128)
    @Schema(description = "도시")
    private String city;

    /**
     * 운영시간
     */
    @Size(max = 64)
    @Schema(description = "운영시간")
    private String openHours;

    /**
     * 제공자
     */
    @Schema(description = "제공자")
    private Provider provider;

    /**
     * 알림메세지
     */
    @Size(max = 256)
    @Schema(description = "알림메세지")
    private String alertMessage;

    /**
     * 이동여부
     */
    @Schema(description = "이동여부")
    private Boolean isRemoved;

    /**
     * 사적이용여부
     */
    @Schema(description = "사적이용여부")
    private Boolean isPrivate;

    private Set<EvseDTO> evses;

    private Set<ChargerDTO> chargers;

    private Set<String> pictures;
}
