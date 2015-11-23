package com.example.wealthpack_9.firstaid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class StepsAdapter extends ArrayAdapter<FirstAidSteps> {
    public StepsAdapter(Context context, ArrayList<FirstAidSteps> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.stepName = (TextView) convertView.findViewById(R.id.step_heading);
            holder.steps = (LinearLayout) convertView.findViewById(R.id.steps);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final FirstAidSteps steps = getItem(position);
        holder.stepName.setText(steps.getStepName());

        holder.steps.removeAllViews();
        for (String step: steps.getSteps()) {
            holder.steps.addView(createStepView(step, holder.steps));
        }

        return convertView;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    private static class ViewHolder {
        TextView stepName;
        LinearLayout steps;
    }

    public View createStepView(String step, LinearLayout steps_layout) {
        TextView textView = new TextView(getContext());
        textView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        textView.setText("• " + step);
        textView.setPadding(5, 2, 5, 2);
        textView.setTextSize(20);
        return textView;
    }
}