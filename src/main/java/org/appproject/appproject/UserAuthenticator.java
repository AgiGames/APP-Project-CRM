package org.appproject.appproject;

import java.util.Scanner;

public class UserAuthenticator {

    /*
        userAuthenticationCollection is an object from the AgiDB class
        giving it arguments for the database path and collection directory name
        only using this object can we send queries to the database
    */
    private static final AgiDB userAuthenticationCollection = new AgiDB("D:\\Education\\Java\\IntelliJProjects\\APP-Project\\database", "userAuthentication");

    private static String username = "";
    private static String password = "";

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

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        username = scanner.nextLine();
        System.out.print("Enter your password: ");
        password = scanner.nextLine();

        /*
            making a query document (JSON) to query to the database
            to check if document already exists in the database
            document contains username and password
        */
        JSONDocument queryDocument = new JSONDocument("username", username);
        queryDocument.append("password", password);

        /*
            read() function returns all JSON files that contain the input username and password
            if read() function returns an empty array, it means no JSON files contain the input username and password
        */
        JSONDocument[] relevantUsers = userAuthenticationCollection.read(queryDocument);

        if (relevantUsers.length == 0)
            return 0;
        if (relevantUsers[0].getValue("role").equals("user"))
            return 1;
        return 2;

    }

}
