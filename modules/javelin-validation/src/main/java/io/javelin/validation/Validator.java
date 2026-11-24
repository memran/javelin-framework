package io.javelin.validation;

import java.util.List;
import java.util.Map;

/** Validates string values against explicit rule strings. */
public interface Validator {
    /**
     * @param values field name to value
     * @param rules  field name to rule strings, e.g. {@code ["required", "min:3"]}
     * @return field name to messages; empty when valid
     */
    Map<String, List<String>> validate(Map<String, String> values, Map<String, List<String>> rules);
}
