package org.appproject.appproject;

import java.util.Scanner;

/*
    class to handle user inputs

    functions implemented so far (backend):
    -> loginOrRegister()

    functions to implement (backend):
    -> homePage()
    -> viewStudentDirectory()
*/
public class UserInputs {

    /*
        function to show prompt for login with existing account or registering account
        returns true only if login is successful
        therefore, returns false if register function is called
    */
    static boolean loginOrRegister() {

        Scanner mainScanner = new Scanner(System.in);
        System.out.println("WELCOME");
        int choice = 0; // variable to store user choice
        while (choice != 3) {
            System.out.println("PRESS 1 TO LOGIN | 2 TO REGISTER | 3 TO EXIT");
            choice = mainScanner.nextInt();
            mainScanner.nextLine();
            switch (choice) {
                case 1:
                    // calling the login() function from UserAuthenticator class
                    boolean successfulLogin = UserAuthenticator.login();
                    if (!successfulLogin)
                        System.out.println("Bad Credentials");
                    else
                        System.out.println("Login Successful!");
                    return successfulLogin;
                case 2:
                    // calling the register() function from UserAuthenticator class
                    UserAuthenticator.register();
                    return false;
            }
        }
        return false;

    }

}
