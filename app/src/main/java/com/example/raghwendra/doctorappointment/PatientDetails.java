package com.example.raghwendra.doctorappointment;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raghawendra.kumar on 17-04-2016.
 */
class PatientDetails implements Parcelable {
    String name;
    String id;
    String diagnosis;
    String symptoms;
    String medication;
    String toBeTaken;
    String comments;
    List<String> knownDeseases;
    String doctor;
    String specialty ;
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getToBeTaken() {
        return toBeTaken;
    }

    public void setToBeTaken(String toBeTaken) {
        this.toBeTaken = toBeTaken;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<String> getKnownDeseases() {
        return knownDeseases;
    }

    public void setKnownDeseases(List<String> knownDeseases) {
        this.knownDeseases = knownDeseases;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }


    protected PatientDetails(Parcel in) {
        name = in.readString();
        id = in.readString();
        diagnosis = in.readString();
        symptoms = in.readString();
        medication = in.readString();
        toBeTaken = in.readString();
        comments = in.readString();
        if(in.readByte() == 0x01){
            knownDeseases = new ArrayList<String>();
            in.readList(knownDeseases,String.class.getClassLoader());
        }
        doctor = in.readString();
        specialty = in.readString();
        status = in.readString();
    }

    public static final Creator<PatientDetails> CREATOR = new Creator<PatientDetails>() {
        @Override
        public PatientDetails createFromParcel(Parcel in) {
            return new PatientDetails(in);
        }

        @Override
        public PatientDetails[] newArray(int size) {
            return new PatientDetails[size];
        }
    };

    public PatientDetails(){}
    public PatientDetails(String name, String id, String diagnosis, String symptoms, String medication, String toBeTaken, String comments, List<String> knownDeseases, String doctor, String specialty, String status) {
        this.name = name;
        this.id = id;
        this.diagnosis = diagnosis;
        this.symptoms = symptoms;
        this.medication = medication;
        this.toBeTaken = toBeTaken;
        this.comments = comments;
        this.knownDeseases = knownDeseases;
        this.doctor = doctor;
        this.specialty = specialty;
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(id);
        dest.writeString(diagnosis);
        dest.writeString(symptoms);
        dest.writeString(medication);
        dest.writeString(toBeTaken);
        dest.writeString(comments);
        if(knownDeseases == null){
            dest.writeByte((byte)(0x00));
        }else
        {
            dest.writeByte((byte)(0x01));
            dest.writeList(knownDeseases);
        }
        dest.writeString(doctor);
        dest.writeString(specialty);
        dest.writeString(status);
    }
}