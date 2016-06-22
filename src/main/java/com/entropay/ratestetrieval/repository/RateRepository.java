package com.entropay.ratestetrieval.repository;

import com.entropay.ratestetrieval.entity.Rate;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Pietro Cascio on 14/05/16.
 */
@Stateless
public class RateRepository {

    private static final Logger LOG =
            Logger.getLogger(RateRepository.class.getName());

    @PersistenceContext
    private EntityManager entityManager;

    public Rate insert(Rate rateEntity) throws Exception {
        if (rateEntity.getId() == null) {
            entityManager.persist(rateEntity);
        } else {
            LOG.log(Level.WARNING, "The rate argument provided is invalid: {0}", rateEntity);
            throw new Exception("The id field can not be initialized in the insert operation. Check the logs!");
        }
        return rateEntity;
    }

    public List<Rate> findAll() {
        return entityManager.createQuery("SELECT r FROM Rate r").getResultList();
    }

    public Rate findById(Long id) throws Exception {
        if (id != null) {
            return entityManager.find(Rate.class, id);
        } else {
            LOG.log(Level.WARNING, "Error in findById method with argument [id: {0}] ", id);
            throw new Exception(String.format("Error in findById method with argument [id: {0}] ", id));
        }
    }

    public List<Rate> findByDate(Date date) throws Exception {
        Query query = null;
        try {
            query = entityManager.createQuery("SELECT r FROM Rate r WHERE r.validDate = :validDate");
            query.setParameter("validDate", date, TemporalType.DATE);

        } catch (Exception e) {
            LOG.log(Level.WARNING, "Error in findByDate method with argument [date: {0}] ", date);
            throw new Exception(String.format("Error in findByDate method with argument [date: %s] ", date));
        }
        return query.getResultList();
    }

    public List<Rate> findByDateRange(Date startDate, Date endDate) throws Exception {

        if (startDate != null && endDate != null) {
            Query query = entityManager.createQuery("SELECT r FROM Rate r WHERE r.validDate BETWEEN :startDate AND :endDate");
            query.setParameter("startDate", startDate, TemporalType.DATE);
            query.setParameter("endDate", endDate, TemporalType.DATE);

            return query.getResultList();
        } else {
            LOG.log(Level.WARNING, "The argument provided are not valid.\nstartDate: {0] - endDate: {1}", new Object[]{startDate, endDate});
            throw new Exception(String.format("The argument provided are not valid.\nstartDate: {0] - endDate: {1}", new Object[]{startDate, endDate}));
        }
    }

    public Rate update(Rate rate) throws Exception {
        Rate dbRate = null;
        if (rate != null) {
            dbRate = entityManager.find(Rate.class, rate.getId());
            if (dbRate != null) {
                entityManager.merge(rate);
            }
        } else {
            LOG.log(Level.WARNING, "Error in the update method with argument [rate: {0}] ", rate);
            throw new Exception(String.format("Error in the update method with argument [rate: {0}] ", rate));
        }
        return dbRate;
    }

    public void remove(Long id) throws Exception {
        Rate dbRate = entityManager.find(Rate.class, id);
        if (dbRate != null) {
            entityManager.remove(dbRate);
        } else {
            LOG.log(Level.WARNING, "The rate with id: {0} has not been found into the database", id);
            throw new Exception(String.format("The rate with id: %d has not been found into the database", id));
        }
    }

    public void removeAll() throws Exception {
        try {
            entityManager.createQuery("DELETE FROM Rate").executeUpdate();
        } catch (Exception e) {
            LOG.log(Level.WARNING, "Error in the remove method with the error message: {0}", e.getMessage());
            throw new Exception(String.format("Error in the remove method with the error message: %s", e.getMessage()));
        }
    }
}
