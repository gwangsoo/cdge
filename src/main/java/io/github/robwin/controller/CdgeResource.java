package io.github.robwin.controller;

import io.github.robwin.service.CdgService;
import io.github.robwin.service.dto.AuthResultDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cdge")
@RequiredArgsConstructor
public class CdgeResource {

    private final CdgService cdgService;

    @PostMapping("/auth")
    @Schema(description = "api-key", required = true, defaultValue = "1234", example = "1234")
    public ResponseEntity<AuthResultDTO> auth(String apiKey, String email, String code) {
        return ResponseEntity
                .ok()
                .body(cdgService.auth(apiKey, email, code));
    }
}
