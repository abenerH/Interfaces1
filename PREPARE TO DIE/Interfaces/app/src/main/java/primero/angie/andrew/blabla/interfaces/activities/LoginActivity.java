package primero.angie.andrew.blabla.interfaces.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tumblr.remember.Remember;

import butterknife.Bind;
import butterknife.ButterKnife;
import primero.angie.andrew.blabla.interfaces.API.Api;
import primero.angie.andrew.blabla.interfaces.R;
import primero.angie.andrew.blabla.interfaces.clases.AccessToken;
import primero.angie.andrew.blabla.interfaces.clases.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private ProgressDialog progressDialog;

    @Bind(R.id.input_emailz) EditText _emailText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.Btn_l) Button _loginButton;
    @Bind(R.id.txt_c) TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Start the Signup Activity
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {

        if (!validate()) {
            onLoginFailed();
            return;
        }

        progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Autentificando");
        progressDialog.show();

        final String email = _emailText.getText().toString();
        final String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.
                        onLoginSuccess(email, password);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                // TODO: Implement successful signup logic here
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    //If Login is successful
    public void onLoginSuccess(String email, String password) {

            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            // create call
        Call<AccessToken> call = Api.instance().login(user);
            call.enqueue(new Callback<AccessToken>() {
                @Override
                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {

                    if (response.body() != null) {

                        Log.i("access_token", response.body().getId());

                        progressDialog.dismiss();
                        Remember.putString("access_token", response.body().getId(), new Remember.Callback(){
                            @Override
                            public void apply(Boolean success) {
                                if (success) {
                                    Intent intent = new Intent(getApplicationContext(), Splash.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                    } else {
                        progressDialog.dismiss();
                    }
                }
                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });

    };

    //If Login fails...
    public void onLoginFailed() {

        Toast.makeText(getBaseContext(), R.string.EmailR, Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);

    }
    
    //This is to validate the input data
    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Error");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("Error");
            valid = false;
        } else {
            _passwordText.setError(null);
        }
        return valid;
    }
}
