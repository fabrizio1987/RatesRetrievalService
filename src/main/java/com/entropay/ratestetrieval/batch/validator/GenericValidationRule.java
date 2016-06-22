package com.entropay.ratestetrieval.batch.validator;

import com.entropay.ratestetrieval.batch.RateData;
import com.entropay.ratestetrieval.batch.RateLineParseException;

/**
 * Created by Pietro Cascio on 14/05/16.
 */
public interface GenericValidationRule {
    public void validate(RateData data) throws RateLineParseException;
}
