package com.entropay.ratestetrieval.batch.validator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pietro Cascio on 14/05/16.
 */
public class RulesFactory {

    private String filename;
    private String line;
    // Create a list of validator rules
    private List<GenericValidationRule> validationRules;

    public RulesFactory(String filename, String line) {

        if (filename == null || line == null) {
            throw new IllegalArgumentException("Parameters can not be null!");
        }
        this.filename = filename;
        this.line = line;
        this.validationRules = new ArrayList<>();

        initializeRuleChain();
    }

    private void initializeRuleChain() {
        // Validation of the line format based on a regular expression
        this.validationRules.add(new LineValidationRule());

//         2. The line has to be constructed based on the following rules:
//            2.1. a 8-character long value consisting of the date in yyyyMMdd format
        this.validationRules.add(new DateValidationRule());
//          2.2. a 8-character long value consisting of the rate. The ratemust be divided
//               by 100,000
        // This validation is not need because already part of the LineValidationRule

//          2.3. the 3-character long buy currency in ISO-4217 format
//          2.4. the 3-character long sell currency in ISO-4217 format
        this.validationRules.add(new CurrencyCodeValidationRule());
    }

    public List<GenericValidationRule> get() {
        return this.validationRules;
    }
}
