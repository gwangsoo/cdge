package com.hae.ecs.domain.charging.repository;

import com.hae.ecs.domain.charging.domain.Station;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Station entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StationRepository extends JpaRepository<Station, Long> {}
