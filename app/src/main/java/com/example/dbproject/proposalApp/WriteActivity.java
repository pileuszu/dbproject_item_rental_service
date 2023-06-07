package com.example.dbproject.proposalApp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dbproject.R;
import com.example.dbproject.tabLayer.DBHelper;

public class WriteActivity extends AppCompatActivity {
    public Integer studentID = 190012345;
    private EditText inquiryEditText;
    private Button confirmButton;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proposal_write);

        inquiryEditText = findViewById(R.id.inquiry_edit_text);
        confirmButton = findViewById(R.id.confirm_button);

        dbHelper = new DBHelper(this);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inquiry = inquiryEditText.getText().toString();
                saveInquiryToDatabase(inquiry);
            }
        });
    }

    private void saveInquiryToDatabase(String inquiry) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dbHelper.InsertProposal(studentID, inquiry, getCurrentDate(), getCurrentTime());
        db.close();
        setResult(RESULT_OK, new Intent());
        finish();
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date currentDate = new Date();
        return dateFormat.format(currentDate);
    }

    private String getCurrentTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date currentTime = new Date();
        return timeFormat.format(currentTime);
    }
}