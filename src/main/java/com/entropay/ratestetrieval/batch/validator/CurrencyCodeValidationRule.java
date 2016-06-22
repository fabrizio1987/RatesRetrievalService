package com.entropay.ratestetrieval.batch.validator;

import com.entropay.ratestetrieval.batch.RateData;
import com.entropay.ratestetrieval.batch.RateLineParseException;

import java.util.*;

/**
 * Created by Pietro Cascio on 14/05/16.
 */
public class CurrencyCodeValidationRule implements GenericValidationRule {

    private static final Set<Currency> CURRENCY_SET = Currency.getAvailableCurrencies();
    private static final List<String> currencyCodeSet = new ArrayList<>();

    static {
        Iterator<Currency> iterator = CURRENCY_SET.iterator();
        while (iterator.hasNext()) {
            currencyCodeSet.add(iterator.next().getCurrencyCode());
        }
    }

    @Override
    public void validate(RateData data) throws RateLineParseException {

        if (data == null) {
            throw new IllegalArgumentException("The parameter is null");
        }
        if ((data.getBuyCurrencyCode() != null && data.getBuyCurrencyCode().length() == 3) &&
                data.getSellCurrencyCode() != null && data.getSellCurrencyCode().length() == 3) {
            if (!currencyCodeSet.contains(data.getBuyCurrencyCode())) {
                throw new RateLineParseException("Buy currency code is not in a valid ISO-4217 format.", data.getFilename(), data.getPlainLineRecord());
            }
            if (!currencyCodeSet.contains(data.getSellCurrencyCode())) {
                throw new RateLineParseException("Sell currency code is not in a valid ISO-4217 format.", data.getFilename(), data.getPlainLineRecord());
            }
        } else {
            throw new RateLineParseException("Currency code is invalid.", data.getFilename(), data.getPlainLineRecord());
        }
    }
}
