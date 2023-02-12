package com.programacho.ecshttpexchangelogger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.web.exchanges.HttpExchange;
import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;

import java.net.URI;
import java.time.Duration;
import java.util.List;

public class ECSHttpExchangeLogger implements HttpExchangeRepository {

    private final Logger log = LoggerFactory.getLogger(ECSHttpExchangeLogger.class);

    private final String httpResponseMimeType;


    public ECSHttpExchangeLogger() {
        this.httpResponseMimeType = null;
    }

    public ECSHttpExchangeLogger(String httpResponseMimeType) {
        this.httpResponseMimeType = httpResponseMimeType;
    }

    @Override
    public List<HttpExchange> findAll() {
        return List.of();
    }

    @Override
    public void add(HttpExchange httpExchange) {
        Duration timeTaken = httpExchange.getTimeTaken();
        URI uri = httpExchange.getRequest().getUri();
        HttpExchange.Request request = httpExchange.getRequest();
        HttpExchange.Response response = httpExchange.getResponse();

        log.atInfo()
                // Message
                .setMessage("Http Exchange completed.")

                // Event
                .addKeyValue("event.category", List.of("web"))
                .addKeyValue("event.action", "access-log")
                .addKeyValue("event.start", httpExchange.getTimestamp())
                .addKeyValue("event.duration", timeTaken.getSeconds() * 1_000_000_000 + timeTaken.getNano())

                // URL
                .addKeyValue("url.original", uri)
                .addKeyValue("url.full", uri)
                .addKeyValue("url.scheme", uri.getScheme())
                .addKeyValue("url.domain", uri.getHost())
                .addKeyValue("url.path", uri.getPath())
                .addKeyValue("url.port", uri.getPort())
                .addKeyValue("url.query", uri.getQuery())
                .addKeyValue("url.username", httpExchange.getPrincipal() != null ? httpExchange.getPrincipal().getName() : "-")

                // User Agent
                .addKeyValue("user_agent.original", request.getHeaders().get("user-agent"))

                // HTTP (Request)
                .addKeyValue("http.request.method", request.getMethod())
                .addKeyValue("http.request.mime_type", request.getHeaders().get("content-type"))

                // HTTP (Response)
                .addKeyValue("http.response.status_code", response.getStatus())
                .addKeyValue("http.response.mime_type", httpResponseMimeType)

                // Custom Fields (Access Log)
                .addKeyValue("labels.http.request.headers", request.getHeaders())
                .addKeyValue("labels.http.response.headers", response.getHeaders())

                // Custom Fields
                .addKeyValue("labels.key", "labels.value")
                .log();
    }
}
