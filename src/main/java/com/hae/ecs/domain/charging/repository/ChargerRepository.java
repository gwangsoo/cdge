package com.hae.ecs.domain.charging.repository;

import com.hae.ecs.domain.charging.domain.Charger;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Charger entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChargerRepository extends JpaRepository<Charger, Long> {}
