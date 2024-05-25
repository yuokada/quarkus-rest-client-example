package io.github.yuokada.rest.filter;

import io.quarkus.vertx.web.RouteFilter;
import io.vertx.ext.web.RoutingContext;

/**
 * A simple filter that adds a header to the response.
 * This filter is an example.
 */
public class VertexFilter {

    @RouteFilter(value = 10000)
    void myFilter(RoutingContext rc) {
        rc.response().putHeader("X-Vertex-Header", "Vertex header");
        rc.next();
    }
}