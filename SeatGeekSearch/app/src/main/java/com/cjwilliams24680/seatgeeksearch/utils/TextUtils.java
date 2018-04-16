package com.cjwilliams24680.seatgeeksearch.utils;

/**
 * Created by chris on 4/15/18.
 */

public class TextUtils {

    public static boolean isBlank(String string) {
        return string == null || string.trim().equals("");
    }

    public static String nonNullify(String string) {
        return string == null ? "" : string;
    }
}
