package com.example.raghwendra.doctorappointment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raghawendra.kumar on 12-04-2016.
 */
public class PatientListAcceptedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<PatientDetails> DataSource=new ArrayList<>();
    OnItemClickListener clickListener;
    Context c;
    PatientDetails item;
    public PatientListAcceptedAdapter(Context context, List<PatientDetails> DataSource) throws JSONException {
        this.DataSource=DataSource;
        this.c=context;
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
                .inflate(R.layout.accepted_patient_list, parent, false);
        viewHolder= new ViewHolderMain(v);

        return viewHolder;
    }
    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolderArg, int position) {
        ViewHolderMain viewHolder=(ViewHolderMain)viewHolderArg;
        item = DataSource.get(position);
        viewHolder.patientName.setText(item.name);
        viewHolder.patientDiagnosis.setText("Diagnosis : " + item.diagnosis);
        viewHolder.knownDeseases.setText("Known Diseases : "+item.knownDeseases.toString());
        viewHolder.doctorAppoint.setText("Doctor Appoint : "+item.doctor);
        viewHolder.doctorSpecialty.setText("Doctor Speciality : "+item.specialty);
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

        public ViewHolderMain(View itemView) {
            super(itemView);
            CardView = (CardView)itemView.findViewById(R.id.card_view);
            patientName = (TextView)itemView.findViewById(R.id.patientName);
            patientDiagnosis = (TextView)itemView.findViewById(R.id.patientDiagnosis);
            knownDeseases = (TextView)itemView.findViewById(R.id.knownDeseases);
            doctorAppoint = (TextView)itemView.findViewById(R.id.doctorAppoint);
            doctorSpecialty = (TextView)itemView.findViewById(R.id.doctorSpecialty);
            CardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            PatientDetails tempObj = getDataSource().get(getAdapterPosition());
            System.out.println("ID" + tempObj.getId());
            Intent intent = new Intent();
            intent.putExtra("ID",tempObj.getName());
            intent.setClass(c, PateintFullDetail.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            c.startActivity(intent);
        }

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

}
