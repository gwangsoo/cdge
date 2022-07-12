package io.github.robwin.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResultDTO implements Serializable {
    private String token;
    private String accessToken;
    private String tokenType;
    private Integer expires_in; // seconds
}
