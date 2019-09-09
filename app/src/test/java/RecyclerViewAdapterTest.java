import com.sweethome.jimmy.mynews.Models.Result;
import com.sweethome.jimmy.mynews.Views.RecyclerViewAdapter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterTest {
    private Result result = new Result();
    private List<Result> resultList = new ArrayList<>();
    private RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(resultList, null, null, 0);

    @Before
    public void prepareForTest() {
        result.setUrl("http://www");
        resultList.add(result);
    }

    @Test
    public void getItemCountTest() {
        Assert.assertEquals(1, recyclerViewAdapter.getItemCount());
    }

    @Test
    public void  getArticleUrlTest() {
        Assert.assertEquals("http://www", recyclerViewAdapter.getArticleUrl(0));
    }
}
