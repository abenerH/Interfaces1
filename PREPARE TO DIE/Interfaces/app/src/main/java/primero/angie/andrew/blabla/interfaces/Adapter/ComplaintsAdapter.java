package primero.angie.andrew.blabla.interfaces.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import primero.angie.andrew.blabla.interfaces.R;
import primero.angie.andrew.blabla.interfaces.activities.complaint_detail;
import primero.angie.andrew.blabla.interfaces.clases.Complaint;
import primero.angie.andrew.blabla.interfaces.clases.Picture;

/**
 * Created by thech on 20/10/2017.
 */


public class ComplaintsAdapter extends RecyclerView.Adapter<ComplaintsAdapter.ViewHolder> {

    public Context mContext;

    public List<Complaint> complaints;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public SimpleDraweeView image;
        public CardView cardview;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            cardview = view.findViewById(R.id.card_complaint);
            image = view.findViewById(R.id.ComplaintPhoto);

        }
    }

    // Provides a suitable constructor but depends on the kind of datasets
    public ComplaintsAdapter(List<Complaint> complaints, Context context) {
        this.complaints = complaints;
        this.mContext = context;
    }

    // Create new views invoked by the layout manager
    @Override
    public ComplaintsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_complaint, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    // Replace the contents of a view invoked by the layout manager
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Complaint complaint = complaints.get(position);
        final List<Picture> pictures = complaint.getPictures();
        holder.title.setText(complaint.getTitle());

        try {
            holder.image.setImageURI(pictures.get(0).getUrl());
        } catch (Exception e){
            Log.i(" LOL - Mcnally", ""+ e.getMessage());
        }

        holder.cardview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Log.i("1", "onBindViewHolder");
                Intent intentA = new Intent(mContext, complaint_detail.class);
                Log.i("2", "onBindViewHolder");
                intentA.putExtra("selectedComplaint", complaint);
                String url;
                try{
                    url = pictures.get(0).getUrl().toString();
                }catch (Exception e ){
                    url = "no image";
                }

                Log.i("la url esta aca ", url);
                intentA.putExtra("url", url);
                Log.i("3", "onBindViewHolder");
                intentA.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Log.i("4", "onBindViewHolder");
                mContext.startActivity(intentA);
                Log.i("5", "onBindViewHolder");
            }
        });
    }

    // Return the size of your dataset invoked by the layout manager
    @Override
    public int getItemCount() {
        return complaints.size();
    }
}
