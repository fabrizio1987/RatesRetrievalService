package com.entropay.ratestetrieval.spring.controllers;

import com.entropay.ratestetrieval.service.JobService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.ejb.EJB;

/**
 * Created by Pietro Cascio on 17/05/16.
 */
@Controller
public class JobController {


    @EJB(mappedName = "java:module/JobService")
    JobService jobService;

    @RequestMapping(value = "/job", method = RequestMethod.POST)
    public String startJob(Model model) {

        return "index";
    }
}

