package com.example.usersapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class ViewUsersActivity extends AppCompatActivity {
    private ListView mUsersListView;
    private UserDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);

        mUsersListView = (ListView) findViewById(R.id.users_list_view);
        mDbHelper = new UserDbHelper(this);

        displayUsers();
    }

    private void displayUsers() {
        // Open a connection to the database
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database you will actually use after this query.
        String[] projection = {
                UserContract.UserEntry._ID,
                UserContract.UserEntry.COLUMN_NAME_FNAME,
                UserContract.UserEntry.COLUMN_NAME_LNAME,
                UserContract.UserEntry.COLUMN_NAME_MOBILE,
                UserContract.UserEntry.COLUMN_NAME_EMAIL
        };

        // Perform a query on the users table
        Cursor cursor = db.query(
                UserContract.UserEntry.TABLE_NAME,   // The table to query
                projection,             // The columns to return
                null,                   // The columns for the WHERE clause
                null,                   // The values for the WHERE clause
                null,                   // Don't group the rows
                null,                   // Don't filter by row groups
                null                    // The sort order
        );

        // Create an adapter to bind the cursor to the ListView
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.user_list_item,
                cursor,
                new String[] { UserContract.UserEntry._ID,
                        UserContract.UserEntry.COLUMN_NAME_FNAME,
                        UserContract.UserEntry.COLUMN_NAME_LNAME,
                        UserContract.UserEntry.COLUMN_NAME_MOBILE,
                        UserContract.UserEntry.COLUMN_NAME_EMAIL },
                new int[] {R.id._id, R.id.fname_text_view, R.id.lname_text_view, R.id.mobile_text_view, R.id.email_text_view },
                0
        );

        // Set the adapter for the ListView
        mUsersListView.setAdapter(adapter);
    }
}
