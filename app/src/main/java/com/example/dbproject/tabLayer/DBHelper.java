package com.example.dbproject.tabLayer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import com.example.dbproject.DB_TABLE.ITEM_CATEGORY;
import com.example.dbproject.DB_TABLE.NOTICE;
import com.example.dbproject.DB_TABLE.PROPOSAL;
import com.example.dbproject.historyApp.HISTORY;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "datahbaawe.db";
    private final String databaseIdentifier;
    private static final String TAG = "DBHelper";

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
        db.execSQL("CREATE TABLE IF NOT EXISTS NOTICE (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, content TEXT, writer TEXT, date TEXT, time TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS STUDENT (id INTEGER PRIMARY KEY AUTOINCREMENT, pw TEXT, name TEXT, division TEXT, department TEXT, penalty_date TEXT, penalty_state TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS ITEM (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT UNIQUE, category TEXT, location TEXT, type TEXT, state TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS RENTAL (id INTEGER PRIMARY KEY AUTOINCREMENT, student_id INTEGER, item_id INTEGER, start_date TEXT, start_time TEXT, return_date TEXT, return_time TEXT, rental_state TEXT, rental_extend INTEGER, FOREIGN KEY (student_id) REFERENCES STUDENT(id) ON UPDATE CASCADE ON DELETE NO ACTION, FOREIGN KEY (item_id) REFERENCES ITEM(id) ON UPDATE CASCADE ON DELETE NO ACTION)");
        db.execSQL("CREATE TABLE IF NOT EXISTS RESERVATION (id INTEGER PRIMARY KEY AUTOINCREMENT, student_id INTEGER, item_id INTEGER, start_date TEXT, start_time TEXT, return_date TEXT, return_time TEXT, reservation_state TEXT, FOREIGN KEY (student_id) REFERENCES STUDENT(id) ON UPDATE CASCADE ON DELETE NO ACTION, FOREIGN KEY (item_id) REFERENCES ITEM(id) ON UPDATE CASCADE ON DELETE NO ACTION)");
        db.execSQL("CREATE TABLE IF NOT EXISTS PROPOSAL (id INTEGER PRIMARY KEY AUTOINCREMENT, student_id INTEGER, title TEXT, content TEXT, write_date TEXT, write_time TEXT, response_state TEXT, response_content TEXT, FOREIGN KEY (student_id) REFERENCES STUDENT(id) ON UPDATE CASCADE ON DELETE NO ACTION)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    // SELECT 문
    public ArrayList<NOTICE> getNoticeList() {
        ArrayList<NOTICE> noticeItems = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM NOTICE ORDER BY id DESC", null);
        if(cursor.getCount() != 0) {
            // 조회된 데이터가 있을 때 내부 수행
            int idIndex = cursor.getColumnIndex("id");
            int titleIndex = cursor.getColumnIndex("title");
            int contentIndex = cursor.getColumnIndex("content");
            int writerIndex = cursor.getColumnIndex("writer");
            int dateIndex = cursor.getColumnIndex("date");
            int timeIndex = cursor.getColumnIndex("time");

            while (cursor.moveToNext()) {
                Integer id = cursor.getInt(idIndex);
                String title = cursor.getString(titleIndex);
                String content = cursor.getString(contentIndex);
                String writer = cursor.getString(writerIndex);
                String date = cursor.getString(dateIndex);
                String time = cursor.getString(timeIndex);

                NOTICE notice = new NOTICE();
                notice.setId(id);
                notice.setTitle(title);
                notice.setContent(content);
                notice.setWriter(writer);
                notice.setDate(date);
                notice.setTime(time);
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
                proposal.setWrite_date(proposal_date);
                proposal.setWrite_time(proposal_time);
                proposalItems.add(proposal);
            }
        }
        cursor.close();

        return proposalItems;
    }

    public ArrayList<ITEM_CATEGORY> getRentalItem_Category_List(String location) {
        ArrayList<ITEM_CATEGORY> categoryItems = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String[] selectionArgs = {String.valueOf(location)};
        Cursor cursor = db.rawQuery("SELECT category, location, COUNT(*) AS total_count, COUNT(CASE WHEN status = '대여 가능' THEN 1 END) AS available_count " +
                "FROM ITEM " +
                "WHERE location = ? AND type = 'RENTAL'" +
                "GROUP BY category, location", selectionArgs);
        if(cursor.getCount() != 0) {
            Log.i(TAG, "cursor.getCount() != 0");
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
        } else if (cursor.getCount() == 0) {
            Log.i(TAG, "cursor.getCount() == 0");
        }
        cursor.close();

        return categoryItems;
    }
    public ArrayList<ITEM_CATEGORY> getReservationItem_Category_List(String location) {
        ArrayList<ITEM_CATEGORY> categoryItems = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String[] selectionArgs = {String.valueOf(location)};
        Cursor cursor = db.rawQuery("SELECT category, location, COUNT(*) AS total_count, COUNT(CASE WHEN status = '대여 가능' THEN 1 END) AS available_count " +
                "FROM ITEM " +
                "WHERE location = ? AND type = 'Reservation'" +
                "GROUP BY category, location", selectionArgs);
        if(cursor.getCount() != 0) {
            Log.i(TAG, "cursor.getCount() != 0");
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
        } else if (cursor.getCount() == 0) {
            Log.i(TAG, "cursor.getCount() == 0");
        }
        cursor.close();

        return categoryItems;
    }
    public ITEM_CATEGORY getReservationItem(String category, String location, String date) {
        ITEM_CATEGORY categoryItems = new ITEM_CATEGORY();
        SQLiteDatabase db = getReadableDatabase();
        String[] selectionArgs = {String.valueOf(date), String.valueOf(location), String.valueOf(category)};
        Cursor cursor = db.rawQuery("SELECT ITEM.category, ITEM.location, COUNT(*) AS total_count,\n" +
                "       SUM(CASE WHEN ITEM.status = '예약 가능' OR (ITEM.status = '예약중' AND (RESERVATION.start_date != ? OR RESERVATION.start_date IS NULL)) THEN 1 ELSE 0 END) AS available_count\n" +
                "FROM ITEM\n" +
                "LEFT JOIN RESERVATION ON ITEM.ID = RESERVATION.item_id\n" +
                "WHERE ITEM.location = ? AND ITEM.category = ? AND ITEM.type = 'Reservation'\n" +
                "GROUP BY ITEM.category, ITEM.location;\n", selectionArgs);
        if (cursor.getCount() != 0) {
            Log.i(TAG, "cursor.getCount() != 0");
            // 조회된 데이터가 있을 때 내부 수행
            int categoryIndex = cursor.getColumnIndex("category");
            int locationIndex = cursor.getColumnIndex("location");
            int totalCountIndex = cursor.getColumnIndex("total_count");
            int availableCountIndex = cursor.getColumnIndex("available_count");

            while (cursor.moveToNext()) {
                String _category = cursor.getString(categoryIndex);
                String _location = cursor.getString(locationIndex);
                Integer _totalCount = cursor.getInt(totalCountIndex);
                Integer _availableCount = cursor.getInt(availableCountIndex);

                categoryItems.setItem_category(_category);
                categoryItems.setItem_location(_location);
                categoryItems.setItem_total_amount(_totalCount);
                categoryItems.setItem_left_amount(_availableCount);
            }
        } else if (cursor.getCount() == 0) {
            Log.i(TAG, "cursor.getCount() == 0");
        }
        cursor.close();

        return categoryItems;
    }

    public Integer selectItemId_For_RentalItem(String _category, String _location) {
        List<Integer> matchingItemIds = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String[] selectionArgs = {String.valueOf(_category), String.valueOf(_location)};
        Cursor cursor = db.rawQuery("SELECT id FROM ITEM WHERE category = ? AND location = ? AND type = 'Rental'", selectionArgs);
        if(cursor.getCount() != 0) {
            int id_Index = cursor.getColumnIndex("id");
            while (cursor.moveToNext()) {
                Integer _id = cursor.getInt(id_Index);
                matchingItemIds.add(_id);
            }
        }
        cursor.close();
        if (matchingItemIds.size() > 0) {
            Random random = new Random();
            int randomIndex = random.nextInt(matchingItemIds.size());
            Log.i(TAG, "selectItemId_For_RentalItem Success!");
            return matchingItemIds.get(randomIndex);
        } else {
            return null;
        }
    }
    public Integer selectItemId_For_ReservationItem(String _category, String _location) {
        List<Integer> matchingItemIds = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String[] selectionArgs = {String.valueOf(_category), String.valueOf(_location)};
        Cursor cursor = db.rawQuery("SELECT id FROM ITEM WHERE category = ? AND location = ? AND type = 'Reservation'", selectionArgs);
        if(cursor.getCount() != 0) {
            int id_Index = cursor.getColumnIndex("id");
            while (cursor.moveToNext()) {
                Integer _id = cursor.getInt(id_Index);
                matchingItemIds.add(_id);
            }
        }
        cursor.close();
        if (matchingItemIds.size() > 0) {
            Random random = new Random();
            int randomIndex = random.nextInt(matchingItemIds.size());
            Log.i(TAG, "selectItemId_For_ReservationItem Success!");
            return matchingItemIds.get(randomIndex);
        } else {
            return null;
        }
    }
    public void InsertNoticeDummy() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("insert into NOTICE (id, title, content, writer, date, time) values (2, 'vulputate elementum nullam varius nulla facilisi cras', 'In hac habitasse platea dictumst. Maecenas ut massa quis augue luctus tincidunt. Nulla mollis molestie lorem. Quisque ut erat. Curabitur gravida nisi at nibh. In hac habitasse platea dictumst. Aliquam augue quam, sollicitudin vitae, consectetuer eget, rutrum at, lorem. Integer tincidunt ante vel ipsum. Praesent blandit lacinia erat. Vestibulum sed magna at nunc commodo placerat. Praesent blandit. Nam nulla. Integer pede justo, lacinia eget, tincidunt eget, tempus vel, pede. Morbi porttitor lorem id ligula. Suspendisse ornare consequat lectus. In est risus, auctor sed, tristique in, tempus sit amet, sem.', 'duis mattis egestas metus aenean', '2022-09-01', '9:11');");
        db.execSQL("insert into NOTICE (id, title, content, writer, date, time) values (3, 'platea dictumst aliquam augue quam sollicitudin vitae', 'Maecenas tristique, est et tempus semper, est quam pharetra magna, ac consequat metus sapien ut nunc. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Mauris viverra diam vitae quam. Suspendisse potenti. Nullam porttitor lacus at turpis. Donec posuere metus vitae ipsum. Aliquam non mauris. Morbi non lectus. Aliquam sit amet diam in magna bibendum imperdiet. Nullam orci pede, venenatis non, sodales sed, tincidunt eu, felis. Fusce posuere felis sed lacus.', 'ipsum dolor sit amet consectetuer adipiscing', '2023-02-23', '0:49');");
        db.execSQL("insert into NOTICE (id, title, content, writer, date, time) values (4, 'venenatis tristique fusce congue diam id ornare', 'Nulla ut erat id mauris vulputate elementum. Nullam varius. Nulla facilisi. Cras non velit nec nisi vulputate nonummy. Maecenas tincidunt lacus at velit. Vivamus vel nulla eget eros elementum pellentesque. Quisque porta volutpat erat. Quisque erat eros, viverra eget, congue eget, semper rutrum, nulla. Nunc purus. Phasellus in felis. Donec semper sapien a libero. Nam dui. Proin leo odio, porttitor id, consequat in, consequat ut, nulla.', 'rhoncus mauris enim leo rhoncus sed vestibulum', '2022-06-08', '21:02');\n");
        db.execSQL("insert into NOTICE (id, title, content, writer, date, time) values (5, 'est donec odio justo sollicitudin', 'Donec ut dolor. Morbi vel lectus in quam fringilla rhoncus. Mauris enim leo, rhoncus sed, vestibulum sit amet, cursus id, turpis. Integer aliquet, massa id lobortis convallis, tortor risus dapibus augue, vel accumsan tellus nisi eu orci. Mauris lacinia sapien quis libero. Nullam sit amet turpis elementum ligula vehicula consequat. Morbi a ipsum. Integer a nibh. In quis justo. Maecenas rhoncus aliquam lacus. Morbi quis tortor id nulla ultrices aliquet. Maecenas leo odio, condimentum id, luctus nec, molestie sed, justo. Pellentesque viverra pede ac diam. Cras pellentesque volutpat dui. Maecenas tristique, est et tempus semper, est quam pharetra magna, ac consequat metus sapien ut nunc. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Mauris viverra diam vitae quam. Suspendisse potenti. Nullam porttitor lacus at turpis. Donec posuere metus vitae ipsum. Aliquam non mauris. Morbi non lectus. Aliquam sit amet diam in magna bibendum imperdiet.', 'tincidunt lacus at velit vivamus vel', '2022-07-30', '16:50');\n");
        db.execSQL("insert into NOTICE (id, title, content, writer, date, time) values (6, 'duis ac nibh fusce lacus purus', 'Duis aliquam convallis nunc. Proin at turpis a pede posuere nonummy. Integer non velit. Donec diam neque, vestibulum eget, vulputate ut, ultrices vel, augue. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec pharetra, magna vestibulum aliquet ultrices, erat tortor sollicitudin mi, sit amet lobortis sapien sapien non mi. Integer ac neque. Duis bibendum. Morbi non quam nec dui luctus rutrum. Nulla tellus. In sagittis dui vel nisl. Duis ac nibh. Fusce lacus purus, aliquet at, feugiat non, pretium quis, lectus. Suspendisse potenti. In eleifend quam a odio. In hac habitasse platea dictumst. Maecenas ut massa quis augue luctus tincidunt.', 'id lobortis convallis tortor risus dapibus', '2022-12-31', '19:50');\n");
        db.execSQL("insert into NOTICE (id, title, content, writer, date, time) values (7, 'odio justo sollicitudin ut suscipit', 'Proin eu mi. Nulla ac enim. In tempor, turpis nec euismod scelerisque, quam turpis adipiscing lorem, vitae mattis nibh ligula nec sem. Duis aliquam convallis nunc. Proin at turpis a pede posuere nonummy. Integer non velit. Donec diam neque, vestibulum eget, vulputate ut, ultrices vel, augue. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec pharetra, magna vestibulum aliquet ultrices, erat tortor sollicitudin mi, sit amet lobortis sapien sapien non mi. Integer ac neque. Duis bibendum. Morbi non quam nec dui luctus rutrum. Nulla tellus. In sagittis dui vel nisl. Duis ac nibh. Fusce lacus purus, aliquet at, feugiat non, pretium quis, lectus. Suspendisse potenti. In eleifend quam a odio. In hac habitasse platea dictumst. Maecenas ut massa quis augue luctus tincidunt. Nulla mollis molestie lorem. Quisque ut erat. Curabitur gravida nisi at nibh. In hac habitasse platea dictumst. Aliquam augue quam, sollicitudin vitae, consectetuer eget, rutrum at, lorem. Integer tincidunt ante vel ipsum. Praesent blandit lacinia erat. Vestibulum sed magna at nunc commodo placerat.', 'tempor convallis nulla neque libero convallis', '2023-04-04', '11:46');\n");
        db.execSQL("insert into NOTICE (id, title, content, writer, date, time) values (8, 'ut suscipit a feugiat et eros vestibulum', 'Morbi porttitor lorem id ligula. Suspendisse ornare consequat lectus. In est risus, auctor sed, tristique in, tempus sit amet, sem. Fusce consequat. Nulla nisl. Nunc nisl. Duis bibendum, felis sed interdum venenatis, turpis enim blandit mi, in porttitor pede justo eu massa. Donec dapibus. Duis at velit eu est congue elementum. In hac habitasse platea dictumst. Morbi vestibulum, velit id pretium iaculis, diam erat fermentum justo, nec condimentum neque sapien placerat ante. Nulla justo. Aliquam quis turpis eget elit sodales scelerisque. Mauris sit amet eros. Suspendisse accumsan tortor quis turpis. Sed ante.', 'vivamus tortor duis mattis egestas metus aenean', '2022-09-06', '10:31');\n");
        db.execSQL("insert into NOTICE (id, title, content, writer, date, time) values (9, 'est risus auctor sed tristique in tempus', 'Fusce posuere felis sed lacus. Morbi sem mauris, laoreet ut, rhoncus aliquet, pulvinar sed, nisl. Nunc rhoncus dui vel sem. Sed sagittis. Nam congue, risus semper porta volutpat, quam pede lobortis ligula, sit amet eleifend pede libero quis orci. Nullam molestie nibh in lectus. Pellentesque at nulla. Suspendisse potenti. Cras in purus eu magna vulputate luctus. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vivamus vestibulum sagittis sapien.', 'vel lectus in quam fringilla', '2023-02-15', '18:11');\n");
        db.execSQL("insert into NOTICE (id, title, content, writer, date, time) values (10, 'duis bibendum felis sed interdum venenatis', 'Praesent blandit lacinia erat. Vestibulum sed magna at nunc commodo placerat. Praesent blandit. Nam nulla. Integer pede justo, lacinia eget, tincidunt eget, tempus vel, pede. Morbi porttitor lorem id ligula. Suspendisse ornare consequat lectus. In est risus, auctor sed, tristique in, tempus sit amet, sem. Fusce consequat. Nulla nisl. Nunc nisl. Duis bibendum, felis sed interdum venenatis, turpis enim blandit mi, in porttitor pede justo eu massa. Donec dapibus. Duis at velit eu est congue elementum. In hac habitasse platea dictumst. Morbi vestibulum, velit id pretium iaculis, diam erat fermentum justo, nec condimentum neque sapien placerat ante.', 'eu est congue elementum in hac habitasse', '2023-03-07', '10:42');\n");
        db.execSQL("insert into NOTICE (id, title, content, writer, date, time) values (11, 'id sapien in sapien iaculis', 'Integer ac neque. Duis bibendum. Morbi non quam nec dui luctus rutrum. Nulla tellus. In sagittis dui vel nisl. Duis ac nibh. Fusce lacus purus, aliquet at, feugiat non, pretium quis, lectus. Suspendisse potenti. In eleifend quam a odio. In hac habitasse platea dictumst. Maecenas ut massa quis augue luctus tincidunt. Nulla mollis molestie lorem. Quisque ut erat. Curabitur gravida nisi at nibh. In hac habitasse platea dictumst. Aliquam augue quam, sollicitudin vitae, consectetuer eget, rutrum at, lorem. Integer tincidunt ante vel ipsum. Praesent blandit lacinia erat. Vestibulum sed magna at nunc commodo placerat. Praesent blandit. Nam nulla. Integer pede justo, lacinia eget, tincidunt eget, tempus vel, pede. Morbi porttitor lorem id ligula. Suspendisse ornare consequat lectus. In est risus, auctor sed, tristique in, tempus sit amet, sem. Fusce consequat.', 'lacinia aenean sit amet justo', '2023-01-28', '9:20');\n");
    }




    // INSERT 문
    public void InsertNotice(Integer _id, String _title, String _content, String _writer, String _date, String _time) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO NOTICE (id, title, content, writer, date, time) VALUES('" + _id + "','" + _title + "','" + _content + "','" + _writer + "','" + _date + "','" + _time + "');");
    }
    public void InsertNotice(String _title, String _content, String _writer, String _date, String _time) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO NOTICE (title, content, writer, date, time) VALUES('" + _title + "','" + _content + "','" + _writer + "','" + _date + "','" + _time + "');");
    }
    public void InsertStudent(Integer _id, String _pw, String _name, String _division, String _department, String _penalty_date, String _penalty_state) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO STUDENT (id, pw, name, division, department, _penalty_date, _penalty_state) VALUES('" + _id + "','" + _pw + "','" + _name + "','" + _division + "','" + _department + "','" + _penalty_date + "','" + _penalty_state + "');");
    }
    public void InsertItem(Integer _id, String _name, String _category, String _location, String _type, String _state) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO ITEM (id, name, category, location, type, state) VALUES('" + _id + "','" + _name + "','" + _category + "','" + _location + "','" + _type + "','" + _state + "');");
    }
    public void InsertItem(String _name, String _category, String _location, String _type, String _state) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO ITEM (name, category, location, type, state) VALUES('" + _name + "','" + _category + "','" + _location + "','" + _type + "','" + _state + "');");
    }
    public void InsertRental(Integer _id, Integer _student_id, Integer _item_id, String _start_date, String _start_time, String _return_date, String _return_time, String _rental_state, Integer _rental_extend) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO RENTAL (id, student_id, item_id, start_date, start_time, return_date, return_time, rental_state, rental_extend) VALUES('" + _id + "','" + _student_id + "','" + _item_id + "','" + _start_date + "','" + _start_time + "','" + _return_date + "','" + _return_time + "','" + _rental_state + "','" + _rental_extend + "');");
    }public void InsertRental(Integer _student_id, Integer _item_id, String _start_date, String _start_time, String _return_date, String _return_time, String _rental_state, Integer _rental_extend) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO RENTAL (student_id, item_id, start_date, start_time, return_date, return_time, rental_state, rental_extend) VALUES('" + _student_id + "','" + _item_id + "','" + _start_date + "','" + _start_time + "','" + _return_date + "','" + _return_time + "','" + _rental_state + "','" + _rental_extend + "');");
    }
    public void InsertReservation(Integer _id, Integer _student_id, Integer _item_id, String _start_date, String _start_time, String _return_date, String _return_time, String _reservation_state) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO RESERVATION (id, student_id, item_id, start_date, start_time, return_date, return_time, reservation_state) VALUES('" + _id + "','" + _student_id + "','" + _item_id + "','" + _start_date + "''" + _start_time + "','" + _return_date + "','" + _return_time + "','" + _reservation_state + "')");
    }
    public void InsertReservation(Integer _student_id, Integer _item_id, String _start_date, String _start_time, String _return_date, String _return_time, String _reservation_state) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO RESERVATION (student_id, item_id, start_date, start_time, return_date, return_time, reservation_state) VALUES('" + _student_id + "','" + _item_id + "','" + _start_date + "''" + _start_time + "','" + _return_date + "','" + _return_time + "','" + _reservation_state + "')");
    }
    public void InsertProposal(Integer _id, Integer _student_id, String _title, String _content, String _write_date, String _write_time, String _response_state, String _response_content) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO PROPOSAL (id, student_id, title, content, write_date, write_time, _response_state, _response_content) VALUES('" + _id + "','" + _student_id + "','" + _title + "','" + _content + "', '" + _write_date + "', '" + _write_time + "','" + _response_state + "','" + _response_content + "')");
    }
    public void InsertProposal(Integer _student_id, String _title, String _content, String _write_date, String _write_time, String _response_state, String _response_content) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO PROPOSAL (student_id, title, content, write_date, write_time, _response_state, _response_content) VALUES('" + _student_id + "','" + _title + "','" + _content + "', '" + _write_date + "', '" + _write_time + "','" + _response_state + "','" + _response_content + "')");
    }
    public void InsertProposal(Integer _student_id, String _content, String _write_date, String _write_time) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO PROPOSAL (student_id, content, write_date, write_time) VALUES('" + _student_id + "','" + _content + "', '" + _write_date + "', '" + _write_time + "')");
    }



    // UPDATE 문
    public void UpdateNotice(String _title, String _content, String _writer, String _date, String _time, Integer _beforeId) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE NOTICE SET title='" + _title + "', content='" + _content + "', writer='" + _writer + "', date='" + _date + "', time='" + _time + "', WHERE id='" + _beforeId + "'");
    }
    public void UpdateStudent(String _pw, String _name, String _division, String _department, String _penalty_date, String _penalty_state, Integer _beforeId ) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE STUDENT SET pw='" + _pw + "', name='" + _name + "', division='" + _division + "', department='" + _department + "', penalty_date='" + _penalty_date + "', penalty-state'" + _penalty_state + "', WHERE id='" + _beforeId + "'");
    }
    public void UpdateItem(String _name, String _category, String _location, String _type, String _state, Integer _beforeId) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE ITEM SET name='" + _name + "', category='" + _category + "', location='" + _location + "', type='" + _type + "', state='" + _state + "', WHERE id='" + _beforeId + "'");
    }
    public void UpdateItem(String _state, Integer _beforeId) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE ITEM SET state='" + _state + "' WHERE id='" + _beforeId + "'");
    }


    // DELETE 문
    public void DeleteNotice(int _id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM NOTICE WHERE id = '" + _id + "'");
    }
}
