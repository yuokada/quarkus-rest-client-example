package io.github.yuokada.rest;

import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@OpenAPIDefinition(
    info = @Info(
        title = "BMS API Endpoints",
        version = "0.1.0",
        description = "Here is description of BMS API.",
        license = @org.eclipse.microprofile.openapi.annotations.info.License(
            name = "MIT License",
            url = "https://opensource.org/licenses/MIT"
        ),
        contact = @org.eclipse.microprofile.openapi.annotations.info.Contact(
            name = "BMS administrator",
            email = "contact@email.example.com"
        )
    ),
    // Or setting quarkus.smallrye-openapi.auto-add-server to true in application.properties
    servers = {
        @Server(url = "http://localhost:8888", description = "Local server"),
        @Server(url = "http://192.168.0.119:8888", description = "A remote server")
    },
    tags = {
        @Tag(name = "BMS basic API", description = "BMS Common APIs for authenticated users"),
        @Tag(name = "BMS guest API", description = "API endpoints for guest users")
    }
)
public class BMSApplication extends Application {

}
