//package com.hae.service.dto;
//
//import com.hae.config.Constants;
//import com.hae.domain.Authority;
//import com.hae.domain.User;
//
//
//import java.time.Instant;
//import java.util.Set;
//import java.util.stream.Collectors;
//import javax.validation.constraints.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
///**
// * A DTO representing a user, with his authorities.
// */
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class AdminUserDTO {
//
//    private Long id;
//
//    @NotBlank
//    @Pattern(regexp = Constants.LOGIN_REGEX)
//    @Size(min = 1, max = 50)
//    private String login;
//
//    @Size(max = 50)
//    private String firstName;
//
//    @Size(max = 50)
//    private String lastName;
//
//    @Email
//    @Size(min = 5, max = 254)
//    private String email;
//
//    @Size(max = 256)
//    private String imageUrl;
//
//    private boolean activated = false;
//
//    @Size(min = 2, max = 10)
//    private String langKey;
//
//    private String createdBy;
//
//    private Instant createdDate;
//
//    private String lastModifiedBy;
//
//    private Instant lastModifiedDate;
//
//    private Set<String> authorities;
//
//}
