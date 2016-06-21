package com.entropay.ratestetrieval.ws.rest.exception;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by Pietro Cascio on 17/05/16.
 */
@Provider
public class DefaultRestExceptionHandler implements ExceptionMapper<Exception> {

    @Context
    private HttpHeaders headers;

    @Override
    public Response toResponse(Exception e) {

        Response response = null;

        StringBuilder sbResponse = new StringBuilder("<response>");
        sbResponse.append("<status>ERROR</status>");
        sbResponse.append(String.format("<message>%s</message>", e.getMessage()));
        sbResponse.append("</response>");

        response = Response.serverError().entity(sbResponse.toString()).type(headers.getMediaType()).build();

        return response;
    }
}
