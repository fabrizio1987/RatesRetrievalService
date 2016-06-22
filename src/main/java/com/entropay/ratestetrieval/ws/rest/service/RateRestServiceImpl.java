package com.entropay.ratestetrieval.ws.rest.service;

import com.entropay.ratestetrieval.entity.Rate;
import com.entropay.ratestetrieval.exception.GenericException;
import com.entropay.ratestetrieval.repository.RateRepository;

import javax.ejb.EJB;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Pietro Cascio on 15/05/16.
 */
public class RateRestServiceImpl implements RateRestService {

    private static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd");

    @EJB
    private RateRepository rateRepository;

    @Override
    public Response addRate(Rate rate) throws Exception {

        Response response;
        if (rate == null) {
            throw new WebApplicationException("The body request is empty!");
        }
        rateRepository.insert(rate);
        response = Response.ok(rate).build();

        return response;
    }

    @Override
    public Response updateRate(Rate rate) throws Exception {

        Response response;

        if (rate != null) {
            Rate currentRate = rateRepository.findById(rate.getId());
            if (currentRate != null) {
                rateRepository.update(rate);
                response = Response.ok().build();
            } else {
                response = Response.notModified().build();
            }
        } else {
            response = Response.notModified().build();
        }
        return response;
    }

    @Override
    public Response findById(long id) throws Exception {

        Response response;
        Rate rate = rateRepository.findById(id);
        if (rate != null) {
            response = Response.ok(rate).build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND).build();
        }

        return response;
    }

    @Override
    public List<Rate> findByDate(String date) throws Exception {
        Response response;
        List<Rate> ratesByDate = new ArrayList<>();
        if (date != null) {
            try {
                Date validDate = DATE_FORMAT.parse(date);
                ratesByDate = rateRepository.findByDate(validDate);

            } catch (ParseException e) {
                throw new GenericException("The date is in a invalid format");
            }
        }
        return ratesByDate;
    }

    // Remove this implementation
//    @Override
//    public List<Rate> findByDateRange(String startDate, String endDate) throws GenericException {
//        Response response;
//        List<Rate> ratesByDate = new ArrayList<>();
//        if (startDate != null && endDate != null) {
//            try {
//                Date start = DATE_FORMAT.parse(startDate);
//                Date end = DATE_FORMAT.parse(endDate);
//                ratesByDate = rateRepository.findByDateRange(start, end);
//
//            } catch (ParseException e) {
//                throw new GenericException("The date is in a invalid format");
//            }
//        }
//        return ratesByDate;
//    }

    @Override
    public List<Rate> findAll() {
        return rateRepository.findAll();
    }

    @Override
    public Response remove(long id) throws Exception {
        Response response;

        Rate dbRate = rateRepository.findById(id);
        if (dbRate != null) {
            rateRepository.remove(dbRate.getId());
            response = Response.ok().build();
        } else {
            response = Response.notModified().build();
        }

        return response;
    }

    @Override
    public Response removeAll() throws Exception {
        rateRepository.removeAll();

        return Response.ok().build();
    }
}
