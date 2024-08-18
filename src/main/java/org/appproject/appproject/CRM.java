package org.appproject.appproject;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.Scanner;

/*
    USE THE FOLLOWING LINE IF YOU ARE GOING TO USE MongoDB FOR ANY PURPOSE:
    ((LoggerContext) LoggerFactory.getILoggerFactory()).getLogger("org.mongodb.driver").setLevel(Level.ERROR);
*/

class CRM {

    public static void main(String[] args) {

        boolean authenticationComplete = UserInputs.loginOrRegister();
        // use the above boolean variable for calling more the homePage() function
        // to be implemented in the future

    }

}