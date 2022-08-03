package com.hae.ecs.domain.charging.repository;

import com.hae.ecs.domain.charging.domain.ChargingUseHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ChargingUseHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChargingUseHistoryRepository extends JpaRepository<ChargingUseHistory, Long> {}
