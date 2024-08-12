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

class CRM {

    public static void main(String[] args) {

        ((LoggerContext) LoggerFactory.getILoggerFactory()).getLogger("org.mongodb.driver").setLevel(Level.ERROR);
        Scanner mainScanner = new Scanner(System.in);
        System.out.println("WELCOME");
        int choice = 0;
        while(choice != 3) {
            System.out.println("PRESS 1 TO LOGIN | 2 TO REGISTER | 3 TO EXIT");
            choice = mainScanner.nextInt();
            mainScanner.nextLine();
            switch(choice){
                case 1:
                    boolean successfulLogin = UserAuthenticator.login();
                    if(!successfulLogin)
                        System.out.println("Bad Credentials");
                    else
                        System.out.println("Login Successful!");
                    break;
                case 2:
                    UserAuthenticator.register();
                    break;
                default:
                    break;
            }
        }

    }

};