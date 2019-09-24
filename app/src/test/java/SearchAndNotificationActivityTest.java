import com.sweethome.jimmy.mynews.Controllers.Activities.SearchAndNotificationActivity;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

public class SearchAndNotificationActivityTest {

    private SearchAndNotificationActivity searchAndNotificationActivity = Mockito.mock(SearchAndNotificationActivity.class);

    @BeforeClass
    public static void initiateTest() {
    }

    @Test
    public void checkBoxArtIsChecked() {
        Assert.assertEquals(0, searchAndNotificationActivity.checkBoxesCheckedCount(0));
    }







}
