package org.appproject.appproject;

import java.util.Scanner;

/*
    class to handle user inputs

    functions implemented so far (backend):
    -> login()

    functions to implement (backend & frontend):
    -> userHomePage()
    -> adminHomePage()
    -> viewStudentDirectory()
*/
public class UserInputs {

    /*
        function to show prompt for login with existing account or registering account
        returns status of login, based on if user is admin or user
    */
    static int login() {

        Scanner mainScanner = new Scanner(System.in);
        System.out.println("WELCOME");
        int choice = 0; // variable to store user choice
        int loginStatus = 0;
        while (choice != 2) {
            System.out.println("PRESS 1 TO LOGIN | 2 TO EXIT");
            choice = mainScanner.nextInt();
            mainScanner.nextLine();
            if (choice == 1) { // calling the login() function from UserAuthenticator class
                loginStatus = UserAuthenticator.login();
                if (loginStatus == 0)
                    System.out.println("Bad Credentials");
                else {
                    System.out.println("Login Successful!");
                    break;
                }

            }
        }
        return loginStatus;

    }

    static void userHomePage() {

    }

    static void adminHomePage() {

    }

}
