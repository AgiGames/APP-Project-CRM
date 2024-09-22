package com.app.demo;

/*
    USE THE FOLLOWING LINE IF YOU ARE GOING TO USE MongoDB FOR ANY PURPOSE:
    ((LoggerContext) LoggerFactory.getILoggerFactory()).getLogger("org.mongodb.driver").setLevel(Level.ERROR);
*/

class CRM {

    // path to the local database
    public static String localDatabasePath = System.getProperty("user.dir") + "/" + "database";

}