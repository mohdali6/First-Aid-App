package com.example.wealthpack_9.firstaid;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Class for storing JSON First Aid data
 */
public class FirstAidSteps implements Parcelable {
    private final String stepName;
    private final ArrayList<String> steps;

    public FirstAidSteps(String stepName, ArrayList<String> steps) {
        this.stepName = stepName;
        this.steps = steps;
    }

    public String getStepName() {
        return stepName;
    }

    public ArrayList<String> getSteps() {
        return steps;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(stepName);
        out.writeStringList(steps);
    }

    public static final Parcelable.Creator<FirstAidSteps> CREATOR = new Parcelable.Creator<FirstAidSteps>() {
        public FirstAidSteps createFromParcel(Parcel in) {
            return new FirstAidSteps(in);
        }

        public FirstAidSteps[] newArray(int size) {
            return new FirstAidSteps[size];
        }
    };

    private FirstAidSteps(Parcel in) {
        stepName = in.readString();
        steps = new ArrayList<>();
        in.readStringList(steps);
    }
}