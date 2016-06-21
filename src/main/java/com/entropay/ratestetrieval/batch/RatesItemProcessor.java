package com.entropay.ratestetrieval.batch;

import com.entropay.ratestetrieval.entity.Rate;

import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Named;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Pietro Cascio on 13/05/16.
 */
@Named("RatesItemProcessor")
public class RatesItemProcessor implements ItemProcessor {

    private static final Logger LOG =
            Logger.getLogger(RatesItemProcessor.class.getName());

    @Override
    public Object processItem(Object rateData) throws Exception {
        RateData data = (RateData) rateData;
        LOG.log(Level.INFO, "** RatesItemProcessor class **");
        LOG.log(Level.INFO, data.toString());

        Rate rateEntity = convertToEntity(data);

        return rateEntity;
    }

    private Rate convertToEntity(RateData data) {
        Rate entity = new Rate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        entity.setFilename(data.getFilename());

        currencyConversion(data, entity);

        Date validDate = null;
        try {
            validDate = sdf.parse(data.getDate());
        } catch (ParseException e) {
            LOG.log(Level.WARNING, "Error during the parsing date process");
            validDate = null;
        }
        entity.setValidDate(validDate);

        return entity;
    }

    private void currencyConversion(RateData data, Rate entity) {
        BigDecimal conversionRate = new BigDecimal(data.getRate());
        conversionRate = conversionRate.setScale(7);

        BigDecimal conversionRateDivisor = new BigDecimal(100000);
        entity.setConversionRate(conversionRate.divide(conversionRateDivisor, RoundingMode.HALF_UP));

        entity.setBuyCurrencyCode(data.getBuyCurrencyCode());
        entity.setSellCurrencyCode(data.getSellCurrencyCode());
    }
}
