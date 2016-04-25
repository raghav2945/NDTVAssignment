package com.example.raghwendra.doctorappointment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raghawendra.kumar on 12-04-2016.
 */
public class PatientListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<PatientDetails> DataSource=new ArrayList<>();
    public List<PatientDetails> DataSource2=new ArrayList<>();
    OnItemClickListener clickListener;
    Context c;
    PatientDetails item;
    public PatientListAdapter(Context context, List<PatientDetails> DataSource) throws JSONException {
        this.DataSource=DataSource;
        this.c=context;
        try {
            updateLastStatus();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<PatientDetails> getDataSource() {
        return DataSource;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patient_list, parent, false);
        viewHolder= new ViewHolderMain(v);

        return viewHolder;
    }
    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    public void updateLastStatus() throws JSONException {
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
                    DataSource2.add(tempParcel);
                }
            }
        }
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolderArg, int position) {
        ViewHolderMain viewHolder=(ViewHolderMain)viewHolderArg;
        item = DataSource.get(position);
        viewHolder.patientName.setText(item.name);
        viewHolder.patientDiagnosis.setText(item.diagnosis);
        viewHolder.knownDeseases.setText(item.knownDeseases.toString());
        viewHolder.doctorAppoint.setText(item.doctor);
        viewHolder.doctorSpecialty.setText(item.specialty);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return DataSource.size();
    }

    public PatientDetails getItem(int position)
    {
        return DataSource.get(position);
    }

    public class ViewHolderMain extends RecyclerView.ViewHolder  implements View.OnClickListener {
        CardView CardView;
        TextView patientName;
        TextView patientDiagnosis;
        TextView knownDeseases;
        TextView doctorAppoint;
        TextView doctorSpecialty;
        Button cancelAppointment;
        Button acceptAppointment;

        public ViewHolderMain(View itemView) {
            super(itemView);
            CardView = (CardView)itemView.findViewById(R.id.card_view);
            patientName = (TextView)itemView.findViewById(R.id.patientName);
            patientDiagnosis = (TextView)itemView.findViewById(R.id.patientDiagnosis);
            knownDeseases = (TextView)itemView.findViewById(R.id.knownDeseases);
            doctorAppoint = (TextView)itemView.findViewById(R.id.doctorAppoint);
            doctorSpecialty = (TextView)itemView.findViewById(R.id.doctorSpecialty);
            cancelAppointment = (Button)itemView.findViewById(R.id.cancelAppointment);
            cancelAppointment.setOnClickListener(this);
            acceptAppointment = (Button)itemView.findViewById(R.id.acceptAppointment);
            acceptAppointment.setOnClickListener(this);
            CardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //clickListener.onItemClick(v,getAdapterPosition());
            switch (v.getId()){
                case R.id.cancelAppointment:
                    System.out.println("Cancel call for :" + getAdapterPosition());
                    PatientDetails acceptObj = getDataSource().get(getAdapterPosition());
                    try {
                        updatedata(acceptObj.getId(),"cancel");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    getDataSource().remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), getDataSource().size());
                    break;
                case R.id.acceptAppointment:
                    System.out.println("Accpt call for :"+getAdapterPosition());
                    PatientDetails cancelObj = getDataSource().get(getAdapterPosition());
                    try {
                        updatedata(cancelObj.getId(),"accept");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    getDataSource().remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), getDataSource().size());
                    updateFiles("patient_detail_api.json", getDataSource());
                    break;
                case R.id.card_view:
                    PatientDetails tempObj = getDataSource().get(getAdapterPosition());
                    System.out.println("ID" + tempObj.getId());
                    Intent intent = new Intent();
                    intent.putExtra("ID",tempObj.getName());
                    intent.setClass(c, PateintFullDetail.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    c.startActivity(intent);
            }

        }

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public void updatedata(String position,String appointmentStatus) throws JSONException {
        for (PatientDetails element : getDataSource()) {
            if(element.getId().equals(position)){
                element.setStatus(appointmentStatus);
                DataSource2.add(element);
            }
        }
        //updateFiles("patient_detail_api.json", getDataSource());
        updateFiles("patient_appointment_accept_api.json", DataSource2);
    }

    void updateFiles(String fileName, List<PatientDetails> dataSource){
        ObjectMapper mapper = new ObjectMapper();
        String jsontowrite = "";
        for (PatientDetails pd:dataSource) {
            String temp = null;
            try {
                temp= mapper.writeValueAsString(pd);
            } catch (JsonProcessingException e) {
                temp= "";
                e.printStackTrace();
            }

            jsontowrite = jsontowrite==""?jsontowrite + temp:jsontowrite+","+temp;
        }
        jsontowrite = "{\"patientDetails\": " +"["+jsontowrite+"]}";
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(c.openFileOutput(fileName , Context.MODE_PRIVATE));
            outputStreamWriter.write(jsontowrite);
            outputStreamWriter.close();
        }
        catch (IOException e) {

        }

    }
    public String loadJSONFromAsset() {
        String json;
        InputStream is;
        try {
            is = c.openFileInput("patient_appointment_accept_api.json");
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
