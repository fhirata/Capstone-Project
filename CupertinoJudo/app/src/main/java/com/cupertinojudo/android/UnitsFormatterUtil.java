package com.cupertinojudo.android;

import android.content.Context;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 */

public class UnitsFormatterUtil {
    public static String formatWeight(Context context, int weight) {
        String weightInPounds = checkNotNull(context).getString(R.string.weight_in_pounds);
        return String.format(weightInPounds, weight);
    }
}
