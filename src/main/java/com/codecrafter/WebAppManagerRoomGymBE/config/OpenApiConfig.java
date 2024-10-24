package com.codecrafter.WebAppManagerRoomGymBE.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "ALL API OF GYM SERVICE",
                description = "API DOCUMENT FOR GYM SERVICE",
                version = "1.0",
                contact = @Contact(
                        name = "Nguyễn Quang Huy",
                        email = "huynq201104@gmail.com",
                        url = "huynq201104@gmail.com"
                ),
                license = @License(
                        name = "mit Lisence",
                        url=""
                ),
                termsOfService = "f"

        ),
        servers = @Server(
                description = "Local Env",
                url = "http://localhost:8085"
        )



)
public class OpenApiConfig {

}
