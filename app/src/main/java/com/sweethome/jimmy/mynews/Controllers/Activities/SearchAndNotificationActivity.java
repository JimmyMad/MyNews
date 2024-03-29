package com.sweethome.jimmy.mynews.Controllers.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.sweethome.jimmy.mynews.Utils.BroadCastReceiver;
import com.sweethome.jimmy.mynews.Utils.EditTextDatePicker;
import com.sweethome.jimmy.mynews.Utils.NyTimesStreams;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

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
        EditTextDatePicker editTextDatePickerBeginDate = new EditTextDatePicker(this, editTextBeginDate.getId());
        EditTextDatePicker editTextDatePickerEndDate = new EditTextDatePicker(this, editTextEndDate.getId());
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

    //Toolbar
    private void configureToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    // Setting up content for either the search or the notification
    private void setVisibleViews() {
        if (activityType == null || activityType.equals("Search Articles")) {
            tableRowDate.setVisibility(View.VISIBLE);
            buttonSearch.setVisibility(View.VISIBLE);
            this.setTitle("Search Articles");
        } else {
            // Some set up for the alarm notification
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

    // Retrieving data from preferences
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
        // Counter for number of checkBoxes checked
        int checkBoxesCheckedCount = 0;
        checkBoxesCheckedCount = checkBoxesCheckedCount(checkBoxesCheckedCount);
        switch (view.getId()) {
            case R.id.searchActivity_button:
                // Check if we have a query and at least 1 checkBox checked
                if (!editTextQuery.getText().toString().equals("") && checkBoxesCheckedCount >= 1) {
                    // Check if we have dates for the request
                    if (editTextBeginDate.getText().toString().equals("") || editTextEndDate.getText().toString().equals("")) {
                        // Request without the dates
                        disposable = NyTimesStreams.streamFetchSearchArticles(editTextQuery.getText().toString(), checkBoxesSearchQuery).subscribeWith(new DisposableObserver<SearchArticle>() {
                            @Override
                            public void onNext(SearchArticle searchArticle) {
                                newActivityIfMatches(searchArticle);
                            }

                            @Override
                            public void onError(Throwable e) {
                            }

                            @Override
                            public void onComplete() {
                            }
                        });
                    } else {
                        String beginDateS = dateSearchFormatter(editTextBeginDate.getText().toString());
                        String endDateS = dateSearchFormatter(editTextEndDate.getText().toString());
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date beginDate = null;
                        Date endDate = null;
                        try {
                            beginDate = dateFormat.parse(beginDateS);
                            endDate = dateFormat.parse(endDateS);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        // Checks if the begin date isn't after end date
                        if (endDate != null && beginDate != null && beginDate.getTime() <= endDate.getTime()) {
                            // Request with the dates
                            disposable = NyTimesStreams.streamFetchSearchArticles(editTextQuery.getText().toString(), beginDateS, endDateS, checkBoxesSearchQuery).subscribeWith(new DisposableObserver<SearchArticle>() {
                                @Override
                                public void onNext(SearchArticle searchArticle) {
                                    newActivityIfMatches(searchArticle);
                                }

                                @Override
                                public void onError(Throwable e) {
                                }

                                @Override
                                public void onComplete() {
                                }
                            });
                        } else {
                            Toast.makeText(this, R.string.ErrorDatesIncorrect, Toast.LENGTH_LONG).show();
                        }
                    }
                    } else
                    Toast.makeText(this, R.string.ErrorNotEnoughInformation, Toast.LENGTH_LONG).show();
            case R.id.searchActivity_switchNotification:
                if (switchNotification.isChecked()) {
                    // Check if have a query and at least 1 checkBox checked for notification
                    if (!editTextQuery.getText().toString().equals("") && checkBoxesCheckedCount >= 1) {
                        if (this.getTitle().equals("Notification"))
                            startAlarm();
                    } else {
                        switchNotification.setChecked(false);
                        Toast.makeText(this, R.string.ErrorNotEnoughInformation, Toast.LENGTH_LONG).show();
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
        if (this.getTitle().equals("Notification"))
            savePrefsNotification();
    }

    // If the request finds articles, we start a new activity to show them
    private void newActivityIfMatches(SearchArticle searchArticle) {
        if (searchArticle.getResponse().getMeta().getHits() >= 1) {
            Response response = searchArticle.getResponse();
            Gson gson = new Gson();
            String json = gson.toJson(response);
            Intent intent = new Intent(this, SearchActivity.class);
            intent.putExtra("ARTICLES_SEARCH", json);
            startActivity(intent);
        } else
            Toast.makeText(this, R.string.NoResultFound, Toast.LENGTH_LONG).show();
    }

    private void disposeWhenDestroy() {
        if (disposable != null && disposable.isDisposed())
            disposable.dispose();
    }

    // Saves the preferences for the notification
    private void savePrefsNotification() {
        sharedPref.edit().putString("query", editTextQuery.getText().toString()).apply();
        String jsonCheckBBoolTab = gson.toJson(checkBoxesIsChecked);
        sharedPref.edit().putString("checkBChecked", jsonCheckBBoolTab).apply();
        sharedPref.edit().putString("categories", checkBoxesSearchQuery).apply();
        sharedPref.edit().putBoolean("notificationSwitch", switchNotification.isChecked()).apply();
    }

    // Starts the alarm for the notification
    private void startAlarm() {
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY , pendingIntent);
        Toast.makeText(getApplicationContext(), R.string.NotificationActivated, Toast.LENGTH_LONG).show();
    }

    // Cancels the alarm for the notification
    private void cancelAlarm() {
        alarmManager.cancel(pendingIntent);
        Toast.makeText(getApplicationContext(), R.string.NotificationCancelled, Toast.LENGTH_LONG).show();
    }

    // Count how many checkBoxes are checked and provide a string for the request
    public int checkBoxesCheckedCount(int checkBoxesCheckedCount) {
        int i = 0;
        checkBoxesSearchQuery= "";
        for (CheckBox checkB : checkBoxes) {
            if (this.getTitle().equals("Notification")) {
                checkBoxesIsChecked[i] = checkB.isChecked();
                i++;
            }
            if (checkB.isChecked()) {
                checkBoxesCheckedCount++;
                checkBoxesSearchQuery += checkB.getText().toString() + "+";
            }
        }
        return checkBoxesCheckedCount;
    }
}
