package com.sweethome.jimmy.mynews.Controllers.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sweethome.jimmy.mynews.Models.Response;
import com.sweethome.jimmy.mynews.Models.SearchArticle;
import com.sweethome.jimmy.mynews.R;
import com.sweethome.jimmy.mynews.Utils.EditTextDatePicker;
import com.sweethome.jimmy.mynews.Utils.NyTimesStreams;

import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class SearchAndNotificationActivity extends AppCompatActivity implements View.OnClickListener {

    private String activityType;
    private Response response = new Response();
    private Disposable disposable;

    private EditText editTextQuery;
    private TableRow tableRowDate;
    private EditText editTextBeginDate;
    private EditTextDatePicker editTextDatePickerBeginDate;
    private EditText editTextEndDate;
    private EditTextDatePicker editTextDatePickerEndDate;

    private ArrayList<CheckBox> checkBoxes = new ArrayList<>();

    private CheckBox checkBoxArts;
    private CheckBox checkBoxPolitics;
    private CheckBox checkBoxBusiness;
    private CheckBox checkBoxSports;
    private CheckBox checkBoxEntrepreneurs;
    private CheckBox checkBoxTravel;
    private Button buttonSearch;
    private Switch switchNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_notification);

        activityType = getIntent().getStringExtra("title");

        this.configureToolbar();

        editTextQuery = findViewById(R.id.searchActivity_editText_query);
        tableRowDate = findViewById(R.id.searchActivity_dateRow);
        editTextBeginDate = findViewById(R.id.searchActivity_beginDate);
        editTextDatePickerBeginDate = new EditTextDatePicker(SearchAndNotificationActivity.this, editTextBeginDate.getId());
        editTextEndDate = findViewById(R.id.searchActivity_endDate);
        editTextDatePickerBeginDate = new EditTextDatePicker(SearchAndNotificationActivity.this, editTextEndDate.getId());
        checkBoxArts = findViewById(R.id.searchActivity_checkBox_arts);
        checkBoxPolitics = findViewById(R.id.searchActivity_checkBox_politics);
        checkBoxBusiness= findViewById(R.id.searchActivity_checkBox_business);
        checkBoxSports = findViewById(R.id.searchActivity_checkBox_sports);
        checkBoxEntrepreneurs = findViewById(R.id.searchActivity_checkBox_entrepreneurs);
        checkBoxTravel = findViewById(R.id.searchActivity_checkBox_travel);
        checkBoxes.addAll(Arrays.asList(checkBoxArts,checkBoxPolitics, checkBoxBusiness, checkBoxSports, checkBoxEntrepreneurs, checkBoxTravel));

        buttonSearch = findViewById(R.id.searchActivity_button);
        buttonSearch.setOnClickListener(this);
        switchNotification = findViewById(R.id.searchActivity_switchNotification);

        setVisibleViews();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    private void configureToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setVisibleViews() {
            if (activityType == null || activityType.equals("Search Articles")) {
                tableRowDate.setVisibility(View.VISIBLE);
                buttonSearch.setVisibility(View.VISIBLE);
                this.setTitle("Search Articles");
            } else {
                switchNotification.setVisibility(View.VISIBLE);
                this.setTitle("Notification");
            }
    }


    @Override
    public void onClick(View view) {
        int checkBoxesEnabled = 0;
        String checkBoxesSearchQuery = "";
        for (CheckBox checkB : checkBoxes) {
            if (checkB.isChecked()) {
                checkBoxesEnabled++;
                checkBoxesSearchQuery += checkB.getText().toString() + "+";
            }
        }
        if (!editTextQuery.getText().toString().equals("") && checkBoxesEnabled >= 1) {
            if (editTextBeginDate.getText().toString().equals("") || editTextEndDate.getText().toString().equals("")) {
                disposable = NyTimesStreams.streamFetchSearchArticles(editTextQuery.getText().toString(), checkBoxesSearchQuery).subscribeWith(new DisposableObserver<SearchArticle>() {
                    @Override
                    public void onNext(SearchArticle searchArticle) {
                        newActivityIfMatches(searchArticle);
                    }

                    @Override
                    public void onError(Throwable e) {
                        String msg = e.getMessage();
                        Log.e("Erreur 1 :", msg);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
            } else {
                // format date dd/MM/YYYY to YYYYMMdd
                String beginDate = "";
                String endDate = "";
                String[] beginDateTab = editTextBeginDate.getText().toString().split("/");
                String[] endDateTab = editTextEndDate.getText().toString().split("/");
                beginDate = beginDate + beginDateTab[2] + beginDateTab[1] + beginDateTab[0];
                endDate = endDate + endDateTab[2] + endDateTab[1] + endDateTab[0];

                disposable = NyTimesStreams.streamFetchSearchArticles(editTextQuery.getText().toString(), beginDate, endDate, checkBoxesSearchQuery).subscribeWith(new DisposableObserver<SearchArticle>() {
                    @Override
                    public void onNext(SearchArticle searchArticle) {
                        newActivityIfMatches(searchArticle);
                    }

                    @Override
                    public void onError(Throwable e) {
                        String msg = e.getMessage();
                        Log.e("Erreur 2 :", msg);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
            }
        } else Toast.makeText(this, "Need query and at least 1 box", Toast.LENGTH_LONG).show();
    }

    private void newActivityIfMatches(SearchArticle searchArticle) {
        if (searchArticle.getResponse().getMeta().getHits() >= 1) {
            response = searchArticle.getResponse();
            Gson gson = new Gson();
            String json = gson.toJson(response);
            Intent intent = new Intent(this, SearchActivity.class);
            intent.putExtra("ARTICLES_SEARCH", json);
            startActivity(intent);
        }
        else Toast.makeText(this, "No result found with this query", Toast.LENGTH_LONG).show();
    }

    private void disposeWhenDestroy() {
        if (disposable != null && disposable.isDisposed()) disposable.dispose();
    }
}
