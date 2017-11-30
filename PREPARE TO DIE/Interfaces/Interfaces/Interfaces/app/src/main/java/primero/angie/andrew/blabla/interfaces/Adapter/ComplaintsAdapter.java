package primero.angie.andrew.blabla.interfaces.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import primero.angie.andrew.blabla.interfaces.R;
import primero.angie.andrew.blabla.interfaces.clases.Complaint;

/**
 * Created by thech on 20/10/2017.
 */


public class ComplaintsAdapter extends RecyclerView.Adapter<ComplaintsAdapter.ViewHolder> {
    private List<Complaint> complaints;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView titulo;
        public TextView description;
        public ViewHolder(View view) {
            super(view);
            titulo = view.findViewById(R.id.titulo);
            description = view.findViewById(R.id.descripcion);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ComplaintsAdapter(List<Complaint> complaints) {
        this.complaints = complaints;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ComplaintsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_mis_denuncias, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Complaint complaint = complaints.get(position);

        holder.titulo.setText(complaint.getTitulo());
        holder.description.setText(complaint.getDescripcion());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return complaints.size();
    }
}