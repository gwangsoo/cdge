package com.hae.service.dto;

import com.hae.domain.ChargerMeta;
import com.hae.domain.enumeration.Availability;
import com.hae.domain.enumeration.ChargerStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;


/**
 * A DTO for the {@link ChargerMeta} entity.
 */
@Schema(description = "고객 메타")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerMetaDTO implements Serializable {

    /**
     * 청구여부
     */
    @Schema(description = "청구여부")
    private Boolean billable;

    /**
     * 뉴스레터수신여부
     */
    @Schema(description = "뉴스레터수신여부")
    private String newsletter;

    /**
     * 광고수신여부
     */
    @Schema(description = "광고수신여부")
    private String wantAdvertisingNotifications;

    /**
     * failedLoginAttempts
     */
    @Schema(description = "로그인실패횟수")
    private Integer failedLoginAttempts;

    /**
     * createdAt
     */
    @Schema(description = "최초등록일시")
    private LocalDateTime createdAt;

    /**
     * lastModified
     */
    @Schema(description = "최근변경일시")
    private LocalDateTime lastModified;
}
