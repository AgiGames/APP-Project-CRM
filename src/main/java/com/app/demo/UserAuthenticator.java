package com.app.demo;

import org.springframework.stereotype.Service;

@Service
public class UserAuthenticator {

    /*
        userAuthenticationCollection is an object from the AgiDB class
        giving it arguments for the database path and collection directory name
        only using this object can we send queries to the database
    */
    private static final AgiDB userAuthenticationCollection = new AgiDB(CRM.localDatabasePath, "userAuthentication");

    private String username = "";
    private String registrationNumber = "";
    private String adminName = "";
    private String adminRegistrationNumber = "";

/*
    implementation of the login() function
*/
    public int login(String username_s, String registrationNumber_s, String password_s){

        username = username_s;
        registrationNumber = registrationNumber_s;

        /*
            making a query document (JSON) to query to the database
            to check if document already exists in the database
            document contains username and password
        */
        JSONDocument queryDocument = new JSONDocument("username", username.toUpperCase());
        queryDocument.append("password", password_s);
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

    public String getUserName() {

        return username.toUpperCase();

    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getAdminName() {
        return adminName;
    }

    public String getAdminRegistrationNumber() {
        return adminRegistrationNumber;
    }

}
