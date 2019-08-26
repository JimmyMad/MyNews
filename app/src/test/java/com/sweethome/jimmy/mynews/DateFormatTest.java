package com.sweethome.jimmy.mynews;

import org.junit.Test;
import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatTest {

    @Test
    public void dateFormat_isCorrect() {

        long l = 1;
        Date aDate = new Date(l);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String strDate = formatter.format(aDate);
        assertEquals("01/01/1970", strDate);
    }
}