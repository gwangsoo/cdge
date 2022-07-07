package com.hae.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 고객메타정보
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomerMeta implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 청구여부
     */
    @Column(name = "billable")
    private Boolean billable;

    /**
     * 뉴스레터수신여부
     */
    @Column(name = "newsletter")
    private Boolean newsletter;

    /**
     * 광고수신여부
     */
    @Column(name = "want_advertising_notifications")
    private Boolean wantAdvertisingNotifications;

    /**
     * 로그인실패횟수
     */
    @Column(name = "failed_login_attempts")
    private Integer failedLoginAttempts;

    /**
     * createdAt
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * lastModified
     */
    @Column(name = "last_modified")
    private LocalDateTime lastModified;
}
