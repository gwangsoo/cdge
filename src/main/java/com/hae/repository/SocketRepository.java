package com.hae.repository;

import com.hae.domain.Socket;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Socket entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SocketRepository extends JpaRepository<Socket, Long> {}
