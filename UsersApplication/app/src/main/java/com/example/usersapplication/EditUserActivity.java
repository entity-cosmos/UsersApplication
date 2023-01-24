package com.example.usersapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditUserActivity extends AppCompatActivity {
    private EditText mFirstNameEditText;
    private EditText mLastNameEditText;
    private EditText mMobile;
    private EditText mEmailEditText;
    private UserDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        mFirstNameEditText = (EditText) findViewById(R.id.fname);
        mLastNameEditText = (EditText) findViewById(R.id.lname);
        mMobile = (EditText) findViewById(R.id.mobile);
        mEmailEditText = (EditText) findViewById(R.id.email);
        mDbHelper = new UserDbHelper(this);

        // Get the user's ID from the intent
        Intent intent = getIntent();
        final long userId = intent.getLongExtra("userId", 0);

        // Get the user's details from the database
        User user = getUserFromDatabase(userId);

        // Set the user's details in the text fields
        mFirstNameEditText.setText(user.getFname());
        mEmailEditText.setText(user.getEmail());

        Button saveButton = (Button) findViewById(R.id.add_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserInDatabase(userId);
                finish();
            }
        });
    }

    private User getUserFromDatabase(long userId) {
        // Open a connection to the database
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                UserContract.UserEntry._ID,
                UserContract.UserEntry.COLUMN_NAME_FNAME,
                UserContract.UserEntry.COLUMN_NAME_LNAME,
                UserContract.UserEntry.COLUMN_NAME_MOBILE,
                UserContract.UserEntry.COLUMN_NAME_EMAIL
        };

        // Filter results WHERE "userId" = 'userId'
        String selection = UserContract.UserEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(userId) };

        // Perform a query on the users table
        Cursor cursor = db.query(
                UserContract.UserEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        User user = null;
        if (cursor.moveToFirst()) {
            // Extract the values from the cursor
            String fname = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_FNAME));
            String lname = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_LNAME));
            String mobile = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_MOBILE));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_EMAIL));

            // Create a new User object
            user = new User(userId, fname, lname, mobile, email);
        }

        // Close the cursor and the connection to the database
        cursor.close();
        db.close();

        return user;
    }

    private void updateUserInDatabase(long userId) {
        // Get the user's details from the text fields
        String fname = mFirstNameEditText.getText().toString();
        String lname = mLastNameEditText.getText().toString();
        String mobile = mMobile.getText().toString();
        String email = mEmailEditText.getText().toString();

        // Open a connection to the database
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new ContentValues object
        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntry.COLUMN_NAME_FNAME, fname);
        values.put(UserContract.UserEntry.COLUMN_NAME_LNAME, lname);
        values.put(UserContract.UserEntry.COLUMN_NAME_MOBILE, mobile);
        values.put(UserContract.UserEntry.COLUMN_NAME_EMAIL, email);

        // Update the user in the users table
        String selection = UserContract.UserEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(userId) };
        int count = db.update(
                UserContract.UserEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        // Close the connection to the database
        db.close();
    }
}

