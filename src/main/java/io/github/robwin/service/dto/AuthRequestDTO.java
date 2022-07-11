package io.github.robwin.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthRequestDTO {
    @Schema(description = "이메일", required = true, defaultValue = "test", example = "test")
    private String email;
    @Schema(description = "비밀번호", required = true, defaultValue = "password", example = "password")
    private String code;
}
