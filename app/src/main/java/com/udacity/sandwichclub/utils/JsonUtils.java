package com.udacity.sandwichclub.utils;

import android.util.JsonReader;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    public static String TAG = "JsonUtils";

    public static Sandwich parseSandwichJson(String json) {
        JsonReader reader = new JsonReader(new StringReader(json));

        try {
            return readObject(reader);
        } catch(IOException e) {
            return null;
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                Log.v(TAG, "An error occured while attempting to close JSON Reader.");
            }
        }
    }

    private static Sandwich readObject(JsonReader reader) throws IOException {
        String mainName = null;
        List<String> alsoKnownAs = new ArrayList<>();
        String placeOfOrigin = null;
        String description = null;
        String image = null;
        List<String> ingredients = new ArrayList<>();

        reader.beginObject();

        while (reader.hasNext()) {
            switch (reader.nextName()) {
                case "name":
                    reader.beginObject();

                    while (reader.hasNext()) {
                        switch (reader.nextName()) {
                            case "mainName":
                                mainName = reader.nextString();

                                break;

                            case "alsoKnownAs":
                                reader.beginArray();

                                while (reader.hasNext()) {
                                    alsoKnownAs.add(reader.nextString());
                                }

                                reader.endArray();

                                break;

                            default:
                                throw new RuntimeException("Unexpected object key encountered");
                        }
                    }

                    reader.endObject();

                    break;

                case "placeOfOrigin":
                    placeOfOrigin = reader.nextString();

                    break;

                case "description":
                    description = reader.nextString();

                    break;

                case "image":
                    image = reader.nextString();

                    break;

                case "ingredients":
                    reader.beginArray();

                    while (reader.hasNext()) {
                        ingredients.add(reader.nextString());
                    }

                    reader.endArray();

                    break;

                default:
                    throw new RuntimeException("Unexpected object key encountered");
            }
        }

        reader.endObject();

        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}
