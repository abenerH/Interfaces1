package primero.angie.andrew.blabla.interfaces.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import primero.angie.andrew.blabla.interfaces.API.Api;
import primero.angie.andrew.blabla.interfaces.R;
import primero.angie.andrew.blabla.interfaces.clases.Complaint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dell on 22/10/2017.
 */

public class complaintadd_activity extends AppCompatActivity {

    private EditText title;
    private EditText description;
    private Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncias);
        initViews();
        initActions();
    }
    private void initViews() {
        title = (EditText) findViewById(R.id.ETTitulo);
        description = (EditText) findViewById(R.id.ETDescripcion);
        create = (Button) findViewById(R.id.BTPublicar);
    }

    /**
     * Logic button action
     */
    private void initActions() {
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create();
            }
        });
    }

    private void create() {

        if (title.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Requiero un titulo", Toast.LENGTH_LONG).show();
        } else if(description.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Requiero una descripcion", Toast.LENGTH_LONG).show();
        } else {
            // this instance new complaint with data xD
            Complaint complaint = new Complaint();
            complaint.setTitulo(title.getText().toString());
            complaint.setDescripcion(description.getText().toString());
            complaint.setCategory(1);
            complaint.setUserId(1);
            complaint.setEnable(true);

            // this make http request to create an complaint
            Call<Complaint> call = Api.instance().createComplaint(complaint);
            call.enqueue(new Callback<Complaint>() {
                @Override
                public void onResponse(Call<Complaint> call, Response<Complaint> response) {
                    if (response.body() != null) {
                        Complaint complaintResult  = response.body();
                        assert complaintResult != null;
                        Log.i("title", complaintResult.getTitulo());
                        Log.i("description", complaintResult.getDescripcion());
                    }
                }

                @Override
                public void onFailure(Call<Complaint> call, Throwable t) {
                    Log.e("ErrorxD", t.getMessage());

                }
            });


        }
    }

}