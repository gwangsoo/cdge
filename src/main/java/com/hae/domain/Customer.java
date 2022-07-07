package com.hae.domain;

import com.hae.domain.enumeration.Provider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 * 고객
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_cdge_customer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customer extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 고객명
     */
    @Size(max = 128)
    @Column(name = "name", length = 64)
    private String name;

    /**
     * company
     */
    @Size(max = 64)
    @Column(name = "company", length = 64)
    private String company;

    /**
     * 휴대전화번호
     */
    @Size(max = 32)
    @Column(name = "mobile", length = 32)
    private String mobile;

    /**
     * email
     */
    @Size(max = 128)
    @Column(name = "email", length = 128)
    private String email;

    @Column(name = "balance_cents", nullable = false)
    private Integer balanceCents;

    /**
     * planName
     */
    @Size(max = 128)
    @Column(name = "plan_name", length = 128)
    private String planName;

    /**
     * currency
     */
    @Size(max = 16)
    @Column(name = "currency", length = 16)
    private String currency;

    @Embedded
    private Address address;

    @Embedded
    private CustomerMeta meta;

    /**
     * nameFirst
     */
    @Size(max = 128)
    @Column(name = "name_first", length = 128)
    private String nameFirst;

    /**
     * nameLast
     */
    @Size(max = 128)
    @Column(name = "name_last", length = 128)
    private String nameLast;

    @Embedded
    private BrandedContent brandedContent;
}
