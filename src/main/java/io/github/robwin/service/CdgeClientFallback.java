package io.github.robwin.service;

import io.github.robwin.service.dto.AuthRequestDTO;
import io.github.robwin.service.dto.AuthResultDTO;
import io.github.robwin.service.dto.StationDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CdgeClientFallback implements CdgeClient {
    @Override
    public Mono<AuthResultDTO> auth(String apiKey, AuthRequestDTO authRequestDTO) {
        return null;
    }

    @Override
    public Mono<AuthResultDTO> authRefresh(String apiKey, String accessToken) {
        return null;
    }

    @Override
    public Flux<StationDTO> stations(String apiKey, String accessToken, Integer from, Integer includePoi, String lastUpdate, Double latMax, Double latMin, Integer limit, Double longMax, Double longMin, Integer privilegedStations) {
        return null;
    }
}
