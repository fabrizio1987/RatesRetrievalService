package com.entropay.ratestetrieval.service;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.ejb.Stateless;
import java.util.Properties;

/**
 * Created by Pietro Cascio on 14/05/16.
 */
@Stateless
public class JobService {

    private JobOperator jobOperator;
    private long jobId;

    public long startJob() {
        this.jobOperator = BatchRuntime.getJobOperator();
        this.jobId = jobOperator.start("ratesProcessJob", new Properties());

        return this.jobId;
    }
}
