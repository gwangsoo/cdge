package io.github.robwin.service;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import io.github.robwin.service.dto.AuthRequestDTO;
import io.github.robwin.service.dto.AuthResultDTO;
import io.github.robwin.service.dto.StationDTO;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing CDGE
 */
@Headers({"x-api-key: {apiKey}", "Content-Type: application/json"})
public interface CdgeClient {
    @RequestLine("POST /auth")
    Mono<AuthResultDTO> auth(@Param("apiKey") String apiKey, @RequestBody AuthRequestDTO authRequestDTO);

    @RequestLine("GET /auth/refresh")
    @Headers("Authorization: Bearer {accessToken}")
    Mono<AuthResultDTO> authRefresh(@Param("apiKey") String apiKey, @Param("accessToken") String accessToken);

    @RequestLine("GET /stations?from={from}&includePoi={includePoi}&lastUpdate={lastUpdate}&latMax={latMax}&latMin={latMin}&limit={limit}&longMax={longMax}&longMin={longMin}&privilegedStations={privilegedStations}")
    @Headers("Authorization: Bearer {accessToken}")
    Flux<StationDTO> stations(@Param("apiKey") String apiKey, @Param("accessToken") String accessToken
            , @Param("from") Integer from, @Param("includePoi")Integer includePoi, @Param("lastUpdate")String lastUpdate
            , @Param("latMax")Double latMax, @Param("latMin")Double latMin, @Param("limit")Integer limit
            , @Param("longMax")Double longMax, @Param("longMin")Double longMin, @Param("privilegedStations")Integer privilegedStations);
}
