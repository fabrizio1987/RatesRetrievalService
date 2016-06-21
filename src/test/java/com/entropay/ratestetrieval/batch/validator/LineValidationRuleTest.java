package com.entropay.ratestetrieval.batch.validator;

import com.entropay.ratestetrieval.batch.RateData;
import com.entropay.ratestetrieval.batch.RateLineParseException;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Pietro Cascio on 15/05/16.
 */
public class LineValidationRuleTest {
    
    private RateData data;
    private LineValidationRule lineValidationRule;
    
    @Before
    public void setUp() throws Exception {
        this.data = new RateData(null, null);
        this.lineValidationRule = new LineValidationRule();
    }

    @Test
    public void lineisValid() throws Exception {
        data.setPlainLineRecord("2016010100814474USDNOK");
        lineValidationRule.validate(data);
    }

    @Test(expected = RateLineParseException.class)
    public void lineIsShorterThrowException() throws Exception {
        data.setPlainLineRecord("2016010100814474USDNO");
        lineValidationRule.validate(data);
    }

    @Test(expected = RateLineParseException.class)
    public void lineIsLongerThrowException() throws Exception {
        data.setPlainLineRecord("2016010100814474USDNOKZ");
        lineValidationRule.validate(data);
    }

    @Test(expected = RateLineParseException.class)
    public void lineHasCharactersInDateThrowException() throws Exception {
        data.setPlainLineRecord("20A6010100814474USDNOK");
        lineValidationRule.validate(data);
    }

    @Test(expected = RateLineParseException.class)
    public void lineHasCharactersInConversionRateThrowException() throws Exception {
        data.setPlainLineRecord("2016010100A14474USDNOK");
        lineValidationRule.validate(data);
    }

    @Test(expected = RateLineParseException.class)
    public void lineHasNumberInBuyCodeThrowException() throws Exception {
        data.setPlainLineRecord("2016010100814474U1DNOK");
        lineValidationRule.validate(data);
    }

    @Test(expected = RateLineParseException.class)
    public void lineHasNumberInSellCodeThrowException() throws Exception {
        data.setPlainLineRecord("2016010100814474USDNO1");
        lineValidationRule.validate(data);
    }
}