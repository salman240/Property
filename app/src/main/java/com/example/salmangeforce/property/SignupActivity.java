package com.example.salmangeforce.property;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.salmangeforce.property.Utils.HelperMethods;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SignupActivity extends AppCompatActivity {

    @BindView(R.id.editTextEmail)
    MaterialEditText etEmail;

    @BindView(R.id.editTextFirstName)
    MaterialEditText etFirstName;

    @BindView(R.id.editTextLastName)
    MaterialEditText etLastName;

    @BindView(R.id.editTextPassword)
    MaterialEditText etPassword;

    private Unbinder unbinder;

    private boolean firstNameTouched;
    private boolean lastNameTouched;
    private boolean emailTouched;
    private boolean passwordTouched;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        unbinder = ButterKnife.bind(this);

        getSupportActionBar().setTitle("Sign Up");

        //Firebase Init
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Signing Up, Please Wait...");

        //Focus Listeners
        View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus && v.getId() == R.id.editTextEmail)
                {
                    emailTouched = true;
                    if(etEmail.getText().toString().equals(""))
                        etEmail.setError("Email is required");
                }
                else if(hasFocus && v.getId() == R.id.editTextPassword)
                {
                    passwordTouched = true;
                    if(etPassword.getText().toString().equals(""))
                        etPassword.setError("Password is required");
                }

                else if(!hasFocus && v.getId() == R.id.editTextFirstName)
                {
                    firstNameTouched = true;
                    if(etFirstName.getText().toString().equals(""))
                        etFirstName.setError("First Name is required");
                }
                else if(hasFocus && v.getId() == R.id.editTextLastName)
                {
                    lastNameTouched = true;
                    if(etLastName.getText().toString().equals(""))
                        etLastName.setError("Last Name is required");
                }
            }
        };

        etEmail.setOnFocusChangeListener(onFocusChangeListener);
        etPassword.setOnFocusChangeListener(onFocusChangeListener);
        etFirstName.setOnFocusChangeListener(onFocusChangeListener);
        etLastName.setOnFocusChangeListener(onFocusChangeListener);

        //Text watcher
        etFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(Objects.requireNonNull(etFirstName.getText()).toString().equals(""))
                {
                    etFirstName.setError("First Name is required");
                }
                else
                {
                    etFirstName.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(Objects.requireNonNull(etLastName.getText()).toString().equals(""))
                {
                    etLastName.setError("Last Name is required");
                }
                else
                {
                    etLastName.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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
                if(!HelperMethods.isPasswordValid(etPassword.getText().toString()))
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
//        btnSignup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(etName.getError() == null && nameTouched
//                    && etEmail.getError() == null && emailTouched
//                    && etPassword.getError() == null && passwordTouched
//                    && etConfirmPassword.getError() == null && confirmPasswordTouched
//                    && etPhone.getError() == null && phoneTouched
//                    && etAddress.getError() == null && addressTouched
//                    && etCompany.getError() == null && companyTouched)
//                {
//                    if(!HelperMethods.isNetworkConnected(SignupActivity.this))
//                    {
//                        Toasty.error(SignupActivity.this, "No Internet Connection!", Toast.LENGTH_LONG, true).show();
//                    }
//                    else
//                    {
//                        progressDialog.show();
//
//                        //Create new user in firebase (auth)
//                        firebaseAuth.createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
//                                .addOnSuccessListener(SignupActivity.this, new OnSuccessListener<AuthResult>() {
//                                    @Override
//                                    public void onSuccess(AuthResult authResult) {
//
//                                        //Storing User in firebase (database)
//                                        User user = new User(etName.getText().toString(),
//                                                etEmail.getText().toString(),
//                                                etPassword.getText().toString(),
//                                                etCompany.getText().toString(),
//                                                etAddress.getText().toString(),
//                                                etPhone.getText().toString());
//
//                                        databaseReference.push().setValue(user)
//                                                .addOnSuccessListener(SignupActivity.this, new OnSuccessListener<Void>() {
//                                                    @Override
//                                                    public void onSuccess(Void aVoid) {
//
//                                                        progressDialog.dismiss();
//                                                        Toasty.success(SignupActivity.this, "User is created successfully!", Toast.LENGTH_LONG, true).show();
//
//                                                        //Opening Sign in Activity
//                                                        Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
//                                                        intent.putExtra("email", etEmail.getText().toString());
//                                                        intent.putExtra("password", etPassword.getText().toString());
//                                                        startActivity(intent);
//                                                        finish();
//
//                                                    }
//                                                }).addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//                                                progressDialog.dismiss();
//                                                Toasty.error(SignupActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG, true).show();
//                                            }
//                                        });
//
//                                    }
//                                }).addOnFailureListener(SignupActivity.this, new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        progressDialog.dismiss();
//                                        Toasty.error(SignupActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG, true).show();
//                                    }
//                                });
//                    }
//                }
//                else
//                {
//                    Toasty.warning(SignupActivity.this, "Please provide details firstly!", Toast.LENGTH_SHORT, true).show();
//                }
//            }
//        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
    
}
