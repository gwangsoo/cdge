package com.hae.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO for the {@link com.hae.domain.Socket} entity.
 */
@Schema(description = "Socket")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocketDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private MetaDTO meta;

}
