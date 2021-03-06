package org.jspare.kui

import io.vertx.core.Future
import io.vertx.core.http.HttpServerOptions
import org.jspare.vertx.AbstractModule
import org.jspare.vertx.web.builder.HttpServerBuilder
import org.jspare.vertx.web.builder.RouterBuilder

class KuiModule : AbstractModule() {

    override fun loadAsync(future: Future<Void>) {

        val pckg = instance.javaClass.`package`.name

        print(pckg)

        val router = RouterBuilder
                .create(vertx)
                .addRoutePackage(pckg)
                .build()

        val options = HttpServerOptions(config)
        val httpServer = HttpServerBuilder
                .create(vertx)
                .httpServerOptions(options)
                .build()

        httpServer.requestHandler(router::accept)
        httpServer.listen { ar ->
            if (ar.failed()) future.fail(ar.cause())
            else future.complete()
        }
    }
}
