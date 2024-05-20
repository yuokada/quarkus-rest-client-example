package io.github.yuokada.rest.annotations;

import io.github.yuokada.rest.service.ErrorMessage;
import io.github.yuokada.rest.service.Team;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@APIResponse(
    name = "AuthErrorResponse",
    responseCode = "400",
    description = "Returns an error response when authentication fails",
    content = @Content(
        mediaType = "application/json",
        schema = @Schema(type = SchemaType.OBJECT, implementation = ErrorMessage.class)
    )
)
@Inherited
public @interface AuthErrorResponse {
}
