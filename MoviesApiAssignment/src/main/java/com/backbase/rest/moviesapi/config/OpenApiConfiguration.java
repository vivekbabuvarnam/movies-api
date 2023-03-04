package com.backbase.rest.moviesapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
                info =@Info(
                                title = "Movies API",
                                version = "1.0.0",
                                contact = @Contact(
                                                name = "vivek", email = "vivekbabu.vvv@gmail.com"
                                ),
                                license = @License(
                                                name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"
                                ),
                                description = "This gives information about a Movie. " +
                                                "You can give rating to a movie " +
                                                "You Can View Top 10 rated movie sorted on box office" +
                                                "This API is projected by JWT OAuth2"

                ),
                servers = @Server(
                                url = "http://localhost:8080",
                                description = "Production"
                )
)
@SecurityScheme(
                name = "Bearer Authentication",
                type = SecuritySchemeType.HTTP,
                bearerFormat = "JWT",
                scheme = "bearer"
)
public class OpenApiConfiguration {
}
