package com.android.ocat.ui.study;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.ocat.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StudyReminderAddActivity extends AppCompatActivity{

    private Spinner spYear,spMonth, spDay;
    private EditText editText;
    private List<String> dataYear,dataMonth, dataDay;
    private ArrayAdapter<String> adapterSpYear,adapterSpMonth, adapterSpDay;
    private int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_reminder_add);

        spYear = findViewById(R.id.sp_year);
        spMonth = findViewById(R.id.sp_month);
        spDay = findViewById(R.id.sp_day);
        editText = findViewById(R.id.editText);
        dataYear = new ArrayList<>();
        dataMonth = new ArrayList<>();
        dataDay = new ArrayList<>();

        // 年份设定为当年的前后20年
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < 40; i++) {
            dataYear.add("" + (cal.get(Calendar.YEAR) - 20 + i));
        }
        adapterSpYear = new ArrayAdapter<String>(this, R.layout.spinner_item, dataYear);
        spYear.setAdapter(adapterSpYear);
        spYear.setSelection(20);// 默认选中今年


        for (int i = 1; i <= 12; i++) {
            dataMonth.add("" + (i < 10 ? "0" + i : i));
        }
        adapterSpMonth = new ArrayAdapter<String>(this, R.layout.spinner_item, dataMonth);
        spMonth.setAdapter(adapterSpMonth);
        spMonth.setSelection(Calendar.MONTH+1);

        adapterSpDay = new ArrayAdapter<String>(this, R.layout.spinner_item, dataDay);
        spDay.setAdapter(adapterSpDay);


        spMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                dataDay.clear();
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, Integer.valueOf(spYear.getSelectedItem().toString()));
                cal.set(Calendar.MONTH, arg2);
                int dayofm = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                for (int i = 1; i <= dayofm; i++) {
                    dataDay.add("" + (i < 10 ? "0" + i : i));
                }
                adapterSpDay.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        Intent i = getIntent();
        noteId = i.getIntExtra("taskId", -1);
        if (noteId != -1) {
            editText.setText(StudyReminderToDoListFragment.tasks.get(noteId));
        }
    }

    public void addTask(View view) {
        String year = spYear.getSelectedItem().toString();
        String month = spMonth.getSelectedItem().toString();
        String day = spDay.getSelectedItem().toString();
        String value = editText.getText().toString();

        if (noteId != -1) {
            StudyReminderToDoListFragment.tasks.set(noteId, value);
        } else {
            StudyReminderToDoListFragment.tasks.add(value);
        }
        StudyReminderToDoListFragment.arrayAdapter.notifyDataSetChanged();
        finish();
    }
}
