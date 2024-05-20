package io.github.yuokada.rest.annotations;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;

@SupportedAnnotationTypes("CustomAPIResponse")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class CustomAPIResponseProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(CustomAPIResponse.class)) {
            if (element instanceof ExecutableElement) {
                ExecutableElement method = (ExecutableElement) element;
                CustomAPIResponse annotation = method.getAnnotation(CustomAPIResponse.class);
                String responseCode = annotation.responseCode();
                String description = annotation.description();
                String mediaType = annotation.mediaType();
                SchemaType schemaType = SchemaType.OBJECT;
                Class<?> implementation = annotation.implementation();

                // Generate @APIResponse annotation based on CustomAPIResponse annotation
                StringBuilder annotationBuilder = new StringBuilder("@APIResponse(");
                annotationBuilder.append("responseCode = \"").append(responseCode).append("\", ");
                annotationBuilder.append("description = \"").append(description).append("\", ");
                annotationBuilder.append("content = @Content(");
                annotationBuilder.append("mediaType = \"").append(mediaType).append("\", ");
                annotationBuilder.append("schema = @Schema(");
                annotationBuilder.append("type = SchemaType.").append(schemaType).append(", ");
                annotationBuilder.append("implementation = ").append(implementation.getName()).append(".class");
                annotationBuilder.append(")");
                annotationBuilder.append(")");
                annotationBuilder.append(")");

                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Generated annotation: " + annotationBuilder.toString());
            }
        }
        return true;
    }
}
