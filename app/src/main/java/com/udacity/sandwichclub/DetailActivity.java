package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String TAG = DetailActivity.class.getName();
    private ImageView image_iv;
    private TextView alsoKnown_tv;
    private TextView ingredients_tv;
    private TextView description_tv;
    private TextView origin_tv;

    private TextView labelAlsoKnown_tv;
    private TextView labelIngredients_tv;
    private TextView labelDescription_tv;
    private TextView labelOrigin_tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        image_iv = findViewById(R.id.image_iv);

        // sandwich content text views
        alsoKnown_tv = findViewById(R.id.also_known_tv);
        ingredients_tv = findViewById(R.id.ingredients_tv);
        description_tv = findViewById(R.id.description_tv);
        origin_tv = findViewById(R.id.origin_tv);

        //labels
        labelAlsoKnown_tv = findViewById(R.id.label_also_known_tv);
        labelIngredients_tv = findViewById(R.id.label_ingredients_tv);
        labelDescription_tv = findViewById(R.id.lable_description_tv);
        labelOrigin_tv = findViewById(R.id.label_origin_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Log.d(TAG, json);
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(image_iv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        if (alsoKnownAs != null && !alsoKnownAs.isEmpty()) {
            alsoKnown_tv.setVisibility(View.VISIBLE);
            labelAlsoKnown_tv.setVisibility(View.VISIBLE);
            alsoKnown_tv.setText(printStringList(alsoKnownAs));
        } else {
            alsoKnown_tv.setVisibility(View.INVISIBLE);
            labelAlsoKnown_tv.setVisibility(View.INVISIBLE);
        }

        List<String> ingredients = sandwich.getIngredients();
        if (ingredients != null && !ingredients.isEmpty()) {
            ingredients_tv.setVisibility(View.VISIBLE);
            labelIngredients_tv.setVisibility(View.VISIBLE);
            ingredients_tv.setText(printStringList(sandwich.getIngredients()));
        } else {
            ingredients_tv.setVisibility(View.INVISIBLE);
            labelIngredients_tv.setVisibility(View.INVISIBLE);
        }

        String description = sandwich.getDescription();
        if (!TextUtils.isEmpty(description)) {
            description_tv.setVisibility(View.VISIBLE);
            labelDescription_tv.setVisibility(View.VISIBLE);
            description_tv.setText(description);
        } else {
            description_tv.setVisibility(View.INVISIBLE);
            labelDescription_tv.setVisibility(View.INVISIBLE);
        }

        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        if (!TextUtils.isEmpty(placeOfOrigin)) {
            origin_tv.setVisibility(View.VISIBLE);
            labelOrigin_tv.setVisibility(View.VISIBLE);
            origin_tv.setText(placeOfOrigin);
        } else {
            origin_tv.setVisibility(View.INVISIBLE);
            labelOrigin_tv.setVisibility(View.INVISIBLE);
        }
    }

    private String printStringList(List<String> alsoKnownAs) {
        StringBuilder sb = new StringBuilder();
        int size = alsoKnownAs.size();

        for (int i = 0; i < size; i++) {
            sb.append(alsoKnownAs.get(i));
            if (i != size - 1) {
                sb.append(",\n");
            }
        }
        return sb.toString();
    }
}
