package com.entropay.ratestetrieval.spring.controllers;

import com.entropay.ratestetrieval.entity.Rate;
import com.entropay.ratestetrieval.repository.RateRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.ejb.EJB;

/**
 * Created by Pietro Cascio on 16/05/16.
 */
@Controller
public class RateController {

    @EJB(mappedName = "java:module/RateRepository")
    RateRepository rateRepository;

    @RequestMapping("rates")
    public String listRates(Model model) {
        model.addAttribute("rates", rateRepository.findAll());

        return "rates";
    }

    @RequestMapping("/rate/{id}")
    public String getRate(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("rate", rateRepository.findById(id));
        } catch (Exception e) {
            // Todo: Implement the exception handling in a correct way into the repository
            e.printStackTrace();
        }
        return "rate";
    }

    @RequestMapping("/rate/new")
    public String newRate(Model model) {
        model.addAttribute("rate", new Rate());
        return "rateform";
    }

    @RequestMapping("rate/edit/{id}")
    public String edit(@PathVariable Long id, Model model) throws Exception {
        model.addAttribute("rate", rateRepository.findById(id));

        return "rateform";
    }

    @RequestMapping("rate/delete/{id}")
    public String delete(@PathVariable Long id, Model model) throws Exception {
        rateRepository.remove(id);

        return "redirect:/rates";
    }

    @RequestMapping("rate/delete/all")
    public String deleteAll() throws Exception {
        rateRepository.removeAll();

        return "redirect:/rates";
    }

    @RequestMapping(value = "/rate", method = RequestMethod.POST)
    public String saveOrUpdateRate(Rate rate) throws Exception {
            Rate savedRate = null;
        if (rate != null) {
            if (rate.getId() == null) {
                savedRate = rateRepository.insert(rate);
            } else {
                savedRate = rateRepository.update(rate);
            }
        } else {
            throw new RuntimeException("The product can not be null!");
        }
        return "redirect:/rate/" + savedRate.getId();
    }
}
