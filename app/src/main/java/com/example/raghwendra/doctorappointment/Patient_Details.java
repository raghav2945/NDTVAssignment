package com.example.raghwendra.doctorappointment;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by raghawendra.kumar on 26-04-2016.
 */
public class Patient_Details implements Parcelable {
    String name;
    String id;
    String age;
    String gender;
    String bloodGroup;
    String city;
    String address;
    String emailId;
    String profile;
    String mobileNo;
    String date;

    protected Patient_Details(Parcel in) {
        name = in.readString();
        id = in.readString();
        age = in.readString();
        gender = in.readString();
        bloodGroup = in.readString();
        city = in.readString();
        address = in.readString();
        emailId = in.readString();
        profile = in.readString();
        mobileNo = in.readString();
        date = in.readString();
    }

    public static final Creator<Patient_Details> CREATOR = new Creator<Patient_Details>() {
        @Override
        public Patient_Details createFromParcel(Parcel in) {
            return new Patient_Details(in);
        }

        @Override
        public Patient_Details[] newArray(int size) {
            return new Patient_Details[size];
        }
    };

    public Patient_Details(){}
    public Patient_Details(String name, String id, String age, String gender, String bloodGroup, String city, String address, String emailId, String profile, String mobileNo, String date) {
        this.name = name;
        this.id = id;
        this.age = age;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.city = city;
        this.address = address;
        this.emailId = emailId;
        this.profile = profile;
        this.mobileNo = mobileNo;
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(id);
        dest.writeString(age);
        dest.writeString(gender);
        dest.writeString(bloodGroup);
        dest.writeString(city);
        dest.writeString(address);
        dest.writeString(emailId);
        dest.writeString(profile);
        dest.writeString(mobileNo);
        dest.writeString(date);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
