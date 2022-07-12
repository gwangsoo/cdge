package io.github.robwin.service;

import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.feign.FeignDecorators;
import io.github.resilience4j.feign.Resilience4jFeign;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.robwin.service.dto.AuthRequestDTO;
import io.github.robwin.service.dto.AuthResultDTO;
import io.github.robwin.util.JsonUtil;
import org.springframework.stereotype.Service;

@Service
public class CdgService {

    private final CdgeClient cdgeClient;

    public CdgService() {
//        CdgeClient requestFailedFallback = () -> "fallback greeting";
//        CdgeClient circuitBreakerFallback = () -> "CircuitBreaker is open!";

        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("cdgeClient");
        RateLimiter rateLimiter = RateLimiter.ofDefaults("cdgeClient");
        FeignDecorators decorators = FeignDecorators.builder()
                .withCircuitBreaker(circuitBreaker)
                .withRateLimiter(rateLimiter)
//                .withFallback()
                .build();

        cdgeClient = Resilience4jFeign.builder(decorators)
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(CdgeClient.class, "https://api.test.virta.global/customer");
    }

    public AuthResultDTO auth(String apiKey, AuthRequestDTO authRequestDTO) {
        return cdgeClient.auth(apiKey, authRequestDTO)
                .block();
    }
}
