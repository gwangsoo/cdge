package com.hae.ecs.domain.charging.repository;

import com.hae.ecs.domain.charging.domain.Connector;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Connector entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConnectorRepository extends JpaRepository<Connector, Long> {}
