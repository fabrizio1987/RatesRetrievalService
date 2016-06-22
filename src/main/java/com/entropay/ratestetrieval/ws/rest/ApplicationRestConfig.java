package com.entropay.ratestetrieval.ws.rest;


import com.entropay.ratestetrieval.ws.rest.service.JobRestServiceImpl;
import com.entropay.ratestetrieval.ws.rest.service.RateRestServiceImpl;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Pietro Cascio on 14/05/16.
 */
public class ApplicationRestConfig extends Application {
    private Set<Object> singletons = new HashSet();
    private Set<Class<?>> empty = new HashSet();

    public ApplicationRestConfig() {

        this.singletons.add(new RateRestServiceImpl());
        this.singletons.add(new JobRestServiceImpl());
    }

    public Set<Class<?>> getClasses() {
        return this.empty;
    }

    public Set<Object> getSingletons() {
        return this.singletons;
    }
}
