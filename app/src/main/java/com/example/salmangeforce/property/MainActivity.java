package com.example.salmangeforce.property;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.SimpleOnSearchActionListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.searchBar)
    MaterialSearchBar materialSearchBar;

    @BindView(R.id.card)
    CardView cardView;

    private FirebaseAuth firebaseAuth;
    private boolean isDoublePressed;
    private Unbinder unbinder;
    private List<String> suggested;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        suggested = new ArrayList<>();
        suggested.add("Lahore");
        suggested.add("Karachi");
        suggested.add("Islamabad");
        suggested.add("Peshawar");
        suggested.add("Quetta");
        suggested.add("Qasoor");

        materialSearchBar.setCardViewElevation(4);
        materialSearchBar.setLastSuggestions(suggested);

        firebaseAuth = FirebaseAuth.getInstance();

        //search bar listeners
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<String> search = new ArrayList<>();
                for(String newSearch : suggested)
                {
                    if(newSearch.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                    {
                        search.add(newSearch);
                    }
                }
                materialSearchBar.setLastSuggestions(search);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        materialSearchBar.setOnSearchActionListener(new SimpleOnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
//                if(!enabled)
//                    Toasty.info(MainActivity.this, "Showing Results in next century", Toast.LENGTH_LONG, true).show();
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                if(text.length() > 0) {
                    Toasty.info(MainActivity.this, "Showing Results in next century", Toast.LENGTH_LONG, true).show();
                    cardView.setVisibility(View.VISIBLE);
                    materialSearchBar.disableSearch();
//                    search(text);
                }
                else {
                    Toast.makeText(MainActivity.this, "Oops, you forgot to enter property", Toast.LENGTH_SHORT).show();
                    materialSearchBar.disableSearch();
                }
            }

            @Override
            public void onButtonClicked(int buttonCode) {
                super.onButtonClicked(buttonCode);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.logout)
        {
            firebaseAuth.signOut();
            Intent intent = new Intent(MainActivity.this, SigninActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(isDoublePressed) {
            super.onBackPressed();
        }
        else {
            isDoublePressed = true;
            Toasty.info(MainActivity.this, "Click again to exit", Toast.LENGTH_SHORT, true).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isDoublePressed = false;
                }
            }, 2000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}//class