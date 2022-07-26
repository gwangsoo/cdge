package com.hae.ecs.domain.charging.repository;

import com.hae.ecs.domain.charging.domain.Pricing;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Pricing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PricingRepository extends JpaRepository<Pricing, Long> {}
