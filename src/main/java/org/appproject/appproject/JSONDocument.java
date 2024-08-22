package org.appproject.appproject;

import java.util.HashMap;
import java.util.Map;
import java.io.FileInputStream;
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;

// represents a JSON in memory to work with AgiDB class
public class JSONDocument {

    // a hash map that stores key and value
    private final Map<String, Object> map = new HashMap<>();

    public JSONDocument() {

    }

    // constructor that gets a path to a JSON file to store keys and values
    public JSONDocument(String jsonFilePath) throws IOException {

        FileInputStream fis = new FileInputStream(jsonFilePath);
        JSONObject jsonObject = new JSONObject(new JSONTokener(fis));
        fis.close();

        parseJsonObject(jsonObject);

    }

    // constructor that gets a key and a value
    public JSONDocument(String key, Object value) {

        map.put(key, value);

    }

    // constructor that gets a JSONObject from the org.json package
    public JSONDocument(JSONObject jsonObject) {

        parseJsonObject(jsonObject);

    }

    // function to add a key-value pair to the JSON document
    public void append(String key, Object value) {

        map.put(key, value);

    }

    // function to get value by key
    public Object getValue(String key) {

        return  map.get(key);

    }

    // function to return all keys of the hash map as an array
    public String[] getKeys() {

        return map.keySet().toArray(new String[0]);

    }

    // function to check if the given key exists in a hasp map
    public boolean keyExists(String key) {

        // boolean to store the result initializing to false as we don't know if the key exists
        boolean keyExists = false;

        // get keys from the hashmap
        String[] keysArray = map.keySet().toArray(new String[0]);

        // iterate through each key
        for (String ithKey : keysArray) {
            // executes if the ith key is equal to the input key
            if (ithKey.equals(key)) {
                keyExists = true; // set boolean flag to true
                break;
            }
        }

        return keyExists; // return the boolean value

    }

    /*
        function to convert the JSON document to JSON String
        builds a new string which is the textual representation of the JSON itself
    */
    public String toJson() {

        // using a StringBuilder object to append text it in a loop
        StringBuilder jsonBuilder = new StringBuilder("{");

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            jsonBuilder.append("\"").append(entry.getKey()).append("\": ");

            if (entry.getValue() instanceof String) {
                jsonBuilder.append("\"").append(entry.getValue()).append("\"");
            } else {
                jsonBuilder.append(entry.getValue());
            }

            jsonBuilder.append(", ");
        }

        // remove the trailing comma and space, then close the JSON object
        if (jsonBuilder.length() > 1) {
            jsonBuilder.setLength(jsonBuilder.length() - 2);
        }

        jsonBuilder.append("}");
        return jsonBuilder.toString();

    }

    // function to convert a JSONObject to the user defined JSONDocument class object
    private void parseJsonObject(JSONObject jsonObject) {

        for (String key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);
            if (value instanceof JSONObject) {
                JSONDocument subDocument = new JSONDocument();
                subDocument.parseJsonObject((JSONObject) value);
                map.put(key, subDocument);
            } else if (value instanceof JSONArray) {
                map.put(key, parseJsonArray((JSONArray) value));
            } else {
                map.put(key, value);  // handles String, Integer, Boolean, etc.
            }
        }

    }

    // function to convert a JSONArray to an array of user defined JSONDocument class object
    private Object[] parseJsonArray(JSONArray jsonArray) {

        Object[] jsonDocumentArray = new Object[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            Object value = jsonArray.get(i);
            if (value instanceof JSONObject) {
                JSONDocument subDocument = new JSONDocument();
                subDocument.parseJsonObject((JSONObject) value);
                jsonDocumentArray[i] = subDocument;
            } else if (value instanceof JSONArray) {
                jsonDocumentArray[i] = parseJsonArray((JSONArray) value);
            } else {
                jsonDocumentArray[i] = value;
            }
        }
        return jsonDocumentArray;

    }

    // overriding the toString() function to print the hash map of a JSONDocument class object
    @Override
    public String toString() {

        String[] keys = getKeys();
        StringBuilder outputString = new StringBuilder();
        for (String key : keys) {
            outputString.append(key).append(" : ").append(map.get(key)).append("\n");
        }
        return  outputString.toString();

    }
}