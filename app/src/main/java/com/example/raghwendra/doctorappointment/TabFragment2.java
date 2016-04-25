package com.example.raghwendra.doctorappointment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by raghawendra.kumar on 24-04-2016.
 */
public class TabFragment2 extends Fragment {
    public PatientListAcceptedAdapter mAdapter;
    RecyclerView rvItems;
    RecyclerView.LayoutManager mLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.tab_fragment, container, false);
        rvItems =(RecyclerView)view.findViewById(R.id.lvGenericList);
        rvItems.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rvItems.setLayoutManager(mLayoutManager);
        return view;
    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            loadData();
        }
    }

    void loadData() {
        try {
            FileInputStream fin = getActivity().getApplicationContext().openFileInput("patient_appointment_accept_api.json");
            bindData();
        } catch (Exception e) {
            System.out.println("RAGHU@ File not found!!!");
        }
    }

    public void bindData() throws JSONException, JsonProcessingException {
        String retVal = loadJSONFromAsset();
        if(retVal!=null) {
            JSONObject obj = new JSONObject(retVal);
            if (obj == null) {
                //preventing from crash in case of failure
                return;
            }
            ObjectMapper mapper = new ObjectMapper();
            //mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            ArrayList<PatientDetails> matchingParcels = new ArrayList<>();
            JSONArray getArray = obj.getJSONArray("patientDetails");
            for (int i = 0; i < getArray.length(); i++) {
                PatientDetails tempParcel = null;
                JSONObject tempObj;
                try {
                    tempObj = getArray.getJSONObject(i);
                    tempParcel = mapper.readValue(tempObj.toString(), PatientDetails.class);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                if (tempParcel != null) {
                    if (tempParcel.getStatus().equals("accept"))
                        matchingParcels.add(tempParcel);
                }
            }
            mAdapter = new PatientListAcceptedAdapter(getActivity().getApplicationContext(), matchingParcels);
            rvItems.setAdapter(mAdapter);
        }else
            System.out.println("Raghu : Data not found!!!" );
    }

    public String loadJSONFromAsset() {
        String json;
        InputStream is;
        try {
            is = getActivity().getApplicationContext().openFileInput("patient_appointment_accept_api.json");
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
}
