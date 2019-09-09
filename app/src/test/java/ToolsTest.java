import org.junit.Test;

import java.util.Date;

import static com.sweethome.jimmy.mynews.Utils.Tools.dateFormatter;
import static com.sweethome.jimmy.mynews.Utils.Tools.dateSearchFormatter;
import static org.junit.Assert.assertEquals;

public class ToolsTest {

    @Test
    public void testDateFormatter() {
        Date date = new Date("12/31/1998");
        assertEquals("31/12/1998", dateFormatter(date));
    }

    @Test
    public void testDateSearchFormatter() {
        String dateString = "01/02/1990";
        assertEquals("19900201", dateSearchFormatter(dateString));
    }
}