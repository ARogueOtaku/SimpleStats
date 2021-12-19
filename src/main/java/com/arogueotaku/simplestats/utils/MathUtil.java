package com.arogueotaku.simplestats.utils;

import java.text.DecimalFormat;

public class MathUtil {
    public static double round(double num, int decimalplaces) {
        if(decimalplaces < 1) return Math.round(num);
        return Math.round((num*Math.pow(10,decimalplaces)))/(Math.pow(10,decimalplaces));
    }
    public static String roundString(double num, int decimalplaces) {
        double doubledVal = round(num, decimalplaces);
        if(decimalplaces < 1) return  ""+doubledVal;
        DecimalFormat decimalFormat = new DecimalFormat("0." + "0".repeat(decimalplaces));
        return decimalFormat.format(doubledVal);
    }
}
