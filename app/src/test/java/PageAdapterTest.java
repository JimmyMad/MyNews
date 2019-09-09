import androidx.fragment.app.FragmentManager;

import com.sweethome.jimmy.mynews.Controllers.Fragments.PageArticlesListFragment;
import com.sweethome.jimmy.mynews.Views.PageAdapter;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class PageAdapterTest {

    private FragmentManager fragmentManager = Mockito.mock(FragmentManager.class);
    private PageAdapter pageAdapter = new PageAdapter(fragmentManager);

    @Test
    public void getItemTest() {
        Assert.assertEquals(PageArticlesListFragment.newInstance(2).getClass(), pageAdapter.getItem(10).getClass());
    }

    @Test
    public void getCountTest() {
        Assert.assertEquals(3, pageAdapter.getCount());
    }
}
