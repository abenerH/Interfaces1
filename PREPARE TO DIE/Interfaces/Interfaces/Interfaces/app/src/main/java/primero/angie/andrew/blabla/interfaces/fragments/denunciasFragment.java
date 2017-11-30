package primero.angie.andrew.blabla.interfaces.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import primero.angie.andrew.blabla.interfaces.API.Api;
import primero.angie.andrew.blabla.interfaces.Adapter.ComplaintsAdapter;
import primero.angie.andrew.blabla.interfaces.R;
import primero.angie.andrew.blabla.interfaces.activities.complaintadd_activity;
import primero.angie.andrew.blabla.interfaces.activities.complaintadd_activity;
import primero.angie.andrew.blabla.interfaces.clases.Complaint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thech on 19/10/2017.
 */

public class denunciasFragment extends Fragment {
    public RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("Still", "alive");
        View view = inflater.inflate(R.layout.denuncias_fragment, container, false);
        Log.i("Still", "alive 2");
        // Call init method
        init(view);
        Log.i("Still", "alive how ");
        return view;
    }

    private void init(View view) {
        Log.i("Still", "alive 3");
        recyclerView = view.findViewById(R.id.recycler_view);
        Log.i("Still", "alive 4");
        recyclerView.setHasFixedSize(true);
        Log.i("Still", "alive 5");
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Log.i("Still", "alive 6");

        Call<List<Complaint>> call = Api.instance().getComplaints();
        Log.i("Still", "alive 7");
        call.enqueue(new Callback<List<Complaint>>() {

            @Override
            public void onResponse(Call<List<Complaint>> call, Response<List<Complaint>> response) {
                Log.i("Still", "alive 8");
                if (response.body() != null) {
                    Log.i("Still", "alive 9");
                    ComplaintsAdapter complaintsAdapter = new ComplaintsAdapter(response.body());
                    Log.i("Still", "alive 10");
                    recyclerView.setAdapter(complaintsAdapter);
                    Log.i("Still", "alive 11");



                }
            }

            @Override
            public void onFailure(Call<List<Complaint>> call, Throwable t) {
                Log.e("MISSION FAILED", t.getMessage());

            }
        });
    }
}