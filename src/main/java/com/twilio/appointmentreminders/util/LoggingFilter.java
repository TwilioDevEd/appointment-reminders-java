package com.twilio.appointmentreminders.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Filter;
import spark.Request;
import spark.Response;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LoggingFilter implements Filter {

    private static final String TEMPLATE_WITH_BODY =
            "\nRequest {} {} {} HEADERS:[{}] BODY: {}\nResponse {} HEADERS:[{}] BODY: {} ";
    private static final String TEMPLATE_WITH_NO_BODY =
            "\nRequest {} {} {} HEADERS:[{}] \nResponse {} HEADERS:[{}]";
    private static Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void handle(Request request, Response response) throws Exception {
        String requestHeaderString = buildHeadersString(request.headers(),
                h -> request.headers(h));
        String responseHeaderString = buildHeadersString(response.raw().getHeaderNames(),
                h -> response.raw().getHeader(h));
        String template;
        Object[] params;
        if(logger.isDebugEnabled()) {
            template = TEMPLATE_WITH_BODY;
            params = new Object[] {
                    request.requestMethod(),
                    request.pathInfo(),
                    request.protocol(),
                    requestHeaderString,
                    request.body(),
                    response.raw().getStatus(),
                    responseHeaderString,
                    response.body()
            };
        } else {
            template = TEMPLATE_WITH_NO_BODY;
            params = new Object[] {
                    request.requestMethod(),
                    request.pathInfo(),
                    request.protocol(),
                    requestHeaderString,
                    response.raw().getStatus(),
                    responseHeaderString,
            };
        }
        logger.info(template, params);
    }

    private String buildHeadersString(Collection<String> headers, Function<String, String> getHeader) {
        return headers
                .stream()
                .map(h -> h + ":" + getHeader.apply(h))
                .collect(Collectors.joining(", "));
    }
}
