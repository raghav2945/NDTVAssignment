package com.example.raghwendra.doctorappointment;

import android.content.Context;
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
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by raghawendra.kumar on 24-04-2016.
 */
public class TabFragment extends Fragment {
    public PatientListAdapter mAdapter;
    RecyclerView rvItems;
    RecyclerView.LayoutManager mLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.tab_fragment, container, false);
        rvItems =(RecyclerView)view.findViewById(R.id.lvGenericList);
        rvItems.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rvItems.setLayoutManager(mLayoutManager);
        loadData();
        return view;
    }

    void loadData() {
        try {
            FileInputStream fin = getActivity().getApplicationContext().openFileInput("patient_detail_api.json");
            bindData(false);
        } catch (Exception e) {
            try {
                bindData(true);
            } catch (JSONException e1) {
                e1.printStackTrace();
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void bindData(boolean isNew) throws JSONException, JsonProcessingException {
        String retVal = loadJSONFromAsset(isNew);
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
                if (tempParcel.getStatus() == null)
                    tempParcel.setStatus("default");

                if (tempParcel != null) {
                    matchingParcels.add(tempParcel);
                }
            }
            if (isNew)
            {
                String jsontowrite = "";
                for (PatientDetails pd : matchingParcels) {
                    String temp = null;
                    try {
                        temp = mapper.writeValueAsString(pd);
                    } catch (JsonProcessingException e) {
                        temp = "";
                        e.printStackTrace();
                    }
                    jsontowrite = jsontowrite==""?jsontowrite + temp:jsontowrite+","+temp;
                }
                jsontowrite = "{\"patientDetails\": " +"["+jsontowrite+"]}";
                try {
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getActivity().getApplicationContext().openFileOutput("patient_detail_api.json", Context.MODE_PRIVATE));
                    outputStreamWriter.write(jsontowrite);
                    outputStreamWriter.close();
                } catch (IOException e) {
                    System.out.println("Execption during write!!!");
                }
            }
            mAdapter = new PatientListAdapter(getActivity().getApplicationContext(), matchingParcels);
            rvItems.setAdapter(mAdapter);
        }else
            System.out.println("Raghu : Data not found!!!" );
    }

    public String loadJSONFromAsset(boolean isFirstCall) {
        String json;
        InputStream is;
        try {
            if(isFirstCall)
                is = getActivity().getAssets().open("patient_detail_api.json");
            else
                is = getActivity().getApplicationContext().openFileInput("patient_detail_api.json");

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
