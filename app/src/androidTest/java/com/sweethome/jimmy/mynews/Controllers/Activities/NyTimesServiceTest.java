package com.sweethome.jimmy.mynews.Controllers.Activities;

public class NyTimesServiceTest {

    // Tests of the 3 requests to load the tabs

    /*@Test
    public void httpRequestTopStoriesTest() {

        Observable<Article> observableArticles = NyTimesStreams.streamFetchTopStories("home");
        TestObserver<Article> testObserver = new TestObserver<>();

        observableArticles.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();
        Article article = testObserver.values().get(0);

        assertEquals("OK", article.getStatus());
        assertEquals("home", article.getSection());
        assertNotNull(article.getResults());
    }

    @Test
    public void httpRequestMostPopularTest() {

        Observable<ArticleMostPopular> observableArticles = NyTimesStreams.streamFetchMostPopular();
        TestObserver<ArticleMostPopular> testObserver = new TestObserver<>();

        observableArticles.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();
        ArticleMostPopular article = testObserver.values().get(0);

        assertEquals("OK", article.getStatus());
        assertNotNull(article.getResultMostPopulars());
    }

    @Test
    public void httpRequestSearchArticlesTest() {

        Observable<SearchArticle> observableArticles = NyTimesStreams.streamFetchSearchArticles("New York", "Travel");
        TestObserver<SearchArticle> testObserver = new TestObserver<>();

        observableArticles.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();
        SearchArticle article = testObserver.values().get(0);

        assertEquals("OK", article.getStatus());
        assertNotNull(article.getResponse().getDocs());
    }*/
}