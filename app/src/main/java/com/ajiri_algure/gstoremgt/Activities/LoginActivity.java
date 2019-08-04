package com.ajiri_algure.gstoremgt.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ajiri_algure.gslider.GslideActivity;
import com.ajiri_algure.gstoremgt.Customer;
import com.ajiri_algure.gstoremgt.R;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;

public class LoginActivity extends GslideActivity {


    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private RelativeLayout signuprl;
    private RelativeLayout rl;
    Button sup;
    public FirebaseAuth fbauth=null;
    SharedPreferences userprefs;
    SharedPreferences.Editor usereditor;
    DatabaseReference database;
    private SharedPreferences sp;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        fbauth= FirebaseAuth.getInstance();
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        llout=findViewById(R.id.llout);
        signuprl=findViewById(R.id.signuprl);
        signuprl.setVisibility(View.GONE);
        database= FirebaseDatabase.getInstance().getReference().child("Customers");
        userprefs=getSharedPreferences("user_prefs",MODE_PRIVATE);
        usereditor=userprefs.edit();
        rl=(RelativeLayout)findViewById(R.id.slide);
        Drawable[] ndrawable={getResources().getDrawable(R.drawable.t1),
                getResources().getDrawable(R.drawable.t2),
                getResources().getDrawable(R.drawable.t3),
                getResources().getDrawable(R.drawable.t4),
                getResources().getDrawable(R.drawable.t5)};
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        sp=getSharedPreferences("user_prefs",MODE_PRIVATE);
        editor=sp.edit();
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        sup=findViewById(R.id.sup);
        sup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                attemptLogin(view);
            }
        });
    }

    public void openGup(View view){
//        startActivity(new Intent(this,GstoreUpload.class));
    }
    private void attemptLogin() {
        try {
            if (mAuthTask != null) {
                return;
            }

            // Reset errors.
            mEmailView.setError(null);
            mPasswordView.setError(null);

            // Store values at the time of the login attempt.
            String email = mEmailView.getText().toString();
            String password = mPasswordView.getText().toString();

            boolean cancel = false;
            View focusView = null;

            // Check for a valid password, if the user entered one.
            if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
                mPasswordView.setError(getString(R.string.error_invalid_password));
                focusView = mPasswordView;
                cancel = true;
            }

            // Check for a valid email address.
            if (TextUtils.isEmpty(email)) {
                mEmailView.setError(getString(R.string.error_field_required));
                focusView = mEmailView;
                cancel = true;
            } else if (!isEmailValid(email)) {
                mEmailView.setError(getString(R.string.error_invalid_email));
                focusView = mEmailView;
                cancel = true;
            }

            if (cancel) {
                // There was an error; don't attempt login and focus the first
                // form field with an error.
                focusView.requestFocus();
            } else {
                // Show a progress spinner, and kick off a background task to
                // perform the user login attempt.
                showProgress(true);
                mAuthTask = new UserLoginTask(email, password);
                mAuthTask.execute((Void) null);
            }
        }catch (Exception e){
            Log.i("khuods",e.toString());
        }
    }
    private void attemptSignup(View view) {
        try {

            if (mAuthTask != null) {
                return;
            }

            EditText mEmailView=findViewById(R.id.e1);
            EditText mPasswordView=findViewById(R.id.pass1);
            EditText mpass2=findViewById(R.id.pass2);
            // Reset errors.
            mEmailView.setError(null);
            mPasswordView.setError(null);

            // Store values at the time of the login attempt.
            String email = mEmailView.getText().toString();
            String password = mPasswordView.getText().toString();

            boolean cancel = false;
            View focusView = null;

            // Check for a valid password, if the user entered one.
            if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
                mPasswordView.setError(getString(R.string.error_invalid_password));
                focusView = mPasswordView;
                cancel = true;
            }

            // Check for a valid email address.
            if (TextUtils.isEmpty(email)) {
                mEmailView.setError(getString(R.string.error_field_required));
                focusView = mEmailView;
                cancel = true;
            } else if (!isEmailValid(email)) {
                mEmailView.setError(getString(R.string.error_invalid_email));
                focusView = mEmailView;
                cancel = true;
            }

            if (cancel) {
                // There was an error; don't attempt login and focus the first
                // form field with an error.
                focusView.requestFocus();
            } else {
                final String mEmail = mEmailView.getText().toString();
                final String mPassword = mPasswordView.getText().toString();
                String p2 = mpass2.getText().toString();
//
//
                if (!p2.matches(mPassword)) {
                    mPasswordView.setError("passsword doesn't match");
                    focusView = mEmailView;
                    cancel = true;
                    focusView.requestFocus();
                } else {
                    showProgress(true);
                    signuprl.setVisibility(View.GONE);
                    UserSignUp utask=new UserSignUp(mEmail,mPassword);
                    utask.execute();
                }
            }
        }catch (Exception e){
            Log.i("khuods",e.toString());
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@")&&email.contains(".com")&&email.length()>8;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return (password.length() >= 8) && (password.contains("1")||password.contains("2")||password.contains("3")||password.contains("4")
                ||password.contains("5")||password.contains("6")||password.contains("7")||password.contains("8")||password.contains("9")||(password.contains("0")))
                ;
    }
    public void googlesignup(View view){
//        Intent sintent=
    }
    AlertDialog.Builder dialog;

    public void signUpdialog(){
        try {
            final Boolean[] register = {false};
            AlertDialog.Builder dialg = new AlertDialog.Builder(this);
            dialg.setMessage("Signup as new user");
            dialg.setPositiveButton("Signup", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    register[0] = true;
                    startSignup();
                }
            });
            dialg.setTitle("Login Failed");
            dialg.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            dialg.create();
            dialg.show();
