package org.appproject.appproject;

import java.util.Scanner;

public class DataVisualization {

    public static void displayStudents(String adminName) {

        AgiDB adminDB = new AgiDB(CRM.localDatabasePath, adminName + "\\" + "students");
        adminDB.displayDirectory();

    }

    public static void displayStudentFolder(String adminName) {

        String nameOfStudent, registrationNumberOfStudent;

        Scanner displayStudentScanner = new Scanner(System.in);
        System.out.print("Enter the name of the student: ");
        nameOfStudent = displayStudentScanner.nextLine();
        System.out.print("Enter the registration number of the student: ");
        registrationNumberOfStudent = displayStudentScanner.nextLine();

        AgiDB studentDB = new AgiDB(CRM.localDatabasePath, adminName + "\\students\\" + nameOfStudent.toUpperCase() + "-" + registrationNumberOfStudent);
        studentDB.displayDirectory();

    }

}