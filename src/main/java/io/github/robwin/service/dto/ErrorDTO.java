package io.github.robwin.service.dto;

import lombok.Data;

@Data
public class ErrorDTO {
    private Integer statusCode;
    private String message;
}
