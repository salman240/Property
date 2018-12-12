package com.example.salmangeforce.property.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.salmangeforce.property.Model.User;
import com.example.salmangeforce.property.R;
import com.example.salmangeforce.property.Utils.HelperMethods;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import io.paperdb.Paper;

public class SigninActivity extends AppCompatActivity {


    @BindView(R.id.editTextEmail)
    MaterialEditText etEmail;

    @BindView(R.id.editTextPassword)
    MaterialEditText etPassword;

    @BindView(R.id.buttonSignIn)
    Button btnSignin;

    @BindView(R.id.parent)
    ScrollView parent;

    private Unbinder unbinder;

    private boolean emailTouched;
    private boolean passwordTouched;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        unbinder = ButterKnife.bind(this);
        Paper.init(this);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Sign In");

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Signing In...");


        //init firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        firebaseAuth = FirebaseAuth.getInstance();


        //If redirecting from signup activity
        Intent intent = getIntent();
        if(intent != null) {
            etEmail.setText(intent.getStringExtra("email"));
            etPassword.setText(intent.getStringExtra("password"));
        }

        //Focus listener
        View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && v.getId() == R.id.editTextEmail)
                {
                    emailTouched = true;
                    if(Objects.requireNonNull(etEmail.getText()).toString().equals(""))
                        etEmail.setError("Email is required");
                }
                else if(hasFocus && v.getId() == R.id.editTextPassword)
                {
                    passwordTouched = true;
                    if(Objects.requireNonNull(etPassword.getText()).toString().equals(""))
                        etPassword.setError("Password is required");
                }
            }
        };

        etEmail.setOnFocusChangeListener(onFocusChangeListener);
        etPassword.setOnFocusChangeListener(onFocusChangeListener);


        //Adding Text Watcher
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(Objects.requireNonNull(etEmail.getText()).toString().equals(""))
                {
                    etEmail.setError("Email is required");
                }
                else if(!HelperMethods.isEmailValid(etEmail.getText().toString()))
                {
                    etEmail.setError("Please provide valid email");
                }
                else
                {
                    etEmail.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!HelperMethods.isPasswordValid(Objects.requireNonNull(etPassword.getText()).toString()))
                {
                    etPassword.setError("Password must contain 8 or more characters");
                }
                else if(etPassword.getText().toString().equals(""))
                {
                    etPassword.setError("Password is required");
                }
                else
                {
                    etPassword.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        //Button onclick listener
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etEmail.getError() == null && emailTouched && etPassword.getError() ==  null && passwordTouched)
                {
                    if(!HelperMethods.isNetworkConnected(SigninActivity.this))
                    {
                        Toasty.error(SigninActivity.this, "No Internet Connection", Toast.LENGTH_LONG, true).show();
                    }
                    else
                    {
                        progressDialog.show();

                        firebaseAuth.signInWithEmailAndPassword(Objects.requireNonNull(etEmail.getText()).toString(),
                                Objects.requireNonNull(etPassword.getText()).toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                //getting user data from database
                                databaseReference.orderByChild("email").equalTo(etEmail.getText().toString()).
                                        addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        progressDialog.dismiss();

                                        try
                                        {
                                            User user = dataSnapshot.getChildren().iterator().next().getValue(User.class);
                                            if(user != null)
                                            {
                                                //Store data in paper
                                                Paper.book().write("user", user);

                                                Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }

                                        }
                                        catch (Exception e)
                                        {
                                            Toasty.error(SigninActivity.this, "You are not allowed", Toast.LENGTH_LONG, true).show();
                                        }

                                                                            }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        progressDialog.dismiss();
                                        Toasty.error(SigninActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG, true).show();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toasty.error(SigninActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG, true).show();
                            }
                        });
                    }
                }
                else
                {
                    Toasty.warning(SigninActivity.this, "Please provide details firstly!", Toast.LENGTH_SHORT, true).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_signin, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId() == R.id.reg)
//        {
//            Intent intent = new Intent(SigninActivity.this, SignupActivity.class);
//            startActivity(intent);
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    //helper methods
    public void openSignin(View view)
    {
        Intent intent = new Intent(SigninActivity.this, SignupActivity.class);
        startActivity(intent);
        finish();
    }

}
