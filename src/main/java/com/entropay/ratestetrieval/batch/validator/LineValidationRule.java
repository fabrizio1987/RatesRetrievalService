package com.entropay.ratestetrieval.batch.validator;

import com.entropay.ratestetrieval.batch.RateData;
import com.entropay.ratestetrieval.batch.RateLineParseException;

import java.util.regex.Pattern;

/**
 * Created by Pietro Cascio on 14/05/16.
 */

// TODO: Write the test case for this class
public class LineValidationRule implements GenericValidationRule {

    private static final String LINE_PATTERN = "^\\d{8}\\d{8}[a-zA-Z]{3}[a-zA-Z]{3}$";
    private static final Pattern pattern = Pattern.compile(LINE_PATTERN);

    @Override
    public void validate(RateData data) throws RateLineParseException {

        if (!pattern.matcher(data.getPlainLineRecord()).matches()) {
            throw new RateLineParseException("The line format is not valid", data.getFilename(), data.getPlainLineRecord());
        }
    }
}
