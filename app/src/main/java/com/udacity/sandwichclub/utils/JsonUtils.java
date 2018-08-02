package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonUtils {

    private static final String TAG = JsonUtils.class.getName();
    public static final String NAME = "name";
    public static final String MAIN_NAME = "mainName";
    public static final String ALSO_KNOWN_AS = "alsoKnownAs";
    public static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    public static final String DESCRIPTION = "description";
    public static final String IMAGE = "image";
    public static final String INGREDIENTS = "ingredients";


    public static Sandwich parseSandwichJson(String json) {
        Sandwich resultingSandwich = null;
        try {
            JSONObject sandwich = new JSONObject(json);

            //name
            JSONObject name = sandwich.getJSONObject(NAME);
            String mainName = name.getString(MAIN_NAME);
            List<String> alsoKnownAsStrings = convertJsonArrayToStringList(name.getJSONArray(ALSO_KNOWN_AS));

            // placeOfOrigin
            String placeOfOrigin = sandwich.getString(PLACE_OF_ORIGIN);

            // description
            String description = sandwich.getString(DESCRIPTION);

            // image
            String imageLink = sandwich.getString(IMAGE);

            // ingredients
            List<String> ingredients = convertJsonArrayToStringList(sandwich.getJSONArray(INGREDIENTS));

            resultingSandwich = new Sandwich(mainName, alsoKnownAsStrings, placeOfOrigin, description, imageLink, ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultingSandwich;
    }

    private static List<String> convertJsonArrayToStringList(JSONArray inputJSONArray) throws JSONException {
        List<String> resultingStrings = new ArrayList<>();
        for (int i = 0; i < inputJSONArray.length(); i++) {
            resultingStrings.add(inputJSONArray.getString(i));
            Log.d(TAG, resultingStrings.get(i));
        }
        return resultingStrings;
    }
}