package com.example.baking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.baking.models.BakingModel;

import java.util.ArrayList;

import static com.example.baking.MainActivity.EXTRA_INGREDIENTS;
import static com.example.baking.MainActivity.EXTRA_STEPS;


public class DetailActivity extends AppCompatActivity {

    private RecyclerView dRecyclerView;
    private ArrayList<BakingModel.Steps> dStepsList;

    /** Adapter for the gridview of movies from the JSON data */
    private DetailAdapter dAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        dRecyclerView =  findViewById(R.id.recyclerDetail_view);

        Intent intent = getIntent();
        String ingredients = intent.getStringExtra(EXTRA_INGREDIENTS);
        ArrayList steps = intent.getStringArrayListExtra(EXTRA_STEPS);
       // Log.i("DetailActivity", "steps " + steps.getDescription());

        // Create a new adapter that takes an empty list of movies as input
        dStepsList = steps;
        Log.i("DetailActivity", "steps " + steps);

        dAdapter = new DetailAdapter(DetailActivity.this, dStepsList);
        dRecyclerView.setHasFixedSize(true);
        dRecyclerView.setAdapter(dAdapter);
        dRecyclerView.setLayoutManager(new LinearLayoutManager(DetailActivity.this));

        // Set the adapter on the {@link GridView} so the list can be populated in the user interface
        TextView textIngredients = findViewById(R.id.txtIngredientsList);
        textIngredients.setText(ingredients);

    }
}
