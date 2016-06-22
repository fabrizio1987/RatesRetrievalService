package com.entropay.ratestetrieval.ws.rest.service;

import com.entropay.ratestetrieval.entity.Rate;
import com.entropay.ratestetrieval.exception.GenericException;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Pietro Cascio on 15/05/16.
 */
@Path("/rates")
@Consumes({"application/xml", "application/json"})
@Produces({"application/xml", "application/json"})
public interface RateRestService {

    @POST
    Response addRate(Rate rate) throws Exception;

    @PUT
    Response updateRate(Rate rate) throws Exception;

    @GET
    @Path("/{id}")
    Response findById(@PathParam("id")long id) throws Exception;

    @GET
    @Path("/param")
    List<Rate> findByDate(@QueryParam("date") String date) throws Exception;

    //TODO: Remove this declaration
//    @GET
//    @Path("/param")
//    List<Rate> findByDateRange(
//            @QueryParam("startDate") String startDate,
//            @QueryParam("endDate") String endDate
//    ) throws GenericException;

    @GET
    List<Rate> findAll();

    @DELETE
    @Path("/{id}")
    Response remove(@PathParam("id") long id) throws GenericException, Exception;

    @DELETE
    Response removeAll() throws Exception;
}
