package nati.aviran.getbs.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;


public class BabySitterSql {
    static final String BS_TABLE = "babysitter";
    static final String BS_EMAIL = "email";

    static final String BS_PASSWORD = "password";
    static final String BS_NAME = "name";
    static final String BS_PHONE = "phone";
    static final String BS_ADDRESS = "address";
    static final String BS_SALARY = "salary";
    static final String BS_AGE = "age";
    static final String BS_AVAILABILITY = "availability";

    static final String BS_IMAGE_URL = "imageUrl";
    static final String BS_LAST_UPDATE = "lastUpdateDate";



    static List<BabySitter> getAllBabySitters(SQLiteDatabase db) {
        Cursor cursor = db.query(BS_TABLE, null, null, null, null, null, null);
        List<BabySitter> list = new LinkedList<BabySitter>();
        if (cursor.moveToFirst()) {
            int email_Index = cursor.getColumnIndex(BS_EMAIL);

            int password_Index = cursor.getColumnIndex(BS_PASSWORD);
            int name_Index = cursor.getColumnIndex(BS_NAME);
            int phone_Index = cursor.getColumnIndex(BS_PHONE);
            int address_Index = cursor.getColumnIndex(BS_ADDRESS);
            int salary_Index = cursor.getColumnIndex(BS_SALARY);
            int age_Index = cursor.getColumnIndex(BS_AGE);
            int availability_Index = cursor.getColumnIndex(BS_AVAILABILITY);

            int imageUrl_Index = cursor.getColumnIndex(BS_IMAGE_URL);
            int lastUpdateDate_Index = cursor.getColumnIndex(BS_LAST_UPDATE);

            do {
                BabySitter bs = new BabySitter();
                bs.email = cursor.getString(email_Index);
                bs.password = cursor.getString(password_Index);
                bs.name = cursor.getString(name_Index);
                bs.phone = cursor.getString(phone_Index);
                bs.address = cursor.getString(address_Index);
                bs.salary = cursor.getString(salary_Index);
                bs.age = cursor.getString(age_Index);
                bs.availability = cursor.getString(availability_Index);
                bs.imageUrl = cursor.getString(imageUrl_Index);
                bs.lastUpdateDate = cursor.getDouble(lastUpdateDate_Index);

                list.add(bs);
            } while (cursor.moveToNext());
        }
        return list;
    }

    static void addBabySitter(SQLiteDatabase db, BabySitter bs) {
        ContentValues values = new ContentValues();

        values.put(BS_EMAIL, bs.email);
        values.put(BS_PASSWORD, bs.password);
        values.put(BS_NAME, bs.name);
        values.put(BS_PHONE, bs.phone);
        values.put(BS_ADDRESS, bs.address);
        values.put(BS_SALARY, bs.salary);
        values.put(BS_AGE, bs.age);
        values.put(BS_AVAILABILITY, bs.availability);
        values.put(BS_IMAGE_URL, bs.imageUrl);
        values.put(BS_LAST_UPDATE, bs.lastUpdateDate);

        db.insert(BS_TABLE, BS_EMAIL, values);
    }


    static public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + BS_TABLE +
                " (" +
                BS_EMAIL + " TEXT PRIMARY KEY, " +
                BS_PASSWORD + " TEXT, " +
                BS_NAME + " TEXT, " +
                BS_PHONE + " TEXT, " +
                BS_ADDRESS + " TEXT, " +
                BS_SALARY + " TEXT, " +
                BS_AGE + " TEXT, " +
                BS_AVAILABILITY + " TEXT, " +
                BS_LAST_UPDATE + " NUMBER, " +
                BS_IMAGE_URL + " TEXT);";
        Log.d("TAG",sql);
        db.execSQL(sql);
    }

    static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + BS_TABLE + ";");
        onCreate(db);
    }


}
