package com.example.usersapplication;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddUserActivity extends AppCompatActivity {
    private EditText mFirstNameEditText;
    private EditText mLastNameEditText;
    private EditText mMobile;
    private EditText mEmailEditText;
    private UserDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        mFirstNameEditText = (EditText) findViewById(R.id.fname);
        mLastNameEditText = (EditText) findViewById(R.id.lname);
        mMobile = (EditText) findViewById(R.id.mobile);
        mEmailEditText = (EditText) findViewById(R.id.email);
        mDbHelper = new UserDbHelper(this);

        Button addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUserToDatabase();
                finish();
            }
        });
    }

    private void addUserToDatabase() {
        // Get the user's details from the text fields
        String fname = mFirstNameEditText.getText().toString();
        String lname = mLastNameEditText.getText().toString();
        String mobile = mMobile.getText().toString();
        String email = mEmailEditText.getText().toString();

        // Open db connection
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Set values
        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntry.COLUMN_NAME_FNAME, fname);
        values.put(UserContract.UserEntry.COLUMN_NAME_LNAME, lname);
        values.put(UserContract.UserEntry.COLUMN_NAME_MOBILE, mobile);
        values.put(UserContract.UserEntry.COLUMN_NAME_EMAIL, email);

        // Insert the new user into the users table
        long newRowId = db.insert(UserContract.UserEntry.TABLE_NAME, null, values);

        // Close the connection to the database
        db.close();
    }
}
