package com.example.myapplication.tools;

import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.format.DateUtils;

import com.example.myapplication.serviceclasses.Constants;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class TimesUtils {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getTime(final long pLongDate) {

        final Date date = new Date(pLongDate * 1000);
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Minsk"));

        final String format;
        if (DateUtils.isToday(pLongDate * 1000)) {
            format = Constants.TIME.TIME_FORMAT_H_MM;
        } else {
            if (isThisYear(date)) {
                format = Constants.TIME.TIME_FORMAT_DD_MMMM;
            } else {
                format = Constants.TIME.TIME_FORMAT_DD_MMMM_YYYY;
            }
        }

        final Locale locale = new Locale(Constants.LANGUAGE);
        final DateFormatSymbols dfs = DateFormatSymbols.getInstance(locale);
        final String[] shortMonths = {
                "янв.", "фев.", "март", "апр.", "май", "июн.",
                "июл.", "авг.", "сен.", "окт.", "нояб.", "дек."};
        dfs.setMonths(shortMonths);
        final SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
        sdf.setDateFormatSymbols(dfs);
        return sdf.format(pLongDate * 1000);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static boolean isToday(final Date date) {
        final Calendar today = Calendar.getInstance();
        final Calendar specifiedDate = Calendar.getInstance();
        specifiedDate.setTime(date);

        return today.get(Calendar.DAY_OF_MONTH) == specifiedDate.get(Calendar.DAY_OF_MONTH)
                && today.get(Calendar.MONTH) == specifiedDate.get(Calendar.MONTH)
                && today.get(Calendar.YEAR) == specifiedDate.get(Calendar.YEAR);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static boolean isThisYear(final Date date) {
        final Calendar today = Calendar.getInstance();
        final Calendar specifiedDate = Calendar.getInstance();
        specifiedDate.setTime(date);

        return today.get(Calendar.YEAR) == specifiedDate.get(Calendar.YEAR);
    }
}
