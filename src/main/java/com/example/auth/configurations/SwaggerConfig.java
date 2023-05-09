package com.example.auth.configurations;

import com.example.auth.AuthApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
@Profile({ "!prod" })
public class SwaggerConfig {

    @Value("${jwt.header}")
    private String jwtHeader;

    private final BuildProperties buildProperties;

    public SwaggerConfig(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .forCodeGeneration(true)
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage(AuthApplication.class.getPackage().getName() + ".controllers"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Collections.singletonList(apiKey()))
                .securityContexts(Collections.singletonList(securityContext()))
                .apiInfo(apiInfo()).tags(
                        new Tag("Authentication API", "Operations for Authentication"),
                        new Tag("Users API", "Read operations for Users"),
                        new Tag("Movies API", "Read operations for Movies"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("API Authentication")
                .description("API reference for developers")
                .termsOfServiceUrl("http://smartcampus.uniajc.edu.co/")
                .license("MIT License")
                .contact(new Contact("Smart Campus UNIAJC", "http://smartcampus.uniajc.edu.co/",
                        "smartcampus@gmail.com"))
                .version(buildProperties.getVersion())
                .build();
    }

    @Bean
    public SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .operationSelector(operationContext -> true)
                .build();
    }

    public List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = { authorizationScope };
        return Collections.singletonList(new SecurityReference(jwtHeader, authorizationScopes));
    }

    private ApiKey apiKey() {
        return new ApiKey(jwtHeader, "JWT", "header");
    }
}
