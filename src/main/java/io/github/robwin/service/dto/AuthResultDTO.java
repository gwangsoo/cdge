package io.github.robwin.service.dto;

import lombok.Data;

@Data
public class AuthResultDTO {
    private String token;
    private String accessToken;
    private String tokenType;
    private Integer expires_in; // seconds
}
