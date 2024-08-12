package org.appproject.appproject;

import com.mongodb.client.*;
import org.bson.Document;

import java.util.Scanner;

public class UserAuthenticator {

    private static final MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/"); // create mongo client based on connection string
    private static final MongoDatabase mongoDatabase = mongoClient.getDatabase("CRM"); // database object
    private static final MongoCollection<Document> userCollection = mongoDatabase.getCollection("users");
    private static String username = "";
    private static String password = "";

    public static void register(){

        String retypedPassword = "";
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        username = scanner.nextLine();
        System.out.print("Enter your password: ");
        password = scanner.nextLine();
        while(!retypedPassword.equals(password)) {
            System.out.print("Retype your password: ");
            retypedPassword = scanner.nextLine();
            if (!retypedPassword.equals(password))
                System.out.println("Invalid Password!");
        }

        Document newUserDocument = new Document("username", username)
                .append("password", password);

        userCollection.insertOne(newUserDocument);
        System.out.println("User registered!");

    }

    public static boolean login(){

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        username = scanner.nextLine();
        System.out.print("Enter your password: ");
        password = scanner.nextLine();
        Document returningUserDocument = new Document("username", username)
                .append("password", password);

        FindIterable<Document> userIterable = userCollection.find(returningUserDocument);

        return userIterable.iterator().hasNext();

    }

}
