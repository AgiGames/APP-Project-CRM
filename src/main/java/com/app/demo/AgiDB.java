package com.app.demo;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.*;


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

        // following block of code is to make the collection in the given path, if it doesn't exist
        File collectionDirectory = new File(databasePath + "\\" + collectionName + "\\");
        // Check if the directory doesn't exist
        if (!collectionDirectory.exists()) {
            // Create the directory
            if (collectionDirectory.mkdirs()) {
                System.out.println("Directory '" + collectionDirectory + "' created successfully!");
                // make the options.txt file and write 0 as the currentDocumentID
                try (FileWriter writer = new FileWriter(databasePath + "\\" + collectionName + "\\" + "options.txt")) {
                    writer.write("0\n");
                } catch (IOException e) {
                    System.out.println("An error occurred making options.txt");
                    //e.printStackTrace();
                }
            } else {
                System.out.println("Failed to create directory '" + collectionDirectory + "'.");
            }
        } else {
            System.out.println("Directory '" + collectionDirectory + "' already exists.");
        }

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

        returns true if successfully written
        returns false if not successfully written
    */
    public boolean write(JSONDocument writeDocument) {

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
                System.out.println("Unable to update options.txt");
                return false;
            }
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Unable to write data!");
            return false;
        }

        return true;

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

    /*
        function to create an empty collection (directory)
        with the database path and collection name
    */
    public boolean createDirectory() {

        File collectionDirectory = new File(databasePath + "\\" + collectionName + "\\");
        if (!collectionDirectory.exists()) {
            return collectionDirectory.mkdirs();
        }
        return false;

    }

    /*
        function to delete a collection (directory)
        with the database path and collection name
    */
    public boolean deleteDirectory() {

        Path directory = Paths.get(databasePath + "\\" + collectionName);

        /*
            basically visits each file and deletes them before deleting them
            the author of this code (Agilesh) doesn't know how exactly it works as he took it from ChatGPT :D
        */
        try {
            Files.walkFileTree(directory, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file); // Delete each file
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir); // Delete directory after contents are deleted
                    return FileVisitResult.CONTINUE;
                }
            });

            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    /*
        function to display the contents of the collection
    */
    public String displayDirectory() {

        File directory = new File(databasePath + "\\" + collectionName);
        StringBuilder fileContents = new StringBuilder();

        // if the directory exists and the given path in fact leads to a directory
        if (directory.exists() && directory.isDirectory()) {
            String[] contents = directory.list(); // get all contents of the directory

            if (contents != null) { // if the contents array is not empty
                for (String item : contents) { // print each item
                    fileContents.append(item);
                    fileContents.append("\n");
                }
            } else {
                System.out.println("Failed to retrieve directory contents.");
            }
        } else {
            System.out.println("Directory does not exist or is not a directory.");
        }

        return fileContents.toString();

    }

    public static String[] getPathsOfAllJSON(String directoryPath) {

        // create a File object for the directory
        File directory = new File(directoryPath);

        // create a FilenameFilter to filter JSON files
        FilenameFilter jsonFilter = (dir, name) -> name.toLowerCase().endsWith(".json");

        // get the list of JSON files in the directory
        String[] jsonFilePaths = directory.list(jsonFilter);

        // if the directory is not empty, prepend the directory path to each file name
        if (jsonFilePaths != null) {
            for (int i = 0; i < jsonFilePaths.length; i++) {
                jsonFilePaths[i] = directoryPath + File.separator + jsonFilePaths[i];
            }
        }

        try {
            return jsonFilePaths;
        }catch (Exception e) {
            System.out.println("No JSON files found in the directory.");
        }
        return new String[0];

    }

    public void delete(JSONDocument queryDocument) {

        // all JSON files in the collection are stored in this object
        JSONArray jsonFilesFromDirectory = loadJsonFilesFromDirectory(databasePath + "\\" + collectionName + "\\");

        String[] filePaths = getPathsOfAllJSON(databasePath + "\\" + collectionName + "\\");

        String filePath;

        // iterate through all JSON files in the directory
        for(int i = 0; i < jsonFilesFromDirectory.length(); i++) {

            // store the ith object
            JSONObject ithJsonObject = jsonFilesFromDirectory.getJSONObject(i);

            // convert the ith JSON file to a JSON Document to be able to use with AgiDB
            JSONDocument ithJsonDocument = new JSONDocument(ithJsonObject);

            filePath = filePaths[i];

            // array which stores all the keys in the JSON document object
            String[] queryKeyArray = queryDocument.getKeys();

            // variable which stores the boolean result of if the ith JSON document and input JSON document are equal
            boolean isEqual = false;

            /*
                body of the comparing algorithm
                reading the comments may not help in understanding how it works
                try and work through it yourself
            */

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
            if(isEqual) {
                File fileToDelete = new File(filePath);
                if (fileToDelete.delete()) {
                    System.out.println("File deleted successfully: " + filePath);
                } else {
                    System.out.println("Failed to delete the file: " + filePath);
                }
            }

        }

    }

}
