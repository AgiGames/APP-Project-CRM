package org.appproject.appproject;

// use this when implementing logging functions
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class UserAuthenticator {

    /*
        userAuthenticationCollection is an object from the AgiDB class
        giving it arguments for the database path and collection directory name
        only using this object can we send queries to the database
    */
    private static final AgiDB userAuthenticationCollection = new AgiDB(CRM.localDatabasePath, "userAuthentication");

    // use this when implementing logging functions
    // private static final Logger log = LoggerFactory.getLogger(UserAuthenticator.class);

    private static String username = "";
    private static String password = "";
    private static String registrationNumber = "";
    private static String adminName = "";
    private static String adminRegistrationNumber = "";

    /*
        implementation of the register() function
        called in the UserInputs loginOrRegister() function
    */
    public static void register(){

        // retypedPassword variable is only used when the user is registering
        String retypedPassword = "";

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        username = scanner.nextLine();
        System.out.print("Enter your password: ");
        password = scanner.nextLine();

        /*
            keeps prompting the user to get input for retypedPassword until
            retypedPassword and password are equal
        */
        while(!retypedPassword.equals(password)) {
            System.out.print("Retype your password: ");
            retypedPassword = scanner.nextLine();
            if (!retypedPassword.equals(password))
                System.out.println("Invalid Password!");
        }

        // create a new JSON file to write our data
        JSONDocument newUserDocument = new JSONDocument("username", username);
        newUserDocument.append("password", password);

        // write the data using our database client object
        userAuthenticationCollection.write(newUserDocument);

        // print out to user that the account is successfully registered
        System.out.println("User registered!");

    }

/*
    implementation of the login() function
    called in the UserInputs loginOrRegister() function
*/
    public static int login(){

        Scanner loginScanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        username = loginScanner.nextLine();
        System.out.println("Enter your registration number: ");
        registrationNumber = loginScanner.nextLine();
        System.out.print("Enter your password: ");
        password = loginScanner.nextLine();


        /*
            making a query document (JSON) to query to the database
            to check if document already exists in the database
            document contains username and password
        */
        JSONDocument queryDocument = new JSONDocument("username", username.toUpperCase());
        queryDocument.append("password", password);
        queryDocument.append("registration_number", registrationNumber);

        /*
            read() function returns all JSON files that contain the input username and password
            if read() function returns an empty array, it means no JSON files contain the input username and password
        */
        JSONDocument[] relevantUsers = userAuthenticationCollection.read(queryDocument);

        if (relevantUsers.length == 0)
            return 0;
        if (relevantUsers[0].getValue("role").equals("user")) { // if the role of whoever logins is user
            // we store their admin's name and admin's registration number for future use
            adminName = (String) relevantUsers[0].getValue("admin_name");
            adminRegistrationNumber = (String) relevantUsers[0].getValue("admin_registration_number");
            return 1;
        }
        return 2;

    }

    public static String getUserName() {

        return username.toUpperCase();

    }

    public static String getRegistrationNumber() {
        return registrationNumber;
    }

    public static String getAdminName() {
        return adminName;
    }

    public static String getAdminRegistrationNumber() {
        return adminRegistrationNumber;
    }

}
