package com.entropay.ratestetrieval.batch.validator;

import com.entropay.ratestetrieval.batch.RateData;
import com.entropay.ratestetrieval.batch.RateLineParseException;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Pietro Cascio on 14/05/16.
 */
public class DateValidationRuleTest {

    private DateValidationRule dateValidationRule;
    private RateData data;

    @Before
    public void setUp() throws Exception {
        dateValidationRule = new DateValidationRule();
        data = new RateData(null, null);
    }

    @Test(expected = RateLineParseException.class)
    public void dateIsNullShouldThrowException() {
        dateValidationRule.validate(data);
    }

    @Test(expected = RateLineParseException.class)
    public void dayIsInvalidShouldThrowException() {
        data.setDate("20160532");
        dateValidationRule.validate(data);
    }

    @Test(expected = RateLineParseException.class)
    public void monthIsInvalidShouldThrowException() {
        data.setDate("20161320");
        dateValidationRule.validate(data);
    }

    @Test(expected = RateLineParseException.class)
    public void yearIsInvalidShouldThrowException() {
        data.setDate("20A60520");
        dateValidationRule.validate(data);
    }

    @Test(expected = RateLineParseException.class)
    public void dateFormatIsInvalidShouldThrowException() {
        data.setDate("2016/05/20");
        dateValidationRule.validate(data);
    }

    @Test(expected = RateLineParseException.class)
    public void dateFormatShorterIsInvalidShouldThrowException() {
        data.setDate("201605");
        dateValidationRule.validate(data);
    }

    @Test(expected = RateLineParseException.class)
    public void dateFormatLongerIsInvalidShouldThrowException() {
        data.setDate("2016052020");
        dateValidationRule.validate(data);
    }

    @Test
    public void dateIsValid() {
        data.setDate("20160101");
        dateValidationRule.validate(data);
    }
}