package com.example.moneymanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

    public class DatabaseController extends SQLiteOpenHelper {
        private static final String LOGCAT = null;

        public DatabaseController(Context applicationcontext) {
            super(applicationcontext, "Companies.db", null, 1);
            Log.d(LOGCAT, "Created");
        }

        @Override
        public void onCreate(SQLiteDatabase database) {
            String query;
            query = "CREATE TABLE IF NOT EXISTS tblCompanies ( _id INTEGER PRIMARY KEY, CompanyName TEXT)";
            database.execSQL(query);
        }

        public String InsertData(String companyName) {
            try {
                SQLiteDatabase database = this.getWritableDatabase();
                String query = "insert into tblCompanies (CompanyName) values ('" + companyName + "')";
                database.execSQL(query);
                database.close();
                return "Added Successfully";
            } catch (Exception ex) {
                return ex.getMessage().toString();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase database, int version_old,
                              int current_version) {
            String query;
            query = "DROP TABLE IF EXISTS tblCompanies";
            database.execSQL(query);
            onCreate(database);
        }

        public Cursor getCompanies() {
            try {
                String selectQuery = "SELECT * FROM tblCompanies order by _id desc";
                SQLiteDatabase database = this.getWritableDatabase();
                Cursor cursor = database.rawQuery(selectQuery, null);
                return cursor;
            } catch (Exception ex) {
                return null;
            }
        }
    }


