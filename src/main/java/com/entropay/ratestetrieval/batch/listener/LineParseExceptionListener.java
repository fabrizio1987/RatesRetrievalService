package com.entropay.ratestetrieval.batch.listener;

import com.entropay.ratestetrieval.batch.RateLineParseException;

import javax.batch.api.chunk.listener.SkipReadListener;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Pietro Cascio on 14/05/16.
 */
@Named("LineParseExceptionListener")
public class LineParseExceptionListener implements SkipReadListener {

    private static final Logger LOG =
            Logger.getLogger(LineParseExceptionListener.class.getName());
    private static final String FAILED_DIRECTORY = "failed_directory";

    @Inject
    private JobContext jobContext;

    @Override
    public void onSkipReadItem(Exception e) throws Exception {

        File failedDirectory = new File(jobContext.getProperties().getProperty(FAILED_DIRECTORY));

        if (!failedDirectory.exists()) {
            failedDirectory.mkdirs();
        }

        RateLineParseException lineParseException = (RateLineParseException) e;
        LOG.log(Level.WARNING, "Problem parsing rate line event for file {0} and line {1}",
                new Object[] {lineParseException.getFilename(), lineParseException.getLine()});

        try (PrintWriter failedWriter = new PrintWriter(
                new BufferedWriter(new FileWriter(
                new File(failedDirectory,
                        jobContext.getJobName() +
                        "-" + jobContext.getInstanceId() + "-" +
                        lineParseException.getFilename()), true)))) {
            failedWriter.println(lineParseException.getLine());
        }
    }
}
