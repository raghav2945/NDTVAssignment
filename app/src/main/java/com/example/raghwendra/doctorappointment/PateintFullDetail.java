package com.example.raghwendra.doctorappointment;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by raghawendra.kumar on 26-04-2016.
 */
public class PateintFullDetail extends AppCompatActivity {

    Patient_Details patientObj;
    String ID;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ID = (String)getIntent().getExtras().get("ID");
        try {
            loadDetails(ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ((TextView)findViewById(R.id.name)).setText("Name : " +patientObj.getName());
        ((TextView)findViewById(R.id.age)).setText("Age : " +patientObj.getAge());
        ((TextView)findViewById(R.id.gender)).setText("Gender : "+patientObj.getGender());
        ((TextView)findViewById(R.id.bloodgroup)).setText("Blood Group : "+patientObj.getBloodGroup());
        ((TextView)findViewById(R.id.city)).setText("City : "+patientObj.getCity());
        ((TextView)findViewById(R.id.email)).setText("Email : "+patientObj.getEmailId());
        ((TextView)findViewById(R.id.mobile)).setText("Mobile : "+patientObj.getMobileNo());
        ((TextView)findViewById(R.id.appointment)).setText("Appointment : "+patientObj.getDate());
        ((TextView)findViewById(R.id.address)).setText("Full Address : "+patientObj.getAddress());

        imageView = (ImageView)findViewById(R.id.imageView1);
        new AsyncImage().execute();
    }
    @JsonIgnoreProperties({"id"})
    void loadDetails(String id) throws JSONException {
        String retVal = loadJSONFromAsset();
        if (retVal != null) {
            JSONObject obj = null;
            try {
                obj = new JSONObject(retVal);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (obj == null) {
                //preventing from crash in case of failure
                return;
            }
            ObjectMapper mapper = new ObjectMapper();
            JSONArray getArray = obj.getJSONArray("patients");
            for (int i = 0; i < getArray.length(); i++) {
                Patient_Details tempParcel = null;
                JSONObject tempObj;
                try {
                    tempObj = getArray.getJSONObject(i);
                    tempParcel = mapper.readValue(tempObj.toString(), Patient_Details.class);
                    if(tempParcel.getName().equals(id)) {
                        patientObj = tempParcel;
                        break;
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public String loadJSONFromAsset() {
        String json;
        InputStream is;
        try {
            is = getAssets().open("patient_list_api.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public class AsyncImage extends AsyncTask<String, Void, Drawable>
    {
        public AsyncImage()
        {}
        @Override
        protected Drawable doInBackground(String... params) {
            URL myUrl;
            Drawable drawable= null;
            try {
                myUrl = new URL(patientObj.getProfile());
                InputStream inputStream = (InputStream)myUrl.getContent();
                drawable = Drawable.createFromStream(inputStream, null);
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return drawable;
        }

        @Override
        protected void onPostExecute(Drawable result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            imageView.setImageDrawable(result);
        }
    }
}
