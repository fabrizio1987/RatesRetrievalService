package com.entropay.ratestetrieval.batch.validator;

import com.entropay.ratestetrieval.batch.RateData;
import com.entropay.ratestetrieval.batch.RateLineParseException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Pietro Cascio on 14/05/16.
 */
public class DateValidationRule implements GenericValidationRule {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    @Override
    public void validate(RateData data) throws RateLineParseException {

        if (data.getDate() != null) {
            try {
                DATE_FORMAT.setLenient(false);
                DATE_FORMAT.parse(data.getDate());
            } catch (ParseException e) {
                throw new RateLineParseException("The date format is incorrect", data.getFilename(), data.getPlainLineRecord());
            }
        } else {
            throw new RateLineParseException("The date is null", data.getFilename(), data.getPlainLineRecord());
        }
    }
}