//
        }catch (Exception e){
            Log.i("jygiu",e.toString());
        }
    }
    EditText e1;
    EditText pass1;
    EditText pass2;
    public void startSignup(){
        signuprl.setVisibility(View.VISIBLE);
    }
    public void signUp(DialogInterface d) {
        try {

            final String mEmail = e1.getText().toString();
            final String mPassword = pass1.getText().toString();
            String p2 = pass2.getText().toString();


            if (!p2.matches(mPassword)) {
                //Toast.makeText(getApplicationContext(), "password doesn't match", Toast.LENGTH_LONG).show();
            } else {
                showProgress(true);
                fbauth.createUserWithEmailAndPassword(mEmail, mPassword).
                        addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.i("sdsfds", "createusersuccess");
                                    FirebaseUser user = fbauth.getCurrentUser();

                                    usereditor.putString("email",mEmail);
                                    usereditor.putString("password",mPassword);
                                    usereditor.commit();
                                    String id = database.push().toString().replaceAll("\\-","")//setting id
                                            .replaceAll("\\#","")
                                            .replaceAll("\\.","")
                                            .replaceAll("\\$","")
                                            .replaceAll("\\[","")
                                            .replaceAll("\\]","");

                                    Customer c=new Customer(mEmail,mPassword,id);
                                    database.child(id).setValue(c, new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                            Toast.makeText(getBaseContext(),"Registration complete",Toast.LENGTH_SHORT).show();
                                            enterApp();
                                        }
                                    });

                                } else {
                                    Log.i("oijzdpo", "createuserfailed", task.getException());
                                }
                            }
                        });
            }
            d.cancel();
            showProgress(false);

        }catch (Exception e){
            Log.i(";opsdfp[",e.toString());
        }
        enterApp();
        showProgress(false);
    }
    @Override
    protected void onStart() {
        super.onStart();
        try {
            FirebaseUser currentUser = fbauth.getCurrentUser();
            if (!currentUser.equals(null)) {
                enterApp();
            }
        }catch (Exception e){
            Log.i("kjlijo",e.toString());
        }
    }
    LinearLayout llout;
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        llout.setVisibility(show?View.VISIBLE:View.GONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

//            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//                }
//            });

//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mProgressView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//                }
//            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
    public void enterApp(){
        startActivity(new Intent(this,Main2Activity.class));
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private boolean successLogin,successsignup;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.


            try {
                fbauth.signInWithEmailAndPassword(mEmail, mPassword)
                        .addOnCompleteListener(getParent(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("kkjmoa", "signInWithEmail:success");
                                    FirebaseUser user = fbauth.getCurrentUser();
                                    editor.putString("id",user.getUid());
                                    editor.commit();
                                    successLogin = true;
                                    publishProgress();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("lkmzo", "signInWithEmail:failure", task.getException());
                                    successLogin = false;
                                    publishProgress();
                                }

                                // ...
                            }
                        });
            }catch (Exception e){
                Log.i("kjopozsd",e.toString());
            }
            // TODO: register the new account here.
//
            return true;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            showProgress(false);
            if(successLogin){
                Toast.makeText(getApplicationContext(),"customer saved",Toast.LENGTH_SHORT).show();
                enterApp();
            }else{
                Toast.makeText(getApplicationContext(),"login failed",Toast.LENGTH_SHORT).show();
//                signUpdialog();
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                //finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
    public class UserSignUp extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private boolean successLogin,successsignup;

        UserSignUp(String email, String password) {
            mEmail = email;
            mPassword = password;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.


            try {
                fbauth.createUserWithEmailAndPassword(mEmail, mPassword).
                        addOnCompleteListener(getParent(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.i("sdsfds", "createusersuccess");
                                    FirebaseUser user = fbauth.getCurrentUser();
                                    String id = database.push().toString().replaceAll("\\-","")//setting id
                                            .replaceAll("\\#","")
                                            .replaceAll("\\.","")
                                            .replaceAll("\\$","")
                                            .replaceAll("\\[","")
                                            .replaceAll("\\]","");

                                    usereditor.putString("email",mEmail);
                                    usereditor.putString("password",mPassword);
                                    usereditor.putString("id",id);
                                    usereditor.commit();

                                    Customer c=new Customer(mEmail,mPassword,id);
                                    database.child(id).setValue(c, new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                            successLogin=true;
                                            publishProgress();
                                        }
                                    });

                                } else {
                                    Log.i("oijzdpo", "createuserfailed", task.getException());
                                }
                            }
                        });
            }catch (Exception e){
                Log.i("kjopozsd",e.toString());
            }
            // TODO: register the new account here.
//
            return true;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            showProgress(false);
            if(successLogin){
                Toast.makeText(getApplicationContext(),"customer created",Toast.LENGTH_SHORT).show();
                enterApp();
            }else{
                Toast.makeText(getApplicationContext(),"create user failed",Toast.LENGTH_SHORT).show();
            }
        }



        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                //finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}
