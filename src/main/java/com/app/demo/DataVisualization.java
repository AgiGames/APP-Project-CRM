package com.app.demo;

import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class DataVisualization {

    public static String displayStudents(String adminName) {

        AgiDB adminDB = new AgiDB(CRM.localDatabasePath, adminName + "\\" + "students");
        return adminDB.displayDirectory();

    }

    public static String displayStudentFolderFromAdmin(String adminName, String nameOfStudent, String registrationNumberOfStudent) {

        AgiDB studentDB = new AgiDB(CRM.localDatabasePath, adminName + "\\students\\" + nameOfStudent.toUpperCase() + "-" + registrationNumberOfStudent);
        return studentDB.displayDirectory();

    }

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