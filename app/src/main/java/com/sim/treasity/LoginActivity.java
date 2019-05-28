package com.sim.treasity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText Login_Mob,Login_Pwd;
    TextView Login_signup_here;

    Button Login_Button;
    CheckBox showPWD;
    SharedPreferences prefs;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        prefs = getSharedPreferences("login", MODE_PRIVATE);
        String restoredUser = prefs.getString("idConnected", null);
        if (restoredUser != null) {
            ((Constants) LoginActivity.this.getApplication()).setTokenUser(prefs.getString("token", null));
            Intent intent=new Intent(LoginActivity.this, DrawerActivity.class);
            intent.putExtra("name",prefs.getString("name", null));
            intent.putExtra("email",prefs.getString("email", null));
            startActivity(intent);
        }

        getSupportActionBar().hide();
        Login_Mob = findViewById(R.id.et_lnmobioe);
        Login_Pwd= findViewById(R.id.et_lnpassword);
        Login_signup_here= findViewById(R.id.tv_signuphere);
        showPWD= findViewById(R.id.show_pwd);
        Login_Button =  findViewById(R.id.btn_login);


        Login_signup_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,Signup.class);
                startActivity(intent);
            }
        });
        showPWD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    Login_Pwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    Login_Pwd.setInputType(129);
                }

            }
        });
        Login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=Login_Mob.getText().toString();
                String password=Login_Pwd.getText().toString();
                String LOGIN_URL = "http://"+getString(R.string.ipAdresse)+"/api/login";
                if (Constants.isOnline(getApplicationContext())){

                    if (username.equalsIgnoreCase("")|| password.equalsIgnoreCase(""))
                    {
                        if (username.equalsIgnoreCase("")){

                            Login_Mob.setError("Pseudo obligatoire");
                        }
                        if(password.equalsIgnoreCase("")){

                            Login_Pwd.setError("Mot de passe obligatoire");
                        }

                    }
                    else{

                        login(LOGIN_URL, username, password);
                    }

                }
                else {
                    Toast.makeText(getApplicationContext(), "Vérifiez votre connexion INTERNET", Toast.LENGTH_SHORT).show();
                }

            }
        });







    }





    private void login(String loginUrl, final String username, final String password) {

        StringRequest postRequest=new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {




                try {
                    JSONObject jsonObject=new JSONObject(response);


                   // Toast.makeText(getApplicationContext(),jsonObject.getString("token"),Toast.LENGTH_LONG).show();


                    String token = jsonObject.getString("token");
                    ((Constants) LoginActivity.this.getApplication()).setTokenUser(token);
                    JSONObject jsonObjectInfo=jsonObject.getJSONObject("user");
                    Integer idConnected= Integer.valueOf(jsonObjectInfo.getString("id"));
                    ((Constants) LoginActivity.this.getApplication()).setIdConnectedUser(String.valueOf(idConnected));


                    //save connected user to shared preferences
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("idConnected", String.valueOf(idConnected));
                    editor.putString("token", token);
                    editor.putString("name", jsonObjectInfo.getString("username"));
                    editor.putString("email", jsonObjectInfo.getString("email"));
                    editor.apply();



                    Intent intent=new Intent(LoginActivity.this, DrawerActivity.class);
                    intent.putExtra("name",jsonObjectInfo.getString("username"));
                    intent.putExtra("email",jsonObjectInfo.getString("email"));
                    startActivity(intent);

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //("eee","eee");
                Toast.makeText(LoginActivity.this," vérifiez votre pseudo et votre mots de passe",Toast.LENGTH_LONG).show();

            }
        })


        {

            @Override
            protected Map<String,String> getParams(){
                Map<String, String> param = new HashMap<String,String>();
                param.put("username", username);
                param.put("password",password);

                return param;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
    }

    public void signupAction(View view) {
        Intent intent=new Intent(LoginActivity.this, Signup.class);
        startActivity(intent);
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
            {
                Toast.makeText(this,"enter pressed",Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }



}
