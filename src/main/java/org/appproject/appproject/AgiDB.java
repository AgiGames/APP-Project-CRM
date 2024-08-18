package org.appproject.appproject;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


// class that represents a collection of data in the database
public class AgiDB {

    /*
        currentDocumentId -> ID of the JSON file that is going to be written next
        databasePath -> contains the path of the database
        collectionName -> contains the name of the collection
    */
    private int currentDocumentId = 0;
    private final String databasePath;
    private final String collectionName;

    AgiDB(String DBPath, String CName) {

        databasePath = DBPath;
        collectionName = CName;

        /*
            the following block of code is to retrieve the currentDocumentId from options.txt file
            this is stored in the text file to prevent overwriting of data already existing
            with ID that is currently being used
        */
        try (Scanner fileScanner = new Scanner(new File(databasePath + "\\" + collectionName + "\\" + "options.txt"))) {
            while(fileScanner.hasNextLine()) {
                currentDocumentId = Integer.parseInt(fileScanner.nextLine());
            }
        } catch (IOException e) {
            System.out.println("options.txt does not exist in " + databasePath + "\\" + collectionName + "\\");
        }

    }

    /*
        following function writes a JSON file
        parameter is a JSONDocument object
        which represents a JSON file in memory
    */
    public void write(JSONDocument writeDocument) {

        // contains the textual representation of the JSON
        String json = writeDocument.toJson();

        try (FileWriter file = new FileWriter(databasePath + "\\" + collectionName + "\\" + currentDocumentId + ".json")) {
            file.write(json); // writing the json file
            /*
                printing the document ID of the json file
                and incrementing the currentDocumentID variable by 1
            */
            System.out.println("Successfully written JSON file of ID: " + currentDocumentId);
            currentDocumentId++;

            /*
                following block of code is to update the options.txt file
                by incrementing the number (currentDocumentId) by one inside the first line of the file
            */
            try {
                // stores the path of the options.txt file
                Path path = Paths.get(databasePath + "\\" + collectionName + "\\" + "options.txt");
                List<String> lines = Files.readAllLines(path);

                /*
                    we write to the file, the value of whatever currentDocumentId is
                    even if no lines exist already
                */
                if (!lines.isEmpty()) {
                    lines.set(0, String.valueOf(currentDocumentId));
                } else {
                    lines.add(String.valueOf(currentDocumentId));
                }

                Files.write(path, lines);

            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("Unable to options.txt");
            }
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Unable to write data!");
        }

    }


    /*
        function to compare objects
        -> objects can be of ANY datatype
        -> both primitive, wrapper-class and user defined
    */
    private static boolean compareObjects(Object obj1, Object obj2) {

        // check if both references are null
        if (obj1 == obj2) {
            return true;
        }

        // check if one of the objects is null
        if (obj1 == null || obj2 == null) {
            return false;
        }

        // use the equals() method to compare values
        return obj1.equals(obj2);

    }

    /*
        following function gets JSON files
        similar to the input JSONDocument object
    */
    public JSONDocument[] read(JSONDocument queryDocument) {

        // all JSON files in the collection are stored in this object
        JSONArray jsonFilesFromDirectory = loadJsonFilesFromDirectory(databasePath + "\\" + collectionName + "\\");

        // JSON files which match the query will be stored in this object
        ArrayList<JSONDocument> queryResult = new ArrayList<>(0);

        /*
            body of the comparing algorithm
            reading the comments may not help in understanding how it works
            try and work through it yourself
        */

        // iterate through all JSON files in the directory
        for(int i = 0; i < jsonFilesFromDirectory.length(); i++) {

            // store the ith object
            JSONObject ithJsonObject = jsonFilesFromDirectory.getJSONObject(i);

            // convert the ith JSON file to a JSON Document to be able to use with AgiDB
            JSONDocument ithJsonDocument = new JSONDocument(ithJsonObject);

            // array which stores all the keys in the JSON document object
            String[] queryKeyArray = queryDocument.getKeys();

            // variable which stores the boolean result of if the ith JSON document and input JSON document are equal
            boolean isEqual = false;

            // iterate through each key of the key array
            for (String queryKey : queryKeyArray) {
                // will execute if the ithJsonDocument has the nth key from the key array
                if (ithJsonDocument.keyExists(queryKey)) {
                    /*
                        comparing the value of the key of query document
                        with the value of the key of ith JSON document
                        store the boolean result
                    */
                    isEqual = (compareObjects(queryDocument.getValue(queryKey), ithJsonDocument.getValue(queryKey)));

                    // if they are not equal, then break out the loop
                    if (!isEqual)
                        break;
                }
            }

            // if they are equal, append the ith JSON document to the query result
            if(isEqual)
                queryResult.add(ithJsonDocument);

        }

        // queryResult is an ArrayList object, returning the value after converting to an array
        return queryResult.toArray(new JSONDocument[0]);

    }

    /*
        function to load all JSON files from a given directory
        used in the read() function of AgiDB class
    */
    public static JSONArray loadJsonFilesFromDirectory(String directoryPath) {

        JSONArray jsonArray = new JSONArray(); // will contain all JSON files in the directory

        // step 1: list all .json files in the directory
        File folder = new File(directoryPath);
        File[] listOfFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));

        // executes of the directory is empty
        if (listOfFiles == null) {
            System.out.println("The folder is empty or doesn't exist.");
            return jsonArray; // return empty array
        }

        // step 2: read each JSON file and add to JSONArray
        for (File file : listOfFiles) {
            if (file.isFile()) {
                // convert each JSON file to a JSON object
                try (FileInputStream fis = new FileInputStream(file)) {
                    JSONObject jsonObject = new JSONObject(new JSONTokener(fis));
                    jsonArray.put(jsonObject);
                } catch (IOException e) {
                    //e.printStackTrace();
                    System.out.println("Unable to read data!");
                }
            }
        }

        return jsonArray;

    }

}
