package org.appproject.appproject;

import java.io.File;
import java.util.Scanner;

public class DataVisualization {

    public static void displayStudents(String adminName) {

        AgiDB adminDB = new AgiDB(CRM.localDatabasePath, adminName + "\\" + "students");
        adminDB.displayDirectory();

    }

    public static void displayStudentFolderFromAdmin(String adminName) {

        String nameOfStudent, registrationNumberOfStudent;

        Scanner displayStudentScanner = new Scanner(System.in);
        System.out.print("Enter the name of the student: ");
        nameOfStudent = displayStudentScanner.nextLine();
        System.out.print("Enter the registration number of the student: ");
        registrationNumberOfStudent = displayStudentScanner.nextLine();

        AgiDB studentDB = new AgiDB(CRM.localDatabasePath, adminName + "\\students\\" + nameOfStudent.toUpperCase() + "-" + registrationNumberOfStudent);
        studentDB.displayDirectory();

    }

    public static void displayStudentFolderFromPath(String directoryPath) {

        File directory = new File(directoryPath);

        // if the directory exists and the given path in fact leads to a directory
        if (directory.exists() && directory.isDirectory()) {
            String[] contents = directory.list(); // get all contents of the directory

            if (contents != null) { // if the contents array is not empty
                for (String item : contents) { // print each item
                    System.out.println(item);
                }
            } else {
                System.out.println("Failed to retrieve directory contents.");
            }
        } else {
            System.out.println("Directory does not exist or is not a directory.");
        }

    }

}