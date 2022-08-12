package com.hae.ecs.domain.charging.repository;

import com.hae.ecs.domain.charging.domain.ChargingFavoritesSpot;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ChargingFavoritesSpot entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChargingFavoritesSpotRepository extends JpaRepository<ChargingFavoritesSpot, Long> {}
