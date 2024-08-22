package org.appproject.appproject;

/*
    USE THE FOLLOWING LINE IF YOU ARE GOING TO USE MongoDB FOR ANY PURPOSE:
    ((LoggerContext) LoggerFactory.getILoggerFactory()).getLogger("org.mongodb.driver").setLevel(Level.ERROR);
*/

class CRM {

    // path to the local database
    public static String localDatabasePath = "D:\\Education\\Java\\IntelliJProjects\\APP-Project\\database";

    public static void main(String[] args) {

        UserInputs userInput = new UserInputs();

        int authenticationStatus = userInput.login();
        switch (authenticationStatus) {
            case 1:
                userInput.userHomePage();
                break;
            case 2:
                userInput.adminHomePage();
                break;
            default:
                break;
        }

    }

}