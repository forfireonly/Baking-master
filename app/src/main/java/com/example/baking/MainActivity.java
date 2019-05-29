package com.example.baking;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.baking.models.BakingModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.OnItemClickListener {

    /** Adapter for the gridview of movies from the JSON data */
    private RecyclerView rRecyclerView;
    private RecipeAdapter rAdapter;
    public final static String RECIPESTRING = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private ArrayList<RecipeList> rRecipeList;
    private RequestQueue rRequestQueue;
    private ArrayList<BakingModel> bakingModelList;
    private ArrayList<BakingModel.Steps> stepsArrayList;

    ArrayList<ArrayList<BakingModel.Steps>> outer;

    public static final String EXTRA_INGREDIENTS = "ingredients";
    public static final String EXTRA_STEPS = "steps";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bakingModelList = new ArrayList<>();

        outer = new ArrayList<ArrayList<BakingModel.Steps>>();

        stepsArrayList = new ArrayList<BakingModel.Steps>();





        rRecyclerView =  findViewById(R.id.recycler_view);
        rRecyclerView.setHasFixedSize(true);
        rRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        rRecipeList = new ArrayList<>();
        rRequestQueue = Volley.newRequestQueue(this);
        parseJSON();
        Log.i("MainActivity", "END OF APP;onCreate");

    }

    private void parseJSON() {
        String url = RECIPESTRING;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            //List<BakingModel> bakingModelList = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject recipeName = response.getJSONObject(i);
                                BakingModel bakingModel = new BakingModel();
                                bakingModel.setRecipeName(recipeName.getString("name"));
                                //stepsArrayList.clear();

                                Log.v("i", String.valueOf(i));
                                //final List<String> ingredientsList = new ArrayList<>();
                                 String ingredientsList = "";
                                for (int j = 0; j < recipeName.getJSONArray("ingredients").length(); j++){
                                    JSONObject ingredientsObject = recipeName.getJSONArray("ingredients").getJSONObject(j);
                                    BakingModel.Ingredients ingredients = new BakingModel.Ingredients();
                                    ingredients.setQuantity(ingredientsObject.getDouble("quantity"));
                                    ingredients.setMeasure(ingredientsObject.getString("measure"));
                                    ingredients.setIngredient(ingredientsObject.getString("ingredient"));//
                                    //Log.i("LOG MainActivity for lp", ingredients.getQuantity() + " " +
                                    //        ingredients.getMeasure() + "\t\t" + ingredients.getIngredient() + "\n");

                                    ingredientsList +=( ingredients.getQuantity() + " " +
                                            ingredients.getMeasure() + "\t\t" + ingredients.getIngredient() + "\n");
                                }
                                Log.i("LOG ingredientsList: ",  ingredientsList.toString());
                                bakingModel.setIngredientsList(ingredientsList);



                                //stepsArrayList.clear();
                               // stepsArrayList = new ArrayList<BakingModel.Steps>(stepsArrayList);



                                 for (int k = 0; k < recipeName.getJSONArray("steps").length(); k++) {

                                        JSONObject stepsObject = recipeName.getJSONArray("steps").getJSONObject(k);
                                        BakingModel.Steps steps = new BakingModel.Steps();
                                        steps.setShortDescription(stepsObject.getString("shortDescription"));
                                        steps.setDescription(stepsObject.getString("description"));
                                        steps.setVideoURL(stepsObject.getString("videoURL"));
                                        steps.setThumbnailURL(stepsObject.getString("thumbnailURL"));
                                        //Log.i("LOG MainActivity for lp", steps.getShortDescription() + " "
                                        //        + steps.getDescription() + "\t" + steps.getVideoURL() + steps.getThumbnailURL() + "\n");


                                     stepsArrayList.add(steps);}

                                outer.add(stepsArrayList);

                                bakingModel.setStepsList(outer);

                                //stepsArrayList.clear();

                                Log.i("LOG MainActivity", "outer: " + bakingModel.getStepsList());


                                stepsArrayList = new ArrayList<BakingModel.Steps>(stepsArrayList);
                                stepsArrayList.clear();

                                String rServings = recipeName.getString("servings");
                                Log.i("LOG MainActivity", "recipeName: " + bakingModel.getRecipeName());
                                Log.i("LOG MainActivity", "rServings: " + rServings);
                                rRecipeList.add(new RecipeList(bakingModel.getRecipeName(), rServings));
                                // adding the final object in the list
                                bakingModelList.add(bakingModel);
                            }


                            rAdapter = new RecipeAdapter(MainActivity.this, rRecipeList);
                            rRecyclerView.setAdapter(rAdapter);
                            rAdapter.setOnItemClickListener(MainActivity.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        rRequestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {

        Toast.makeText(MainActivity.this,"Card " + position + " pressed", Toast.LENGTH_SHORT).show();
        Log.i("OnClick", "MainActivity");
        Intent detailIntent = new Intent(this, DetailActivity.class);
        BakingModel clickedItem = bakingModelList.get(position);
        BakingModel stepsItem = bakingModelList.get(position);


        Log.i("OnClick", "ingredients " + clickedItem.getIngredientsList());
        Log.i("OnClick", "getStepsList:\n " + stepsItem.getStepsList().get(position));


        detailIntent.putExtra(EXTRA_INGREDIENTS, clickedItem.getIngredientsList());
        detailIntent.putExtra(EXTRA_STEPS, (Serializable) stepsItem.getStepsList().get(position));

        startActivity(detailIntent);
    }
}
