package com.example.salmangeforce.property;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.app_name)
    TextView textViewName;

    @BindView(R.id.app_detail)
    TextView textViewDetail;

    @BindView(R.id.app_sub_detail)
    TextView textViewSubDetail;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private Unbinder unbinder;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        unbinder = ButterKnife.bind(this);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Signing In...");

        textViewName.animate().alpha(1).setDuration(1500);
        textViewDetail.animate().alpha(1).setDuration(1500);
        textViewSubDetail.animate().alpha(1).setDuration(1500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(progressBar != null)
                    progressBar.setAlpha(0);

//                checking if user is already signed in
                if(firebaseAuth.getCurrentUser() == null)
                {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            Intent intent = new Intent(SplashActivity.this, SigninActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 500);
                }
                else
                {
                    progressDialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 500);
                }
            }
        }, 2000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onBackPressed() {
    }

}//class ends
