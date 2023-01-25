package com.example.usersapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private ListView mUserListView;
    private UserDbHelper mDbHelper;
    private UserAdapter mUserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserListView = (ListView) findViewById(R.id.user_list_view);
        mDbHelper = new UserDbHelper(this);

        // Set up the adapter for the list view
        mUserAdapter = new UserAdapter(this);
        mUserListView.setAdapter(mUserAdapter);

        // Populate the list view with the users from the database
        loadUsersFromDatabase();

        // Set up the listener for the "Add User" button
        Button addUserButton = (Button) findViewById(R.id.add_user_button);
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddUserActivity.class);
                startActivity(intent);
            }
        });

        mUserListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = (User) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, EditUserActivity.class);
                intent.putExtra("userId", user.getId());
                startActivity(intent);
            }
        });
    }

    private void loadUsersFromDatabase() {
        // Clear the adapter
        mUserAdapter.clear();

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

        // Perform a query on the users table
        Cursor cursor = db.query(
                UserContract.UserEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        // Iterate over the cursor and add each user to the adapter
        while (cursor.moveToNext()) {
            long userId = cursor.getLong(cursor.getColumnIndexOrThrow(UserContract.UserEntry._ID));
            String fname = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_FNAME));
            String lname = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_FNAME));
            String mobile = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_FNAME));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_EMAIL));
            mUserAdapter.add(new User(userId, fname, lname, mobile, email));
        }

        // Close the cursor and the connection to the database
        cursor.close();
        db.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUsersFromDatabase();
    }
}
