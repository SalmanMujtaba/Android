package com.example.salman.inclass03akarsh;


import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {
    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
    String name, department, email, account_state, mood;

    public Student(String name, String department, String email, String account_state, String mood) {
        this.name = name;
        this.department = department;
        this.email = email;
        this.account_state = account_state;
        this.mood = mood;
    }

    protected Student(Parcel in) {
        this.name = in.readString();
        this.department = in.readString();
        this.email = in.readString();
        this.account_state = in.readString();
        this.mood = in.readString();

    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", email='" + email + '\'' +
                ", account_state='" + account_state + '\'' +
                ", mood='" + mood + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(department);
        dest.writeString(email);
        dest.writeString(account_state);
        dest.writeString(mood);
    }
}
