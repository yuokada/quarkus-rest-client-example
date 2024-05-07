package io.github.yuokada.rest.filter;

import io.quarkus.vertx.http.runtime.filters.Filters;
import io.quarkus.vertx.web.RouteFilter;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.HttpException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

@ApplicationScoped
public class RandomResponseFilter {

    @Inject
    Logger logger;

    private static void practiceFilter(RoutingContext rc, Logger logger) {
        logger.info("Filter is Called!");
        HttpServerResponse response = rc.response();
        response.putHeader("X-Super-Header", "intercepting the request");
        HttpServerRequest request = rc.request();
        if (request.headers().contains("X-Super-Header")) {
            logger.info("Start stopping propagation");
            rc.response().setStatusCode(400);
            rc.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            rc.end("{\"message\": \"Stopping propagation\"}");
            rc.fail(400, new HttpException(400, "invalid request"));
            throw new HttpException(400, "invalid request");
        } else {
            rc.next();
        }
    }

    public void register(@Observes Filters filters) {
        filters.register(rc -> {
            practiceFilter(rc, logger);
        }, 1000);
        logger.info(String.format("%s is registered", RandomResponseFilter.class.getName()));
    }

}
