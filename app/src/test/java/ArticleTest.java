import com.sweethome.jimmy.mynews.Models.Article;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ArticleTest {

    //Test Article
    @Test
    public void articleGetTest() {
        Article article = new Article();
        article.setStatus("ok");
        article.setCopyright("authorization");
        article.setSection("home");
        article.setLastUpdated("01/01/1990");
        article.setNumResults(1);

        assertEquals("ok", article.getStatus());
        assertEquals("authorization", article.getCopyright());
        assertEquals("home", article.getSection());
        assertEquals("01/01/1990", article.getLastUpdated());
        assertEquals("1" , article.getNumResults().toString());
        assertNull(article.getResults());
    }
}