package com.example.projectbase.util;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomPasswordUtil {
    public static String random() {
        int length = 6;
        boolean useLetters = true;
        boolean useNumbers = true;
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }
}
