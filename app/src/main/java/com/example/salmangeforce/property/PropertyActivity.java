package com.example.salmangeforce.property;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PropertyActivity extends AppCompatActivity {

    @BindView(R.id.textViewInfo)
    TextView textViewInfo;

    @BindView(R.id.spinner_search_radius)
    com.rey.material.widget.Spinner spinnerSearchRadius;

    @BindView(R.id.spinner_min_price)
    com.rey.material.widget.Spinner spinnerMinPrice;

    @BindView(R.id.spinner_max_price)
    com.rey.material.widget.Spinner spinnerMaxPrice;

    @BindView(R.id.spinner_min_beds)
    com.rey.material.widget.Spinner spinnerMinBeds;

    @BindView(R.id.spinner_max_beds)
    com.rey.material.widget.Spinner spinnerMaxBeds;

    @BindView(R.id.spinner_prop_type)
    com.rey.material.widget.Spinner spinnerPropType;

    @BindView(R.id.spinner_added_to_site)
    com.rey.material.widget.Spinner spinnerAddedToSite;

    private Unbinder unbinder;

    private ArrayList<String> searchRadius;
    private ArrayList<String> propertyType;
    private ArrayList<String> addedToSite;
    private ArrayList<String> minPrice;
    private ArrayList<String> maxPrice;
    private ArrayList<String> minBeds;
    private ArrayList<String> maxBeds;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);
        unbinder = ButterKnife.bind(this);

        Intent intent = getIntent();
        if(intent.getStringExtra("prop_action") != null && intent.getStringExtra("prop_area") != null)
        {
//            textViewInfo.setText(Html.fromHtml("<h2>Property for <u>" + intent.getStringExtra("prop_action") +
//                    "</u> in <font color='#444444'>" + intent.getStringExtra("prop_area") + "</font></h2>"));

            Objects.requireNonNull(getSupportActionBar()).setTitle(intent.getStringExtra("prop_area") + " ~ For " +
                    intent.getStringExtra("prop_action"));
        }


        //Dummy data
        initData();

        ArrayAdapter<String> searchRadiusAdapter = new ArrayAdapter<>(this, R.layout.row_spn, searchRadius);
        searchRadiusAdapter.setDropDownViewResource(R.layout.row_spn_dropdown);

        ArrayAdapter<String> minPriceAdapter = new ArrayAdapter<>(this, R.layout.row_spn, minPrice);
        minPriceAdapter.setDropDownViewResource(R.layout.row_spn_dropdown);

        ArrayAdapter<String> maxPriceAdapter = new ArrayAdapter<>(this, R.layout.row_spn, maxPrice);
        maxPriceAdapter.setDropDownViewResource(R.layout.row_spn_dropdown);

        ArrayAdapter<String> minBedsAdapter = new ArrayAdapter<>(this, R.layout.row_spn, minBeds);
        minBedsAdapter.setDropDownViewResource(R.layout.row_spn_dropdown);

        ArrayAdapter<String> maxBedsAdapter = new ArrayAdapter<>(this, R.layout.row_spn, maxBeds);
        maxBedsAdapter.setDropDownViewResource(R.layout.row_spn_dropdown);

        ArrayAdapter<String> propTypeAdapter = new ArrayAdapter<>(this, R.layout.row_spn, propertyType);
        propTypeAdapter.setDropDownViewResource(R.layout.row_spn_dropdown);

        ArrayAdapter<String> addedToSiteAdapter = new ArrayAdapter<>(this, R.layout.row_spn, addedToSite);
        addedToSiteAdapter.setDropDownViewResource(R.layout.row_spn_dropdown);

        spinnerSearchRadius.setAdapter(searchRadiusAdapter);
        spinnerMinPrice.setAdapter(minPriceAdapter);
        spinnerMaxPrice.setAdapter(maxPriceAdapter);
        spinnerMinBeds.setAdapter(minBedsAdapter);
        spinnerMaxBeds.setAdapter(maxBedsAdapter);
        spinnerPropType.setAdapter(propTypeAdapter);
        spinnerAddedToSite.setAdapter(addedToSiteAdapter);
    }

    //Helper
    private void initData() {
        searchRadius = new ArrayList<>();
        minPrice = new ArrayList<>();
        maxPrice = new ArrayList<>();
        minBeds = new ArrayList<>();
        maxBeds = new ArrayList<>();
        propertyType = new ArrayList<>();
        addedToSite = new ArrayList<>();

        searchRadius.add("This area only");
        searchRadius.add("Within 1/2 mile");
        searchRadius.add("Within 1/4 mile");
        searchRadius.add("Within 1 mile");
        searchRadius.add("Within 3 mile");
        searchRadius.add("Within 5 mile");
        searchRadius.add("Within 10 mile");
        searchRadius.add("Within 15 mile");
        searchRadius.add("Within 20 mile");
        searchRadius.add("Within 30 mile");
        searchRadius.add("Within 40 mile");

        minPrice.add("no min");
        minPrice.add("50000");
        minPrice.add("60000");
        minPrice.add("70000");
        minPrice.add("80000");
        minPrice.add("90000");
        minPrice.add("100000");
        minPrice.add("110000");
        minPrice.add("120000");
        minPrice.add("125000");
        minPrice.add("130000");
        minPrice.add("140000");
        minPrice.add("150000");
        minPrice.add("160000");
        minPrice.add("170000");
        minPrice.add("175000");
        minPrice.add("180000");
        minPrice.add("190000");
        minPrice.add("200000");
        minPrice.add("210000");
        minPrice.add("220000");
        minPrice.add("230000");
        minPrice.add("240000");
        minPrice.add("250000");
        minPrice.add("260000");
        minPrice.add("270000");
        minPrice.add("280000");
        minPrice.add("290000");
        minPrice.add("300000");
        minPrice.add("325000");
        minPrice.add("350000");
        minPrice.add("375000");
        minPrice.add("400000");
        minPrice.add("425000");
        minPrice.add("450000");
        minPrice.add("475000");
        minPrice.add("500000");
        minPrice.add("550000");
        minPrice.add("600000");
        minPrice.add("650000");
        minPrice.add("700000");
        minPrice.add("800000");
        minPrice.add("900000");
        minPrice.add("1000000");
        minPrice.add("1250000");
        minPrice.add("1500000");
        minPrice.add("1750000");
        minPrice.add("2000000");
        minPrice.add("2500000");
        minPrice.add("3000000");
        minPrice.add("4000000");
        minPrice.add("5000000");
        minPrice.add("7500000");
        minPrice.add("10000000");
        minPrice.add("15000000");
        minPrice.add("20000000");

        maxPrice.add("no max");
        maxPrice.add("50000");
        maxPrice.add("60000");
        maxPrice.add("70000");
        maxPrice.add("80000");
        maxPrice.add("90000");
        maxPrice.add("100000");
        maxPrice.add("110000");
        maxPrice.add("120000");
        maxPrice.add("125000");
        maxPrice.add("130000");
        maxPrice.add("140000");
        maxPrice.add("150000");
        maxPrice.add("160000");
        maxPrice.add("170000");
        maxPrice.add("175000");
        maxPrice.add("180000");
        maxPrice.add("190000");
        maxPrice.add("200000");
        maxPrice.add("210000");
        maxPrice.add("220000");
        maxPrice.add("230000");
        maxPrice.add("240000");
        maxPrice.add("250000");
        maxPrice.add("260000");
        maxPrice.add("270000");
        maxPrice.add("280000");
        maxPrice.add("290000");
        maxPrice.add("300000");
        maxPrice.add("325000");
        maxPrice.add("350000");
        maxPrice.add("375000");
        maxPrice.add("400000");
        maxPrice.add("425000");
        maxPrice.add("450000");
        maxPrice.add("475000");
        maxPrice.add("500000");
        maxPrice.add("550000");
        maxPrice.add("600000");
        maxPrice.add("650000");
        maxPrice.add("700000");
        maxPrice.add("800000");
        maxPrice.add("900000");
        maxPrice.add("1000000");
        maxPrice.add("1250000");
        maxPrice.add("1500000");
        maxPrice.add("1750000");
        maxPrice.add("2000000");
        maxPrice.add("2500000");
        maxPrice.add("3000000");
        maxPrice.add("4000000");
        maxPrice.add("5000000");
        maxPrice.add("7500000");
        maxPrice.add("10000000");
        maxPrice.add("15000000");
        maxPrice.add("20000000");

        minBeds.add("no min");
        minBeds.add("1");
        minBeds.add("2");
        minBeds.add("3");
        minBeds.add("4");
        minBeds.add("5");

        maxBeds.add("no max");
        maxBeds.add("1");
        maxBeds.add("2");
        maxBeds.add("3");
        maxBeds.add("4");
        maxBeds.add("5");

        propertyType.add("Any");
        propertyType.add("Houses");
        propertyType.add("Flats/Apartments");
        propertyType.add("Bungalows");
        propertyType.add("Land");
        propertyType.add("Commercial Property");
        propertyType.add("Other");

        addedToSite.add("Anytime");
        addedToSite.add("Last 24 hours");
        addedToSite.add("Last 3 days");
        addedToSite.add("Last 7 days");
        addedToSite.add("Last 14 days");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
