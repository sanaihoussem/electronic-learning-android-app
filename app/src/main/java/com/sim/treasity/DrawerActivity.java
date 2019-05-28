package com.sim.treasity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sim.treasity.Maps.MapsActivity;
import com.sim.treasity.fragment.DictionFragment;
import com.sim.treasity.fragment.HomeQuizFragment;
import com.sim.treasity.fragment.ListForum;
import com.sim.treasity.fragment.LoiOhmFragment;
import com.sim.treasity.fragment.MenuPrincipalFragment;
import com.sim.treasity.fragment.ProfilFragment;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    CircleImageView profileImage;
    TextView emmailUSer;

    private int PICK_IMAGE_REQUEST = 1;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private Bitmap bitmap;
    private Uri filePath;
    Context context = DrawerActivity.this;



    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        profileImage = hView.findViewById(R.id.profile_image);
        profileImage.setOnClickListener(this);


        getUsername();
        Fragment fragment = new MenuPrincipalFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            this.finishAffinity();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Fragment fragment = null;

        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            fragment = new MenuPrincipalFragment();

        } else if (id == R.id.nav_gallery) {
            fragment = new ListForum();

        } else if (id == R.id.nav_slideshow) {
                fragment = new HomeQuizFragment();

        } else if (id == R.id.nav_manage) {
            fragment = new ProfilFragment();

        } else if (id == R.id.nav_dicos) {
            fragment = new DictionFragment();

        } else if (id == R.id.nav_map) {
            startActivity(new Intent(this,MapsActivity.class));

        } else if (id == R.id.nav_cours) {
            fragment = new LoiOhmFragment();
        } else if (id == R.id.nav_logout) {
            logout();
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        String LOGOUT_URL = "http://"+getString(R.string.ipAdresse)+"/api/logout";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest  =new StringRequest(Request.Method.GET, LOGOUT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response){
                SharedPreferences settings = getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.remove("idConnected");
                editor.remove("token");
                editor.remove("name");
                editor.remove("email");
                editor.apply();
                Intent intent=new Intent(DrawerActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String>
                        param = new HashMap<String, String>();
                param.put("token",((Constants) DrawerActivity.this.getApplication()).getTokenUser());
                return param;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String tokenuser = ((Constants) DrawerActivity.this.getApplication()).getTokenUser();
                String token= tokenuser;
                String bearer = "Bearer ".concat(token);
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<String, String>();
                headersSys.remove("Authorization");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }


    public void getUsername(){

        String HTTP_URL = "http://"+getString(R.string.ipAdresse)+"/api/getUserNameById";

        RequestQueue requestQueue = Volley.newRequestQueue(DrawerActivity.this);

        JsonArrayRequest stringRequest  =new JsonArrayRequest(Request.Method.POST, HTTP_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response){


                NavigationView navigationView = findViewById(R.id.nav_view);
                View hView =  navigationView.getHeaderView(0);
                TextView nav_user = hView.findViewById(R.id.textView3);
                emmailUSer = hView.findViewById(R.id.emailUser);


                try{
                    JSONObject jresponse = response.getJSONObject(0);
                    String username = jresponse.getString("username");
                    nav_user.setText(username);
                    emmailUSer.setText(jresponse.getString("email"));
                    String urlImage = jresponse.getString("image").replace("127.0.0.1",getString(R.string.ipAdresseFolder));
                    Picasso.with(context).load(urlImage).placeholder(R.drawable.avatar)
                            .error(R.drawable.avatar).networkPolicy(NetworkPolicy.NO_CACHE)
                            .memoryPolicy(MemoryPolicy.NO_CACHE).into(profileImage);
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();


            }
        })
        {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                String tokenuser = ((Constants) DrawerActivity.this.getApplication()).getTokenUser();
                String bearer = "Bearer ".concat(tokenuser);
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<String, String>();
                headersSys.remove("Authorization");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }
        };





        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }




        //////////////////////////////////////////////
        /////UPLOAD FUNCTIONS ///////////////////////


        //Requesting permission
        private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    /*
    * This is the method responsible for image upload
    * We need the full image path and the name for the image in this method
    * */
    public void uploadMultipart() {

        //getting the actual path of the image
        String path = getPath(filePath);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();
            String UPLOAD_URL = "http://"+getString(R.string.ipAdresse)+"/treasity/data/AndroidImageUpload/upload.php";
            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, UPLOAD_URL)
                    .addFileToUpload(path, "image") //Adding file
                    .addParameter("userID", ((Constants) this.getApplication()).getIdConnectedUser()) //Adding text parameter to the request
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                profileImage.setImageBitmap(bitmap);
                uploadMultipart();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        assert cursor != null;
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        assert cursor != null;
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    @Override
    public void onClick(View v) {
        if (v == profileImage) {
            requestStoragePermission();
            showFileChooser();
            //uploadMultipart();
        }

    }









}
