package com.sweethome.jimmy.mynews.Controllers.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TableRow;

import com.sweethome.jimmy.mynews.R;
import com.sweethome.jimmy.mynews.Utils.EditTextDatePicker;

public class SearchActivity extends AppCompatActivity {

    private String activityType;

    private EditText editTextQuery;
    private TableRow tableRowDate;
    private EditText editTextBeginDate;
    private EditTextDatePicker editTextDatePickerBeginDate;
    private EditText editTextEndDate;
    private EditTextDatePicker editTextDatePickerEndDate;
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
        setContentView(R.layout.activity_search);
        activityType = getIntent().getStringExtra("title");
        this.setTitle(activityType);
        this.configureToolbar();

        editTextQuery = findViewById(R.id.searchActivity_editText_query);
        tableRowDate = findViewById(R.id.searchActivity_dateRow);
        editTextBeginDate = findViewById(R.id.searchActivity_beginDate);
        editTextDatePickerBeginDate = new EditTextDatePicker(SearchActivity.this, editTextBeginDate.getId());
        editTextEndDate = findViewById(R.id.searchActivity_endDate);
        editTextDatePickerBeginDate = new EditTextDatePicker(SearchActivity.this, editTextEndDate.getId());
        checkBoxArts = findViewById(R.id.searchActivity_checkBox_arts);
        checkBoxPolitics = findViewById(R.id.searchActivity_checkBox_politics);
        checkBoxBusiness= findViewById(R.id.searchActivity_checkBox_business);
        checkBoxSports = findViewById(R.id.searchActivity_checkBox_sports);
        checkBoxEntrepreneurs = findViewById(R.id.searchActivity_checkBox_entrepreneurs);
        checkBoxTravel = findViewById(R.id.searchActivity_checkBox_travel);
        buttonSearch = findViewById(R.id.searchActivity_button);
        switchNotification = findViewById(R.id.searchActivity_switchNotification);

        setVisibleViews();
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
        if(activityType.equals("Search Articles")) {
            tableRowDate.setVisibility(View.VISIBLE);
            buttonSearch.setVisibility(View.VISIBLE);
        }
        else switchNotification.setVisibility(View.VISIBLE);
    }

}
