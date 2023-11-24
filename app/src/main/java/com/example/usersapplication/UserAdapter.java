package com.example.usersapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class UserAdapter extends ArrayAdapter<User> {
    public UserAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        User user = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_list_item, parent, false);
        }

        // Lookup view for data population
        TextView fnameTextView = (TextView) convertView.findViewById(R.id.fname_text_view);
        TextView lnameTextView = (TextView) convertView.findViewById(R.id.lname_text_view);
        TextView mobileTextView = (TextView) convertView.findViewById(R.id.mobile_text_view);
        TextView emailTextView = (TextView) convertView.findViewById(R.id.email_text_view);

        // Populate the data into the template view using the data object
        fnameTextView.setText(user.getFname());
        lnameTextView.setText(user.getLname());
        mobileTextView.setText(user.getMobile());
        emailTextView.setText(user.getEmail());

        // Return the completed view to render on screen
        return convertView;
    }
}
