import org.junit.Test;

import java.util.Date;

import static com.sweethome.jimmy.mynews.Utils.Tools.dateFormatter;
import static org.junit.Assert.assertEquals;

public class ToolsTest {

    @Test
    public void testDateFormatter() {
        Date date = new Date("12/31/1998");
        /*SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        assertEquals("01/01/1970", formatter.format(date));*/
        assertEquals("31/12/1998", dateFormatter(date));
    }
}