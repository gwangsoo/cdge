package io.github.robwin.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@RequiredArgsConstructor
public class OpenApiConfiguration {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("Resilience4j-demo")
                .apiInfo(new ApiInfoBuilder()
                        .title("Resilience4j-demo APIs")
                        .description("스마트오피스 API")
                        .version("v1")
                        .build())
                .useDefaultResponseMessages(false)
//                .ignoredParameterTypes(Pageable.class)
//                .globalRequestParameters(
//                        singletonList(new RequestParameterBuilder()
//                                .name("SESSION")
//                                .description("Please input session id")
//                                .in(ParameterType.COOKIE)
//                                .required(false)
//                                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
//                                .build()))
                .select()
//                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("io.github.robwin.controller"))
                .paths(PathSelectors.any())
                .build()
                ;
    }

//    @Bean
//    public Docket apiFirstDocket() {
//        Contact contact = new Contact("", "http://www.atensys.co.kr/", "atensystem@atensys.co.kr");
//
//        ApiInfo apiInfo = new ApiInfo(
//            "API First " + properties.getTitle(),
//            properties.getDescription(),
//            properties.getVersion(),
//            properties.getTermsOfServiceUrl(),
//            contact,
//            properties.getLicense(),
//            properties.getLicenseUrl(),
//            new ArrayList<>()
//        );
//
//        return new Docket(DocumentationType.OAS_30)
//            .groupName("openapi")
//            .host(properties.getHost())
//            .protocols(new HashSet<>(Arrays.asList(properties.getProtocols())))
//            .apiInfo(apiInfo)
//            .useDefaultResponseMessages(properties.isUseDefaultResponseMessages())
//            .forCodeGeneration(true)
//            .directModelSubstitute(ByteBuffer.class, String.class)
//            .genericModelSubstitutes(ResponseEntity.class)
//            .ignoredParameterTypes(Pageable.class)
//            .select()
//            .apis(RequestHandlerSelectors.basePackage("com.atensys.room.web.api"))
//            .paths(regex(properties.getDefaultIncludePattern()))
//            .build();
//    }
}
