package com.bulq.bulq_commerce.util;

import org.apache.commons.lang3.RandomStringUtils;

public class AppUtil {
    public String stringGenerator(Long id) {
        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = true;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
        generatedString += id.toString();
        return generatedString;
    }
}
