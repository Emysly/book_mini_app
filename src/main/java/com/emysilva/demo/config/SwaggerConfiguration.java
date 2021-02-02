package com.emysilva.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public Docket swaggerConfig()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(CurrencyConverterApiDetails())
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .paths(PathSelectors.ant("/api/**")) //restrict base on the url of this application
                .apis(RequestHandlerSelectors.basePackage("com.emysilva"))  //restrict base on the package name
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private ApiInfo CurrencyConverterApiDetails()
    {
        return new ApiInfo(
                "Book Api",
                "A simple book api to demonstrate the role base authorization",
                "1.0",
                "book platform",
                new springfox.documentation.service.Contact("book", "https://decagonhq.com/", "info@book.com"),
                "Api Licence",
                "https://decagonhq.com/",
                Collections.emptyList()
        );
    }
}