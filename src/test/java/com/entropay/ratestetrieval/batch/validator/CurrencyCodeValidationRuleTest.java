package com.entropay.ratestetrieval.batch.validator;

import com.entropay.ratestetrieval.batch.RateData;
import com.entropay.ratestetrieval.batch.RateLineParseException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Created by Pietro Cascio on 14/05/16.
 */
public class CurrencyCodeValidationRuleTest {

    private CurrencyCodeValidationRule currencyCodeValidationRule;
    private RateData data;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        currencyCodeValidationRule = new CurrencyCodeValidationRule();
        data = new RateData(null, null);
    }

    // USDEUR
    @Test
    public void currencyCodesAreValid() {
        data.setBuyCurrencyCode("USD");
        data.setSellCurrencyCode("EUR");

        currencyCodeValidationRule.validate(data);
    }

    @Test(expected = RateLineParseException.class)
    public void buyCurrencyCodeIsNullThrowAnException() {
        data.setBuyCurrencyCode(null);
        data.setSellCurrencyCode("EUR");

        currencyCodeValidationRule.validate(data);
    }

    @Test(expected = RateLineParseException.class)
    public void sellCurrencyCodeIsNullThrowAnException()  {
        data.setBuyCurrencyCode("EUR");
        data.setSellCurrencyCode(null);

        currencyCodeValidationRule.validate(data);
    }

    @Test
    public void buyCurrencyCodeIsInvalidThrowAnException() {
        thrown.expect(RateLineParseException.class);
        thrown.expectMessage("Buy currency code is not in a valid ISO-4217 format.");


        data.setBuyCurrencyCode("USA");
        data.setSellCurrencyCode("EUR");

        currencyCodeValidationRule.validate(data);
    }

    @Test
    public void sellCurrencyCodeIsInvalidThrowAnException() {
        thrown.expect(RateLineParseException.class);
        thrown.expectMessage("Sell currency code is not in a valid ISO-4217 format.");


        data.setBuyCurrencyCode("USD");
        data.setSellCurrencyCode("EUZ");

        currencyCodeValidationRule.validate(data);
    }

    @Test(expected = RateLineParseException.class)
    public void buyCurrencyCodeIsShorterThrowAnException() {

        data.setBuyCurrencyCode("US");
        data.setSellCurrencyCode("EUR");

        currencyCodeValidationRule.validate(data);
    }

    @Test(expected = RateLineParseException.class)
    public void sellCurrencyCodeIsShorterThrowAnException() throws Exception {

        data.setBuyCurrencyCode("USD");
        data.setSellCurrencyCode("EU");

        currencyCodeValidationRule.validate(data);
    }

    @Test(expected = RateLineParseException.class)
    public void buyCurrencyCodeIsLongerThrowAnException() {

        data.setBuyCurrencyCode("USDZ");
        data.setSellCurrencyCode("EUR");

        currencyCodeValidationRule.validate(data);
    }

    @Test(expected = RateLineParseException.class)
    public void sellCurrencyCodeIsLongerThrowAnException() throws Exception {

        data.setBuyCurrencyCode("USD");
        data.setSellCurrencyCode("EURZ");

        currencyCodeValidationRule.validate(data);
    }
}