package primero.angie.andrew.blabla.interfaces.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import primero.angie.andrew.blabla.interfaces.R;
import primero.angie.andrew.blabla.interfaces.clases.Complaint;


public class complaint_detail extends AppCompatActivity {

    Complaint c;
    SimpleDraweeView imageView;
    TextView textView, textView2;
    public static complaint_detail complaint_detail2;

    public complaint_detail(){
        complaint_detail2 = this;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.i("1.0", "onCreate");

        setContentView(R.layout.complaints_add);
        imageView = findViewById(R.id.iv_Denuncia);
        textView = (TextView)findViewById(R.id.txv_complaintTitle);
        textView2 = (TextView)findViewById(R.id.txv_complaintDescription);
        Bundle data = getIntent().getExtras();
        c = data.getParcelable("selectedComplaint");
        String url = data.getString("url");
        try{
            imageView.setImageURI(url);
        }catch(Exception e){
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }

        int id = c.getId();

        Log.i("4", "onCreate");
        textView.setText(c.getTitle());
        Log.i("5", "onCreate");
        textView2.setText(c.getDescription());
        Log.i("6", "onCreate");
    }
}