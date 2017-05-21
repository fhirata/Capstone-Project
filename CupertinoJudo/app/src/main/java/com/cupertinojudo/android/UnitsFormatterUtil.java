package com.cupertinojudo.android;

import android.content.Context;

/**
 *
 */

public class UnitsFormatterUtil {
    public static String formatWeight(Context context, int weight) {
        String weightInPounds = context.getString(R.string.weight_in_pounds);
        return String.format(weightInPounds, weight);
    }
}
