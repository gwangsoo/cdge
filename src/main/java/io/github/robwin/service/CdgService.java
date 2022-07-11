package io.github.robwin.service;

import feign.FeignException;
import feign.Param;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.feign.FeignDecorators;
import io.github.resilience4j.feign.Resilience4jFeign;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.robwin.service.dto.AuthRequestDTO;
import io.github.robwin.service.dto.AuthResultDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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
        cdgeClient = Resilience4jFeign.builder(decorators).target(CdgeClient.class, "https://api.test.virta.global/customer");
    }

    public AuthResultDTO auth(String apiKey, String email, String code) {
        return cdgeClient.auth(apiKey, AuthRequestDTO.builder().email(email).code(code).build())
                .block();
    }
}
