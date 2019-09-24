package com.sweethome.jimmy.mynews.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Tools {

    // Format date to dd/MM/yyyy
    public static String dateFormatter(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return formatter.format(date);
    }

    // Format date string dd/MM/YYYY to YYYYMMdd
    public static String dateSearchFormatter(String dateString) {
        String[] stringTab = dateString.split("/");
        return stringTab[2] + stringTab[1] + stringTab[0];
    }
}
