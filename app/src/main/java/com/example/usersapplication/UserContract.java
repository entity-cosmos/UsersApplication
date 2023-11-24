package com.example.usersapplication;

import android.provider.BaseColumns;

public final class UserContract {
    private UserContract() {}

    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String _ID = "id";
        public static final String COLUMN_NAME_FNAME = "fname";
        public static final String COLUMN_NAME_LNAME = "lname";
        public static final String COLUMN_NAME_MOBILE = "mobile";
        public static final String COLUMN_NAME_EMAIL = "email";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UserEntry.TABLE_NAME + " (" +
                    UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    UserEntry.COLUMN_NAME_FNAME + " TEXT," +
                    UserEntry.COLUMN_NAME_LNAME + " TEXT," +
                    UserEntry.COLUMN_NAME_MOBILE + " TEXT," +
                    UserEntry.COLUMN_NAME_EMAIL + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME;
}
