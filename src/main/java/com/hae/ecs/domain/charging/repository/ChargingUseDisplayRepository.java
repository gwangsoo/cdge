package com.hae.ecs.domain.charging.repository;

import com.hae.ecs.domain.charging.domain.ChargingUseDisplay;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ChargingUseDisplay entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChargingUseDisplayRepository extends JpaRepository<ChargingUseDisplay, Long> {}
