package com.hae.ecs.domain.charging.repository;

import com.hae.ecs.domain.charging.domain.Socket;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Socket entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SocketRepository extends JpaRepository<Socket, Long> {}
