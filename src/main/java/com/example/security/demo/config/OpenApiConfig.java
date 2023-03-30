package com.example.security.demo.config;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Demo API",
        version = "v0.0.1",
        license = @License(
            name = "Apache 2.0",
            url = "https://www.apache.org/licenses/LICENSE-2.0"
        ),
        description = "Demo sample application"
    ),
    externalDocs = @ExternalDocumentation(
        description = "Demo Wiki Documentation",
        url = "https://springshop.wiki.github.org/docs"
    )
)
@SecurityScheme(
    name = "JWT",
    type = SecuritySchemeType.HTTP,
    in = SecuritySchemeIn.HEADER,
    bearerFormat = "JWT",
    scheme = "bearer"
)
public class OpenApiConfig {

}
