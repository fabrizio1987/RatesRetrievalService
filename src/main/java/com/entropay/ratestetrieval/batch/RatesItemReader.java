package com.entropay.ratestetrieval.batch;

import com.entropay.ratestetrieval.batch.validator.GenericValidationRule;
import com.entropay.ratestetrieval.batch.validator.RulesFactory;

import javax.batch.api.chunk.AbstractItemReader;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.file.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Pietro Cascio on 13/05/16.
 */
@Named("RatesItemReader")
public class RatesItemReader extends AbstractItemReader {

    /* Constant declarations */
    private static final String SCAN_DIRECTORY = "scan_directory";
    private static final String ARCHIVE_DIRECTORY = "archive_directory";
    private static final String FAILED_DIRECTORY = "failed_directory";
    private static final String ISO_8601_FORMAT_BASIC = "yyyyMMdd";

    public static final Logger LOG =
            Logger.getLogger(RatesItemReader.class.getName());

    /* Member variable declarations */
    @Inject
    private JobContext jobContext;
    private RatesFilesCheckpoint checkpoint;
    private RandomAccessFile currentFile;

    @Override
    public void open(Serializable checkpoint) throws Exception {

        String pathScanDirectory = jobContext.getProperties().getProperty(SCAN_DIRECTORY);
        File scanDirectory = new File(pathScanDirectory);

        if (checkpoint == null) {
            this.checkpoint = new RatesFilesCheckpoint();
            LOG.log(Level.INFO, "Scanning target directory {0}", scanDirectory);

            if (!scanDirectory.exists()) {
                LOG.log(Level.INFO, "The target directory does not exist, creating it");
                scanDirectory.mkdirs();
            } else {
                this.checkpoint.setFiles(Arrays.asList(scanDirectory.listFiles()));
            }
        } else {
            LOG.log(Level.INFO, "Checkpoint has been found. Starting from previous checkpoint");
            this.checkpoint = (RatesFilesCheckpoint) checkpoint;
        }

        // File file = this.checkpoint.currentFile();
        File file = this.checkpoint.currentFile();


        if (file == null) {
            LOG.log(Level.INFO, "There isn't any file to process");
            currentFile = null;
        } else {
            currentFile = new RandomAccessFile(file, "r");
            LOG.log(Level.INFO, "Processing file: {0}", file.getName());
            currentFile.seek(this.checkpoint.getFilePointer());
        }
    }

    @Override
    public Object readItem() throws Exception {
        if (currentFile != null) {
            String line = currentFile.readLine();
            if (line != null) {
                // Sets the checkpoint
                this.checkpoint.setFilePointer(currentFile.getFilePointer());

                return parseLine(this.checkpoint.currentFile().getName(), line);
            } else {
                LOG.log(Level.INFO, "Finished processing file: {0}", this.checkpoint.currentFile());
                currentFile.close();

                // Move the file to the archive directory
                String strArchiveDirectory = jobContext.getProperties().getProperty(ARCHIVE_DIRECTORY);
                File archiveDirectory = new File(strArchiveDirectory);

                if (!archiveDirectory.exists()) {
                    archiveDirectory.mkdirs();
                }
                Path source = Paths.get(this.checkpoint.currentFile().toURI());

                Path target = Paths.get(strArchiveDirectory, this.checkpoint.currentFile().getName());
                Files.move(source, target);
                LOG.log(Level.INFO, "The file has been moved to the archive directory");
                // this.checkpoint.currentFile().delete();

                File nextFile = this.checkpoint.nextFile();
                if (nextFile == null) {
                    LOG.log(Level.INFO, "No more files to process");
                    return null;
                } else {
                    currentFile = new RandomAccessFile(nextFile, "r");
                    LOG.log(Level.INFO, "Processing file: {0}", currentFile);

                    return readItem();
                }
            }
        } else {
            return null;
        }
    }

    private Object parseLine(String filename, String line) throws RateLineParseException {

        RateData rateData = null;
        LOG.log(Level.INFO, "Line read: {0}", line);

//      Line validator rules
//      1. Total line length must be 22 characters
        if (line.length() != 22) {
            throw new RateLineParseException("Line length is invalid", filename, line);
        } else {
            rateData = new RateData(filename, line);
            RulesFactory rulesFactory = new RulesFactory(filename, line);

            // Loop on the validation list to execute each validation business logic
            for (GenericValidationRule rule : rulesFactory.get()) {
                rule.validate(rateData);
            }
        }
        return rateData;
    }

    @Override
    public Serializable checkpointInfo() throws Exception {
        return this.checkpoint;
    }
}
