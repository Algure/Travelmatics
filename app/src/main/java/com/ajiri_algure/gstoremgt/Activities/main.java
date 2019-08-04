package com.ajiri_algure.gstoremgt.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ajiri_algure.gstoremgt.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class main extends AppCompatActivity {

    GoogleSignInClient mGc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
         mGc=new GoogleSignIn.getClient(this,gso);
    }
    public void nextLogin(View view){
        startActivity(new Intent(this,LoginActivity.class));
    }
    public void gLogin(View view){
        Intent sin=mGc.getSignInIntent();
        startActivityForResult(sin,111);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==111){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try{
            GoogleSignInAccount acc=task.getResult(ApiException.class);
            startActivity(new Intent(this,Mymart.class));
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }
}
