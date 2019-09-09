package com.sweethome.jimmy.mynews.Controllers.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.sweethome.jimmy.mynews.Models.Response;
import com.sweethome.jimmy.mynews.Models.SearchArticle;
import com.sweethome.jimmy.mynews.R;
import com.sweethome.jimmy.mynews.Utils.EditTextDatePicker;
import com.sweethome.jimmy.mynews.Utils.BroadCastReceiver;
import com.sweethome.jimmy.mynews.Utils.NyTimesStreams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static com.sweethome.jimmy.mynews.Utils.Tools.dateSearchFormatter;

public class SearchAndNotificationActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.searchActivity_editText_query)
    EditText editTextQuery;
    @BindView(R.id.searchActivity_dateRow)
    TableRow tableRowDate;
    @BindView(R.id.searchActivity_beginDate)
    EditText editTextBeginDate;
    @BindView(R.id.searchActivity_endDate)
    EditText editTextEndDate;
    @BindView(R.id.searchActivity_checkBox_arts)
    CheckBox checkBoxArts;
    @BindView(R.id.searchActivity_checkBox_politics)
    CheckBox checkBoxPolitics;
    @BindView(R.id.searchActivity_checkBox_business)
    CheckBox checkBoxBusiness;
    @BindView(R.id.searchActivity_checkBox_sports)
    CheckBox checkBoxSports;
    @BindView(R.id.searchActivity_checkBox_entrepreneurs)
    CheckBox checkBoxEntrepreneurs;
    @BindView(R.id.searchActivity_checkBox_travel)
    CheckBox checkBoxTravel;
    @BindView(R.id.searchActivity_button)
    Button buttonSearch;
    @BindView(R.id.searchActivity_switchNotification)
    Switch switchNotification;

    private String activityType;
    private Disposable disposable;
    private SharedPreferences sharedPref;
    private Gson gson = new Gson();
    private ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    private Boolean[] checkBoxesIsChecked = {false, false, false, false, false, false};
    private String checkBoxesSearchQuery;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_notification);

        sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        activityType = getIntent().getStringExtra("title");

        this.configureToolbar();
        ButterKnife.bind(this);
        EditTextDatePicker editTextDatePickerBeginDate = new EditTextDatePicker(SearchAndNotificationActivity.this, editTextBeginDate.getId());
        EditTextDatePicker editTextDatePickerEndDate = new EditTextDatePicker(SearchAndNotificationActivity.this, editTextEndDate.getId());
        checkBoxes.addAll(Arrays.asList(checkBoxArts, checkBoxPolitics, checkBoxBusiness, checkBoxSports, checkBoxEntrepreneurs, checkBoxTravel));

        checkBoxArts.setOnClickListener(this);
        checkBoxPolitics.setOnClickListener(this);
        checkBoxBusiness.setOnClickListener(this);
        checkBoxSports.setOnClickListener(this);
        checkBoxEntrepreneurs.setOnClickListener(this);
        checkBoxTravel.setOnClickListener(this);

        buttonSearch.setOnClickListener(this);
        switchNotification.setOnClickListener(this);

        setVisibleViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (this.getTitle().equals("Notification"))
            savePrefsNotification();
    }

    private void configureToolbar() {
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
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.add(Calendar.DAY_OF_YEAR, 1);

            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent alarmIntent = new Intent(this, BroadCastReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
            switchNotification.setVisibility(View.VISIBLE);
            this.setTitle("Notification");
            setNotificationFromPreferences();
        }
    }

    private void setNotificationFromPreferences() {
        if (sharedPref.getBoolean("notificationSwitch", false)) {
            switchNotification.setChecked(true);
        }
        if (!sharedPref.getString("checkBChecked", "").equals("")) {
            checkBoxesIsChecked = gson.fromJson(sharedPref.getString("checkBChecked", ""), Boolean[].class);
            int i = 0;
            for (CheckBox checkB : checkBoxes) {
                checkB.setChecked(checkBoxesIsChecked[i]);
                i++;
            }
        }
        if (!sharedPref.getString("query", "").equals("")) {
            editTextQuery.setText(sharedPref.getString("query", ""));
        }
    }


    @Override
    public void onClick(View view) {
        int checkBoxesChecked = 0;
        int i = 0;
        checkBoxesSearchQuery= "";
        for (CheckBox checkB : checkBoxes) {
            if (this.getTitle().equals("Notification")) {
                checkBoxesIsChecked[i] = checkB.isChecked();
                i++;
            }
            if (checkB.isChecked()) {
                checkBoxesChecked++;
                checkBoxesSearchQuery += checkB.getText().toString() + "+";
            }
        }
        switch (view.getId()) {
            case R.id.searchActivity_button:
                if (!editTextQuery.getText().toString().equals("") && checkBoxesChecked >= 1) {
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
                        String beginDate = dateSearchFormatter(editTextBeginDate.getText().toString());
                        String endDate = dateSearchFormatter(editTextEndDate.getText().toString());

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
                } else
                    Toast.makeText(this, "Need query and at least one box checked", Toast.LENGTH_LONG).show();
            case R.id.searchActivity_switchNotification:
                if (switchNotification.isChecked()) {
                    if (!editTextQuery.getText().toString().equals("") && checkBoxesChecked >= 1) {
                        if (this.getTitle().equals("Notification"))
                            startAlarm();
                    } else {
                        switchNotification.setChecked(false);
                        Toast.makeText(this, "Need query and at least 1 box", Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (this.getTitle().equals("Notification"))
                        cancelAlarm();
                }

            case R.id.searchActivity_checkBox_arts:
                checkBoxesIsChecked[0] = checkBoxArts.isChecked();
            case R.id.searchActivity_checkBox_politics:
                checkBoxesIsChecked[1] = checkBoxPolitics.isChecked();
            case R.id.searchActivity_checkBox_business:
                checkBoxesIsChecked[2] = checkBoxBusiness.isChecked();
            case R.id.searchActivity_checkBox_sports:
                checkBoxesIsChecked[3] = checkBoxSports.isChecked();
            case R.id.searchActivity_checkBox_entrepreneurs:
                checkBoxesIsChecked[4] = checkBoxEntrepreneurs.isChecked();
            case R.id.searchActivity_checkBox_travel:
                checkBoxesIsChecked[5] = checkBoxTravel.isChecked();
        }
        savePrefsNotification();
    }

    private void newActivityIfMatches(SearchArticle searchArticle) {
        if (searchArticle.getResponse().getMeta().getHits() >= 1) {
            Response response = searchArticle.getResponse();
            Gson gson = new Gson();
            String json = gson.toJson(response);
            Intent intent = new Intent(this, SearchActivity.class);
            intent.putExtra("ARTICLES_SEARCH", json);
            startActivity(intent);
        } else
            Toast.makeText(this, "No result found with this query", Toast.LENGTH_LONG).show();
    }

    private void disposeWhenDestroy() {
        if (disposable != null && disposable.isDisposed())
            disposable.dispose();
    }

    private void savePrefsNotification() {
        sharedPref.edit().putString("query", editTextQuery.getText().toString()).apply();
        String jsonCheckBBoolTab = gson.toJson(checkBoxesIsChecked);
        sharedPref.edit().putString("checkBChecked", jsonCheckBBoolTab).apply();
        sharedPref.edit().putString("categories", checkBoxesSearchQuery).apply();
        sharedPref.edit().putBoolean("notificationSwitch", switchNotification.isChecked()).apply();
    }

    private void startAlarm() {
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY , pendingIntent);
        Toast.makeText(getApplicationContext(), "Alarm Activated", Toast.LENGTH_LONG).show();
    }

    private void cancelAlarm() {
        alarmManager.cancel(pendingIntent);
        Toast.makeText(getApplicationContext(), "Alarm Cancelled", Toast.LENGTH_LONG).show();
    }
}
