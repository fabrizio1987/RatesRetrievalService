package com.entropay.ratestetrieval.batch;

import com.entropay.ratestetrieval.entity.Rate;
import com.entropay.ratestetrieval.repository.RateRepository;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.ejb.EJB;
import javax.inject.Named;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Pietro Cascio on 13/05/16.
 */
@Named("RatesItemWriter")
public class RatesItemWriter extends AbstractItemWriter {

    public static final Logger LOG =
            Logger.getLogger(RatesItemWriter.class.getName());
    @EJB
    RateRepository rateRepository;

    @Override
    public void writeItems(List<Object> list) throws Exception {
        for (Object rate : list) {
            Rate rateEntity = (Rate) rate;

            rateRepository.insert(rateEntity);
        }
    }
}
