package com.example.salmangeforce.property;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

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

    @BindView(R.id.editTextName)
    MaterialEditText etName;

    @BindView(R.id.editTextEmail)
    MaterialEditText etEmail;

    @BindView(R.id.editTextPassword)
    MaterialEditText etPassword;

    @BindView(R.id.editTextConfirmPassword)
    MaterialEditText etConfirmPassword;

    @BindView(R.id.editTextCompanyName)
    MaterialEditText etCompany;

    @BindView(R.id.editTextAddress)
    MaterialEditText etAddress;

    @BindView(R.id.editTextPhone)
    MaterialEditText etPhone;
    
    @BindView(R.id.buttonSignUp)
    Button btnSignup;

    private Unbinder unbinder;

    private boolean nameTouched;
    private boolean emailTouched;
    private boolean passwordTouched;
    private boolean confirmPasswordTouched;
    private boolean companyTouched;
    private boolean addressTouched;
    private boolean phoneTouched;

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
                if(hasFocus && v.getId() == R.id.editTextName)
                {
                    nameTouched = true;
                    if(etName.getText().toString().equals(""))
                        etName.setError("Name is required");
                }
                else if(hasFocus && v.getId() == R.id.editTextEmail)
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
                else if(hasFocus && v.getId() == R.id.editTextConfirmPassword)
                {
                    confirmPasswordTouched = true;
                    if(etConfirmPassword.getText().toString().equals(""))
                        etConfirmPassword.setError("Confirm Password is required");
                }
                else if(hasFocus && v.getId() == R.id.editTextCompanyName)
                {
                    companyTouched = true;
                    if(etCompany.getText().toString().equals(""))
                        etCompany.setError("Company is required");
                }
                else if(hasFocus && v.getId() == R.id.editTextAddress)
                {
                    addressTouched = true;
                    if(etAddress.getText().toString().equals(""))
                        etAddress.setError("Address is required");
                }
                else if(hasFocus && v.getId() == R.id.editTextPhone)
                {
                    phoneTouched = true;
                    if(etPhone.getText().toString().equals(""))
                        etPhone.setError("Phone is required");
                }
            }
        };

        etName.setOnFocusChangeListener(onFocusChangeListener);
        etEmail.setOnFocusChangeListener(onFocusChangeListener);
        etPassword.setOnFocusChangeListener(onFocusChangeListener);
        etConfirmPassword.setOnFocusChangeListener(onFocusChangeListener);
        etCompany.setOnFocusChangeListener(onFocusChangeListener);
        etAddress.setOnFocusChangeListener(onFocusChangeListener);
        etPhone.setOnFocusChangeListener(onFocusChangeListener);


        //Text watcher
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(Objects.requireNonNull(etName.getText()).toString().equals(""))
                {
                    etName.setError("Name is required");
                }
                else
                {
                    etName.setError(null);
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

        etConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!HelperMethods.isPasswordValid(etConfirmPassword.getText().toString()))
                {
                    etConfirmPassword.setError("Password must contain 8 or more characters");
                }
                else if(etConfirmPassword.getText().toString().equals(""))
                {
                    etConfirmPassword.setError("Confirm Password is required");
                }
                else if(!etConfirmPassword.getText().toString().equals(Objects.requireNonNull(etPassword.getText()).toString()))
                {
                    etConfirmPassword.setError("Password do not match");
                }
                else
                {
                    etConfirmPassword.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(Objects.requireNonNull(etAddress.getText()).toString().equals(""))
                {
                    etAddress.setError("Phone is required");
                }
                else
                {
                    etAddress.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etCompany.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(Objects.requireNonNull(etCompany.getText()).toString().equals(""))
                {
                    etCompany.setError("Company name is required");
                }
                else
                {
                    etCompany.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(Objects.requireNonNull(etPhone.getText()).toString().equals(""))
                {
                    etPhone.setError("Phone is required");
                }
                else
                {
                    etPhone.setError(null);
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
