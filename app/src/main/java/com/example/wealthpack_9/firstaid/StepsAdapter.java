package com.example.wealthpack_9.firstaid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class StepsAdapter extends ArrayAdapter<FirstAidSteps> {
    public StepsAdapter(Context context, ArrayList<FirstAidSteps> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FirstAidSteps steps = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView stepName = (TextView) convertView.findViewById(R.id.step_heading);
        stepName.setText(steps.getStepName());

        return convertView;
    }
}