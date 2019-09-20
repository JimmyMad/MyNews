import android.widget.CheckBox;

import com.sweethome.jimmy.mynews.Controllers.Activities.SearchAndNotificationActivity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchAndNotificationActivityTest {

    private SearchAndNotificationActivity searchAndNotificationActivity = Mockito.mock(SearchAndNotificationActivity.class);
    private ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    private CheckBox checkBoxArts = Mockito.mock(CheckBox.class);
    private CheckBox checkBoxPolitics = Mockito.mock(CheckBox.class);
    private CheckBox checkBoxBusiness = Mockito.mock(CheckBox.class);
    private CheckBox checkBoxSports = Mockito.mock(CheckBox.class);
    private CheckBox checkBoxEntrepreneurs = Mockito.mock(CheckBox.class);
    private CheckBox checkBoxTravel = Mockito.mock(CheckBox.class);

    @Before
    public void initiateTest() {
        checkBoxes.addAll(Arrays.asList(checkBoxArts, checkBoxPolitics, checkBoxBusiness, checkBoxSports, checkBoxEntrepreneurs, checkBoxTravel));
        Mockito.when(searchAndNotificationActivity.getCheckBoxes()).thenReturn(checkBoxes);
        Mockito.when(checkBoxArts.getText()).thenReturn("Arts");
        Mockito.when(checkBoxArts.isChecked()).thenReturn(true);
        Mockito.when(checkBoxPolitics.getText()).thenReturn("Politics");
        Mockito.when(checkBoxPolitics.isChecked()).thenReturn(false);
        Mockito.when(checkBoxBusiness.getText()).thenReturn("Business");
        Mockito.when(checkBoxBusiness.isChecked()).thenReturn(true);
        Mockito.when(checkBoxSports.getText()).thenReturn("Sports");
        Mockito.when(checkBoxSports.isChecked()).thenReturn(false);
        Mockito.when(checkBoxEntrepreneurs.getText()).thenReturn("Entrepreneurs");
        Mockito.when(checkBoxEntrepreneurs.isChecked()).thenReturn(true);
        Mockito.when(checkBoxTravel.getText()).thenReturn("Travel");
        Mockito.when(checkBoxTravel.isChecked()).thenReturn(false);
    }

    @Test
    public void CheckBoxArrayTest() {
        Assert.assertEquals(6, searchAndNotificationActivity.getCheckBoxes().size());
        Assert.assertTrue(searchAndNotificationActivity.getCheckBoxes().get(0).isChecked());
        Assert.assertFalse(searchAndNotificationActivity.getCheckBoxes().get(3).isChecked());
        Assert.assertEquals("Travel", searchAndNotificationActivity.getCheckBoxes().get(5).getText());
    }







}
