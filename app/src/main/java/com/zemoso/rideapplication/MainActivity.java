package com.zemoso.rideapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.zemosolabs.zetarget.sdk.ZeTarget;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainActivity extends AppCompatActivity {
    public final static String MESSAGE_KEY="easyway2in.com.mynewapplication.message_key";

    public final static String firstNameKey = "firstName";
    public final static String lastNameKey = "lastName";
    public final static String emailKey = "email";
    public final static String mobileNumberKey = "mobileNumber";
    public final static String MYPREFERENCES = "MyPrefs";
    private final int REQUEST_CODE_LOCATION_PERM = 12;

    EditText message_text;
    EditText message1_text;
    EditText EmailId;
    EditText Mobilenumber;
    LoginButton loginButton;
    CallbackManager callbackManager;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        //Zetarget sdk is inialize ....by id
       ZeTarget.initializeWithContextAndKey(getApplicationContext(), "b5771f64-a917-4630-851c-c064a54369d2");
        ZeTarget.enableDebugging();

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {

                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Intent intent = new Intent(getApplicationContext(), ThirdActivity.class);
                        startActivity(intent);

                    }


                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
        sharedPreferences = getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);

        if(sharedPreferences.contains(firstNameKey)&&sharedPreferences.contains(mobileNumberKey)){

            Intent intent = new Intent(this,ThirdActivity.class);
            startActivity(intent);
        }
        if(AccessToken.getCurrentAccessToken() != null){
            Intent intent = new Intent(this,ThirdActivity.class);
           startActivity(intent);

        }
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.zemoso.rideapplication",
                    PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

//
//        if (ContextCompat.checkSelfPermission(MainActivity.this,
//                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(MainActivity.this,
//                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,},
//                    REQUEST_CODE_LOCATION_PERM);
//        }

        setContentView(R.layout.activity_main);

        message_text=(EditText)findViewById(R.id.message_Text);
        message1_text=(EditText)findViewById(R.id.message1_Text);
        EmailId=(EditText)findViewById(R.id.message2_Text);
        Mobilenumber=(EditText)findViewById(R.id.message3_Text);

        //Here you need to get the shared preferences using the key MYPREFERENCES .
        sharedPreferences = getSharedPreferences(MYPREFERENCES,Context.MODE_PRIVATE);
        //get the value of firstname from the shared pred using the firstNameKey and setting it in the editText message_text
        message_text.setText(sharedPreferences.getString(firstNameKey, null));//default value is assigned null . So that hint can show up
        message1_text.setText(sharedPreferences.getString(lastNameKey,null));
        EmailId.setText(sharedPreferences.getString(emailKey, null));
        Mobilenumber.setText(sharedPreferences.getString(mobileNumberKey,null));


                }
    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    public void sendMessage(View view)
    {

        ////String message = message_text.getText().toString();
        ////String message1 = message_text.getText().toString();
        ////String e1 =Emailid.getText().toString();
        ////String Mn= Mobilenumber.getText().toString();



     String message = message_text.getText().toString();
     String message1 =message1_text.getText().toString();
     String eI=EmailId.getText().toString();
     String Mn= Mobilenumber.getText().toString();
        SharedPreferences.Editor editor=sharedPreferences.edit();
        //key for every thing cannot be same . other wise it will override.
        //Need to put different key for every value .
        editor.putString(firstNameKey, message);
        editor.putString(lastNameKey,message1);
        editor.putString(emailKey,eI);
        editor.putString(mobileNumberKey,Mn);
        editor.commit();
        Intent intent = new Intent(this,ThirdActivity.class);//This will open the third activity
        intent.putExtra(MESSAGE_KEY,message);  //no need to put values in intent becoz already storing in shared preferences.
        intent.putExtra(MESSAGE_KEY, message1);
        intent.putExtra(MESSAGE_KEY, eI);
        intent.putExtra(MESSAGE_KEY, Mn);
        startActivity(intent);

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,},
                    REQUEST_CODE_LOCATION_PERM);
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_LOCATION_PERM: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    Log.e("write", "Write permission granted");
                    // contacts-related task you need to do.
                } else {
                    Toast.makeText(this, "Please accept the permission", Toast.LENGTH_LONG).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
