package com.sweethome.jimmy.mynews.Controllers.Activities;

import androidx.annotation.NonNull;

import com.sweethome.jimmy.mynews.Models.Article;
import com.sweethome.jimmy.mynews.Models.Result;
import com.sweethome.jimmy.mynews.Utils.NyTimesService;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.hamcrest.CoreMatchers.is;

@RunWith(JUnit4.class)
public class NyTimesServiceTest extends TestCase {

    private static final int TIMEOUT = 10000;

    @Test(timeout = TIMEOUT)
    public void testListReposSuccess() throws IOException, InterruptedException {
        final BlockingQueue<String> events = new LinkedBlockingQueue();

        final MockWebServer mockWebServer = new MockWebServer();
        final MockResponse mockResponse = new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"status\":\"OK\",\"copyright\":\"Copyright (c) 2019 The New York Times Company. All Rights Reserved.\",\"section\":\"home\",\"last_updated\":\"2019-09-20T08:57:27-04:00\",\"num_results\":36,\"results\":[{\"section\":\"U.S.\",\"subsection\":\"\",\"title\":\"Secret F.B.I. Subpoenas Scoop Up Personal Data From Scores of Companies\",\"abstract\":\"The practice, which the bureau says is vital to counterterrorism efforts, casts a much wider net than previously disclosed, newly released documents show.\",\"url\":\"https:\\/\\/www.nytimes.com\\/2019\\/09\\/20\\/us\\/data-privacy-fbi.html\",\"byline\":\"By JENNIFER VALENTINO-DeVRIES\",\"item_type\":\"Article\",\"updated_date\":\"2019-09-20T05:00:10-04:00\",\"created_date\":\"2019-09-20T05:00:10-04:00\",\"published_date\":\"2019-09-20T05:00:10-04:00\",\"material_type_facet\":\"\",\"kicker\":\"\",\"des_facet\":[\"Surveillance of Citizens by Government\",\"Espionage and Intelligence Services\",\"Subpoenas\",\"USA Freedom Act\",\"First Amendment (US Constitution)\",\"Freedom of Information Act\",\"USA PATRIOT Act\"],\"org_facet\":[\"Federal Bureau of Investigation\",\"Bank of America Corporation\",\"Electronic Frontier Foundation\",\"Experian PLC\",\"Equifax Inc\",\"Facebook Inc\",\"Google Inc\",\"Microsoft Corp\",\"T-Mobile US Inc\",\"Verizon Communications Inc\",\"Western Union Company\",\"Yahoo! Inc\"],\"per_facet\":[],\"geo_facet\":[],\"multimedia\":[{\"url\":\"https:\\/\\/static01.nyt.com\\/images\\/2019\\/09\\/20\\/multimedia\\/20NSLs-02\\/20NSLs-02-thumbStandard.jpg\",\"format\":\"Standard Thumbnail\",\"height\":75,\"width\":75,\"type\":\"image\",\"subtype\":\"photo\",\"caption\":\"Richard Salgado, Google\\u2019s director of law enforcement and information security. The company has been public about the secret subpoenas it has received from the F.B.I.\",\"copyright\":\"Tom Williams\\/CQ Roll Call, via Getty Images\"},{\"url\":\"https:\\/\\/static01.nyt.com\\/images\\/2019\\/09\\/20\\/multimedia\\/20NSLs-02\\/20NSLs-02-thumbLarge.jpg\",\"format\":\"thumbLarge\",\"height\":150,\"width\":150,\"type\":\"image\",\"subtype\":\"photo\",\"caption\":\"Richard Salgado, Google\\u2019s director of law enforcement and information security. The company has been public about the secret subpoenas it has received from the F.B.I.\",\"copyright\":\"Tom Williams\\/CQ Roll Call, via Getty Images\"},{\"url\":\"https:\\/\\/static01.nyt.com\\/images\\/2019\\/09\\/20\\/multimedia\\/20NSLs-02\\/merlin_161091351_4febfb63-cfee-4950-8433-3645e630b508-articleInline.jpg\",\"format\":\"Normal\",\"height\":127,\"width\":190,\"type\":\"image\",\"subtype\":\"photo\",\"caption\":\"Richard Salgado, Google\\u2019s director of law enforcement and information security. The company has been public about the secret subpoenas it has received from the F.B.I.\",\"copyright\":\"Tom Williams\\/CQ Roll Call, via Getty Images\"},{\"url\":\"https:\\/\\/static01.nyt.com\\/images\\/2019\\/09\\/20\\/multimedia\\/20NSLs-02\\/merlin_161091351_4febfb63-cfee-4950-8433-3645e630b508-mediumThreeByTwo210.jpg\",\"format\":\"mediumThreeByTwo210\",\"height\":140,\"width\":210,\"type\":\"image\",\"subtype\":\"photo\",\"caption\":\"Richard Salgado, Google\\u2019s director of law enforcement and information security. The company has been public about the secret subpoenas it has received from the F.B.I.\",\"copyright\":\"Tom Williams\\/CQ Roll Call, via Getty Images\"},{\"url\":\"https:\\/\\/static01.nyt.com\\/images\\/2019\\/09\\/20\\/multimedia\\/20NSLs-02\\/merlin_161091351_4febfb63-cfee-4950-8433-3645e630b508-superJumbo.jpg\",\"format\":\"superJumbo\",\"height\":1365,\"width\":2048,\"type\":\"image\",\"subtype\":\"photo\",\"caption\":\"Richard Salgado, Google\\u2019s director of law enforcement and information security. The company has been public about the secret subpoenas it has received from the F.B.I.\",\"copyright\":\"Tom Williams\\/CQ Roll Call, via Getty Images\"}],\"short_url\":\"https:\\/\\/nyti.ms\\/2AuBlfM\"}]}");
        mockWebServer.enqueue(mockResponse);
        mockWebServer.start();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("https://api.nytimes.com/svc/").toString())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final NyTimesService service = retrofit.create(NyTimesService.class);
        final Call<Article> articleCall = service.getTopStoriesTest("home","vYNxoAopAjLFANQNx7dMBKZDL8isrF9t");

        articleCall.enqueue(new Callback<Article>() {
            @Override
            public void onResponse(@NonNull Call<Article> call, @NonNull Response<Article> response) {
                assert response.body() != null;
                for (Result result : response.body().getResults()) {
                    events.offer(result.getTitle());
                    events.offer(result.getSection());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Article> call, @NonNull Throwable t) {

            }
        });

        Assert.assertThat(events.take(), is("Secret F.B.I. Subpoenas Scoop Up Personal Data From Scores of Companies"));
        Assert.assertThat(events.take(), is("U.S."));

        mockWebServer.shutdown();
    }
}