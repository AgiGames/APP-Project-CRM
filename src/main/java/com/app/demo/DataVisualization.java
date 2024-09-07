package com.app.demo;

import org.json.JSONArray;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class DataVisualization {

    @Async
    public static String displayStudents(String adminName, String adminRegistrationNumber) {

        AgiDB adminDB = new AgiDB(CRM.localDatabasePath, adminName.toUpperCase() + "-" + adminRegistrationNumber + "\\" + "students");
        System.out.println(adminDB.displayDirectory());
        return adminDB.displayDirectory();

    }

    @Async
    public static String[] displayStudentFolderFromAdmin(String adminName, String adminRegistrationNumber, String nameOfStudent, String registrationNumberOfStudent) {

        JSONArray fileInformation = AgiDB.loadJsonFilesFromDirectory(CRM.localDatabasePath + "\\" + adminName.toUpperCase() + "-" + adminRegistrationNumber + "\\students\\" + nameOfStudent.toUpperCase() + "-" + registrationNumberOfStudent);
        String[] fileNames = new String[fileInformation.length()];
        for(int i = 0; i < fileInformation.length(); i++) {
            JSONDocument ithJsonDocument = new JSONDocument(fileInformation.getJSONObject(i));
            fileNames[i] = (String) ithJsonDocument.getValue("fileName");
        }
        return fileNames;

    }

    @Async
    public static String displayStudentFolderFromPath(String directoryPath) {

        File directory = new File(directoryPath);
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

}