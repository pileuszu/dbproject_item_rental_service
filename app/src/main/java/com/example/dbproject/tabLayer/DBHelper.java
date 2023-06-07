package com.example.dbproject.tabLayer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import com.example.dbproject.homeApp.historyApp.HISTORY;
import com.example.dbproject.homeApp.NOTICE;
import com.example.dbproject.proposalApp.PROPOSAL;
import com.example.dbproject.rentalApp.ITEM_CATEGORY;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "datahbaawe.db";
    private final String databaseIdentifier;


    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.databaseIdentifier = null;
    }

    public DBHelper(@Nullable Context context, String databaseIdentifier) {
        super(context, DB_NAME, null, DB_VERSION);
        this.databaseIdentifier = databaseIdentifier;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 데이터 베이스가 생성이 될 때 호출
        db.execSQL("CREATE TABLE IF NOT EXISTS NOTICE (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, content TEXT, writer TEXT, date TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS STUDENT (id INTEGER PRIMARY KEY AUTOINCREMENT, pw TEXT, name TEXT, dp1 TEXT, dp2 TEXT, sDate TEXT, status TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS ITEM (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, category TEXT, location TEXT, type TEXT, status TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS RENTAL (id INTEGER PRIMARY KEY AUTOINCREMENT, student_id INTEGER, item_id INTEGER, start_date TEXT, expired_date TEXT, return_date TEXT, extend TEXT, return TEXT, FOREIGN KEY (student_id) REFERENCES STUDENT(id) ON UPDATE CASCADE ON DELETE NO ACTION, FOREIGN KEY (item_id) REFERENCES ITEM(id) ON UPDATE CASCADE ON DELETE NO ACTION)");
        db.execSQL("CREATE TABLE IF NOT EXISTS PROPOSAL (id INTEGER PRIMARY KEY AUTOINCREMENT, student_id INTEGER, content TEXT, date TEXT, time TEXT, FOREIGN KEY (student_id) REFERENCES STUDENT(id) ON UPDATE CASCADE ON DELETE NO ACTION)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    // SELECT 문
    public ArrayList<NOTICE> getNoticeList() {
        ArrayList<NOTICE> noticeItems = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM NOTICE", null);
        if(cursor.getCount() != 0) {
            // 조회된 데이터가 있을 때 내부 수행
            int titleIndex = cursor.getColumnIndex("title");
            int contentIndex = cursor.getColumnIndex("content");
            int writerIndex = cursor.getColumnIndex("writer");
            int dateIndex = cursor.getColumnIndex("date");

            while (cursor.moveToNext()) {
                String title = cursor.getString(titleIndex);
                String content = cursor.getString(contentIndex);
                String writer = cursor.getString(writerIndex);
                String date = cursor.getString(dateIndex);

                NOTICE notice = new NOTICE();
                notice.setTitle(title);
                notice.setContent(content);
                notice.setWriter(writer);
                notice.setDate(date);
                noticeItems.add(notice);
            }
        }
        cursor.close();

        return noticeItems;
    }

    public ArrayList<HISTORY> getHistoryList(Integer student_id) {
        ArrayList<HISTORY> historyItems = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String[] selectionArgs = {String.valueOf(student_id)};
        Cursor cursor = db.rawQuery("SELECT RENTAL.*, ITEM.name AS item_name\n" +
                "FROM RENTAL\n" +
                "JOIN ITEM ON RENTAL.item_id = ITEM.id\n" +
                "WHERE RENTAL.student_id = ?;", selectionArgs);
        if(cursor.getCount() != 0) {
            // 조회된 데이터가 있을 때 내부 수행
            int history_id_Index = cursor.getColumnIndex("id");
            int history_item_id_Index = cursor.getColumnIndex("item_id");
            int history_item_name_Index = cursor.getColumnIndex("item_name");
            int history_start_date_Index = cursor.getColumnIndex("start_date");
            int history_return_date_Index = cursor.getColumnIndex("return_date");
            int history_return_state_Index = cursor.getColumnIndex("return");

            while (cursor.moveToNext()) {
                Integer history_id = cursor.getInt(history_id_Index);
                Integer item_id = cursor.getInt(history_item_id_Index);
                String history_item_name = cursor.getString(history_item_name_Index);
                String history_start_date = cursor.getString(history_start_date_Index);
                String history_return_date = cursor.getString(history_return_date_Index);
                String history_return_state = cursor.getString(history_return_state_Index);

                HISTORY history = new HISTORY();
                history.setHistory_id(history_id);
                history.setHistory_item_id(item_id);
                history.setHistory_item_name(history_item_name);
                history.setHistory_start_date(history_start_date);
                history.setHistory_return_date(history_return_date);
                history.setHistory_return_state(history_return_state);
                historyItems.add(history);
            }
        }
        cursor.close();

        return historyItems;
    }

    public ArrayList<PROPOSAL> getProposalList(Integer student_id) {
        ArrayList<PROPOSAL> proposalItems = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String[] selectionArgs = {String.valueOf(student_id)};
        Cursor cursor = db.rawQuery("SELECT * FROM PROPOSAL WHERE PROPOSAL.student_id = ? ORDER BY id DESC", selectionArgs);
        if(cursor.getCount() != 0) {
            // 조회된 데이터가 있을 때 내부 수행
            int proposal_id_Index = cursor.getColumnIndex("id");
            int proposal_content_Index = cursor.getColumnIndex("content");
            int proposal_date_Index = cursor.getColumnIndex("date");
            int proposal_time_Index = cursor.getColumnIndex("time");

            while (cursor.moveToNext()) {
                Integer proposal_id = cursor.getInt(proposal_id_Index);
                String proposal_content = cursor.getString(proposal_content_Index);
                String proposal_date = cursor.getString(proposal_date_Index);
                String proposal_time = cursor.getString(proposal_time_Index);

                PROPOSAL proposal = new PROPOSAL();
                proposal.setId(proposal_id);
                proposal.setContent(proposal_content);
                proposal.setDate(proposal_date);
                proposal.setTime(proposal_time);
                proposalItems.add(proposal);
            }
        }
        cursor.close();

        return proposalItems;
    }

    public ArrayList<ITEM_CATEGORY> getItem_Category_List(String location) {
        ArrayList<ITEM_CATEGORY> categoryItems = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String[] selectionArgs = {String.valueOf(location)};
        Cursor cursor = db.rawQuery("SELECT category, location, COUNT(*) AS total_count, COUNT(CASE WHEN status = '대여 가능' THEN 1 END) AS available_count\n" +
                "FROM ITEM\n" +
                "WHERE location = ?\n" +
                "GROUP BY category, location\n" +
                "HAVING COUNT(*) > 1", selectionArgs);
        if(cursor.getCount() != 0) {
            // 조회된 데이터가 있을 때 내부 수행
            int category_Index = cursor.getColumnIndex("category");
            int location_Index = cursor.getColumnIndex("location");
            int total_count_Index = cursor.getColumnIndex("total_count");
            int available_count_Index = cursor.getColumnIndex("available_count");

            while (cursor.moveToNext()) {
                String _category = cursor.getString(category_Index);
                String _location = cursor.getString(location_Index);
                Integer _total_count = cursor.getInt(total_count_Index);
                Integer _available_count = cursor.getInt(available_count_Index);

                ITEM_CATEGORY item_category = new ITEM_CATEGORY();
                item_category.setItem_category(_category);
                item_category.setItem_location(_location);
                item_category.setItem_total_amount(_total_count);
                item_category.setItem_left_amount(_available_count);
                categoryItems.add(item_category);
            }
        }
        cursor.close();

        return categoryItems;
    }


    // INSERT 문
    public void InsertNotice(String _title, String _content, String _writer, String _date) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO NOTICE (title, content, writer, date) VALUES('" + _title + "','" + _content + "','" + _writer + "','" + _date + "');");
    }
    public void InsertStudent(Integer _id, String _pw, String _name, String _dp1, String _dp2, String _sDate, String _status) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO STUDENT (id, pw, name, dp1, dp2, sDate, status) VALUES('" + _id + "','" + _pw + "','" + _name + "','" + _dp1 + "','" + _dp2 + "','" + _sDate + "','" + _status + "');");
    }
    public void InsertStudent(String _pw, String _name, String _dp1, String _dp2, String _sDate, String _status) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO STUDENT (pw, name, dp1, dp2, sDate, status) VALUES('" + _pw + "','" + _name + "','" + _dp1 + "','" + _dp2 + "','" + _sDate + "','" + _status + "');");
    }
    public void InsertItem(String _name, String _category, String _location, String _type, String _status) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO ITEM (name, category, location, type, status) VALUES('" + _name + "','" + _category + "','" + _location + "','" + _type + "','" + _status + "');");
    }
    public void InsertRental(Integer _student_id, Integer _item_id, String _start_date, String _expired_date, String _return_date, String _extend, String _return) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO RENTAL (student_id, item_id, start_date, expired_date, return_date, extend, return) VALUES('" + _student_id + "','" + _item_id + "','" + _start_date + "','" + _expired_date + "','" + _return_date + "','" + _extend + "','" + _return + "');");
    }
    public void InsertProposal(Integer _student_id, String _content, String _date, String _time) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO PROPOSAL (student_id, content, date, time) VALUES('" + _student_id + "','" + _content + "', '" + _date + "', '" + _time + "')");
    }

    // UPDATE 문
    public void UpdateNotice(String _title, String _content, String _writer, String _date, String _beforeData) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE NOTICE SET title='" + _title + "', content='" + _content + "', writer='" + _writer + "', date='" + _date + "', WHERE date='" + _beforeData + "'");
    }
    public void updateInquiryList(List<String> inquiryList, ArrayAdapter<String> adapter) {
        inquiryList.clear();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM PROPOSAL ORDER BY id DESC", null);
            if (cursor != null && cursor.getCount() > 0) {
                int proposal_id_Index = cursor.getColumnIndex("id");
                int proposal_content_Index = cursor.getColumnIndex("item_id");
                int proposal_date_Index = cursor.getColumnIndex("item_name");
                int proposal_time_Index = cursor.getColumnIndex("start_date");

                while (cursor.moveToNext()) {
                    int id = cursor.getInt(proposal_id_Index);
                    String content = cursor.getString(proposal_content_Index);
                    String date = cursor.getString(proposal_date_Index);
                    String time = cursor.getString(proposal_time_Index);
                    String inquiry = "[" + id + "]   " + "( " + date + " " + time + " )" + "\n\n" + content;
                    inquiryList.add(inquiry);
                }
            }
        } catch (Exception e) {
            // 예외 처리
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        adapter.notifyDataSetChanged();
    }




    // DELETE 문
    public void DeleteNotice(int _id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM NOTICE WHERE id = '" + _id + "'");
    }

}