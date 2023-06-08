package com.example.dbproject.reservationApp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dbproject.R;
import com.example.dbproject.DB_TABLE.ITEM_CATEGORY;
import com.example.dbproject.tabLayer.DBHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ScheduleActivity extends AppCompatActivity {
    public Integer studentID = 190012345;
    private TextView textViewSelectedDate;
    private TextView schedule_item_name;
    private TextView schedule_count;
    private Button schedule_confirm_button;
    private PopupWindow popupWindow;
    private DatePicker datePicker;
    private Calendar calendar;
    private DBHelper dbHelper;
    private ITEM_CATEGORY categoryItem;
    private static final String TAG = "ScheduleActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation_schedule);
        dbHelper = new DBHelper(this);
        categoryItem = new ITEM_CATEGORY();
        // 인텐트에서 값을 추출하기 위해 getIntent() 메서드를 사용합니다.
        Intent intent = getIntent();

        // getStringExtra() 메서드를 사용하여 "item_name"이라는 이름의 문자열 값을 추출합니다.
        String itemCategory = intent.getStringExtra("item_category");
        String itemLocation = intent.getStringExtra("item_location");
        Log.i(TAG, "onCreate: " + " itemCategory: " + itemCategory + " itemLocation: " + itemLocation);

        textViewSelectedDate = findViewById(R.id.textViewSelectedDate);
        schedule_item_name = findViewById(R.id.schedule_item_name);
        schedule_count = findViewById(R.id.schedule_count);

        calendar = Calendar.getInstance();

        schedule_item_name.setText(itemCategory);

        textViewSelectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDatePickerPopup(itemCategory, itemLocation);
            }
        });
    }

    private void showDatePickerPopup(String itemCategory, String itemLocation) {
        Log.i(TAG, "showDatePickerPopup: " + " itemCategory: " + itemCategory + " itemLocation: " + itemLocation);

        // 팝업 윈도우 초기화
        LayoutInflater inflater = LayoutInflater.from(this);
        View popupView = inflater.inflate(R.layout.popup_date_picker, null);
        popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        // 팝업 윈도우 속성 설정
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(textViewSelectedDate, Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);


        // DatePicker 초기화
        datePicker = popupView.findViewById(R.id.datePicker);
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String _itemCategory = itemCategory;
                        String _itemLocation = itemLocation;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        String selectedDate = sdf.format(calendar.getTime());
                        schedule_confirm_button = findViewById(R.id.schedule_confirm_button);


                        categoryItem = dbHelper.getReservationItem(_itemCategory, _itemLocation, selectedDate);
                        if(categoryItem != null) {
                            schedule_count.setText("총 개수 / 남은 개수 : " +
                                    categoryItem.getItem_total_amount() +
                                    " / " +
                                    categoryItem.getItem_left_amount());
                        } else {
                            schedule_count.setText("총 개수 / 남은 개수 : 0 / 0");
                        }
                        textViewSelectedDate.setText("Selected Date: " + selectedDate);
                        schedule_confirm_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(categoryItem.getItem_left_amount() > 0) {
                                    Integer itemId = dbHelper.selectItemId_For_ReservationItem(categoryItem.getItem_category(), categoryItem.getItem_location());
                                    dbHelper.UpdateItem("예약중", itemId);
                                    dbHelper.InsertReservation(190012345, itemId, selectedDate, getCurrentTime(), null, null, "예약중");

                                        Toast.makeText(ScheduleActivity.this, "예약이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ScheduleActivity.this, "예약 가능한 아이템이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                                }
                                finish();
                            }

                        });
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 액티비티 종료 시 팝업 윈도우 해제
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
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
