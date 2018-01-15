package com.spaikergrn.vk_client.tools;

import android.text.format.DateUtils;

import com.spaikergrn.vk_client.serviceclasses.Constants;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class TimesUtils {

    public static String getTimeDialogs(final long pLongDate) {

        return getTime(pLongDate, Constants.TIME.TIME_FORMAT_H_MM,
                Constants.TIME.TIME_FORMAT_DD_MMMM,
                Constants.TIME.TIME_FORMAT_DD_MMMM_YYYY);
    }

    public static String getTimeHistory(final long pLongDate) {

        return getTime(pLongDate, Constants.TIME.TIME_FORMAT_H_MM,
                Constants.TIME.DATE_FORMAT_DD_MMMM_HH_MM,
                Constants.TIME.DATE_FORMAT_DD_MMMM_YYYY_HH_MM);

    }

    private static String getTime(final long pLongDate, final String pToday, final String pThisYear, final String pLastYear) {
        final Date date = new Date(pLongDate * 1000);
        TimeZone.setDefault(TimeZone.getTimeZone(Constants.TIME.EUROPE_MINSK));

        final String format;
        if (DateUtils.isToday(pLongDate * 1000)) {
            format = pToday;
        } else {
            if (isThisYear(date)) {
                format = pThisYear;
            } else {
                format = pLastYear;
            }
        }

        final Locale locale = new Locale(Constants.LANGUAGE);
        final DateFormatSymbols dfs = DateFormatSymbols.getInstance(locale);
        final String[] shortMonths = {
                Constants.TIME.JAN, Constants.TIME.FEB, Constants.TIME.MARCH, Constants.TIME.APR, Constants.TIME.MAY, Constants.TIME.JUN,
                Constants.TIME.JUL, Constants.TIME.AUG, Constants.TIME.SEM, Constants.TIME.OCT, Constants.TIME.NOV, Constants.TIME.DEC};
        dfs.setMonths(shortMonths);
        final SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
        sdf.setDateFormatSymbols(dfs);
        return sdf.format(pLongDate * 1000);
    }

    private static boolean isThisYear(final Date date) {
        final Calendar today = Calendar.getInstance();
        final Calendar specifiedDate = Calendar.getInstance();
        specifiedDate.setTime(date);

        return today.get(Calendar.YEAR) == specifiedDate.get(Calendar.YEAR);
    }
}
