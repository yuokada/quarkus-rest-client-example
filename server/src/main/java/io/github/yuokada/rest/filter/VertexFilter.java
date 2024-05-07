package io.github.yuokada.rest.filter;

import io.quarkus.vertx.web.RouteFilter;
import io.vertx.ext.web.RoutingContext;

public class VertexFilter {

    @RouteFilter(value = 10000)
    void myFilter(RoutingContext rc) {
        rc.response().putHeader("X-Vertex-Header", "Vertex header");
        rc.next();
    }
}