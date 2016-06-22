package com.entropay.ratestetrieval.batch.listener;

import javax.batch.api.listener.JobListener;
import javax.inject.Named;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Pietro Cascio on 14/05/16.
 */
@Named("RateProcessorJobListener")
public class RateProcessorJobListener implements JobListener {

    private static final Logger LOG =
            Logger.getLogger(RateProcessorJobListener.class.getName());

    @Override
    public void beforeJob() throws Exception {
        LOG.log(Level.INFO, "Rate processor job starting at {0}", new Date());
    }

    @Override
    public void afterJob() throws Exception {
        LOG.log(Level.INFO, "Rate processor job completed at {0}", new Date());
    }
}
