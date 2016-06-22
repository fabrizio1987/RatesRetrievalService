package com.entropay.ratestetrieval.ws.rest.service;

import com.entropay.ratestetrieval.service.JobService;

import javax.ejb.EJB;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by Pietro Cascio on 15/05/16.
 */
@Path("job")
public class JobRestServiceImpl {

    @EJB
    private JobService jobService;

    @POST
    @Produces("text/plain")
    public String startJob() {
        Long jobId = jobService.startJob();

        return String.format("The job has started with ID: %d", jobId );
    }
}
