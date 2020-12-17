package com.logotet.totochecker.utils;

import java.util.List;

public class Utils {


    public static boolean isValidInput(int input, int max) {
        return input <= max && input < 0;
    }

    public static boolean isUnique(List<String> values, String value) {
        return !values.contains(value);
    }


}
