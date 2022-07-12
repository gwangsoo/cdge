package io.github.robwin.service;

import feign.FeignException;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.feign.FeignDecorators;
import io.github.resilience4j.feign.Resilience4jFeign;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.robwin.service.dto.AuthRequestDTO;
import io.github.robwin.service.dto.AuthResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CdgService {
    private static Logger LOG = LoggerFactory.getLogger(CdgService.class);

    private CdgeClient cdgeClient;

    private final String cdgeUri;

    private final String apiKey;

    private final String name;

    public CdgService(@Value("${charging.cpo.cdge.uri}") String cdgeUri, @Value("${charging.cpo.cdge.api-key}") String apiKey, @Value("${charging.cpo.cdge.name}") String name) {
        this.cdgeUri = cdgeUri;
        this.apiKey = apiKey;
        this.name = name;

        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults(name);
        RateLimiter rateLimiter = RateLimiter.ofDefaults(name);
        FeignDecorators decorators = FeignDecorators.builder()
                .withCircuitBreaker(circuitBreaker)
                .withRateLimiter(rateLimiter)
                .build();

        this.cdgeClient = Resilience4jFeign.builder(decorators)
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(CdgeClient.class, cdgeUri);
    }

    public AuthResultDTO auth(String apiKey, AuthRequestDTO authRequestDTO) {
        try {
            return cdgeClient.auth(apiKey, authRequestDTO);
        }
        // 관련 에러처리는 여기에
        catch (FeignException err) {
            LOG.error(err.getMessage());
        }
        return null;
    }
}
