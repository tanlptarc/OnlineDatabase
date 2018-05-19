package com.example.sylviatan.onlinedatabase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {

    public UserAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //ListView position captured each user record
        User user = getItem(position);

        //Define and retrieve a LayoutInflater
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Create View object to be returned later
        View rowView = inflater.inflate(R.layout.display, parent, false);

        TextView textViewID, textViewName, textViewAge, textViewMarried;

        textViewID = rowView.findViewById(R.id.textViewID);
        textViewName = rowView.findViewById(R.id.textViewName);
        textViewAge = rowView.findViewById(R.id.textViewAge);
        textViewMarried = rowView.findViewById(R.id.textViewMarried);

        textViewID.setText(user.getId());
        textViewName.setText(user.getName());
        textViewAge.setText(user.getAge());
        textViewMarried.setText(user.getMarried());

        return rowView;
    }
}
