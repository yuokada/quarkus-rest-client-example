package io.github.yuokada.rest.filter;

import io.quarkus.vertx.http.runtime.filters.Filters;
import io.vertx.ext.web.RoutingContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

/**
 * A simple filter that adds a header to the response.
 */
@ApplicationScoped
public class DynamicFilter {

    @Inject
    Logger logger;

    @ConfigProperty(name = "custom.private.filter.enabled", defaultValue = "false")
    boolean enabled;

    // @RouteFilter(value = Priorities.USER)
    private static void myFilter(RoutingContext rc, Logger logger, boolean enabled) {
        if (enabled) {
            logger.info(String.format("%s is enabled!", DynamicFilter.class.getSimpleName()));
            rc.response().putHeader("X-Vertex-Header", "Vertex header");
        } else {
            logger.info(String.format("%s is disabled!", DynamicFilter.class.getSimpleName()));
        }
        rc.next();
    }

    public void register(@Observes Filters filters) {
        filters.register(rc -> {
            myFilter(rc, logger, enabled);
        }, Priorities.USER + 100);

        logger.info(String.format("%s is registered", DynamicFilter.class.getName()));
    }
}