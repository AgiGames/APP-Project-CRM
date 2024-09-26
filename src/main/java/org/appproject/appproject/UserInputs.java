package org.appproject.appproject;

// use these imports when implementing logging functions
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.util.Scanner;

/*
    class to handle user inputs

    functions implemented so far (backend):
    -> login()
    -> adminHomePage()
    -> userHomePage()

*/
public class UserInputs {

    // use this when implementing logging functions
    //private static final Logger log = LoggerFactory.getLogger(UserInputs.class);

    /*
        if an object instantiated from this class is being used
        currentUserName will have a value if the user is of role: user
        currentAdminName will have a value if the user is of role: admin
     */
    private static String currentAdminName;
    private static String currentRegistrationNumber;
    private static String currentUserName;

    /*
        function to show prompt for login with existing account or registering account
        returns status of login, based on if user is admin or user
    */
    public int login() {

        Scanner loginScanner = new Scanner(System.in);
        System.out.println("WELCOME");
        int choice = 0; // variable to store user choice
        int loginStatus = 0;
        while (choice != 2) {
            System.out.println("PRESS 1 TO LOGIN | 2 TO EXIT");
            choice = loginScanner.nextInt();
            loginScanner.nextLine();
            if (choice == 1) { // calling the login() function from UserAuthenticator class
                loginStatus = UserAuthenticator.login();
                if (loginStatus == 0)
                    System.out.println("Bad Credentials");
                else {
                    if(loginStatus == 1)
                        currentUserName = UserAuthenticator.getUserName();
                    else
                        currentAdminName = UserAuthenticator.getUserName();
                    currentRegistrationNumber = UserAuthenticator.getRegistrationNumber();
                    System.out.println("Login Successful!");
                    break;
                }

            }
        }
        return loginStatus;

    }


    /*
        the home page a user sees whenever they log in
    */
    public void userHomePage() {

        System.out.println("Welcome student, " + currentUserName);
        int choice = 0;
        while(choice != 4) {

            System.out.print("\033[H\033[2J"); // clears the terminal
            choice = promptUser();
            switch (choice) {
                case 1:
                    System.out.println(CRM.localDatabasePath + "\\" + UserAuthenticator.getAdminName() + "-" + UserAuthenticator.getAdminRegistrationNumber() + "\\students\\" + currentUserName.toUpperCase() + "-" + currentRegistrationNumber + "\\");
                    DataVisualization.displayStudentFolderFromPath(CRM.localDatabasePath + "\\" + UserAuthenticator.getAdminName() + "-" + UserAuthenticator.getAdminRegistrationNumber() + "\\students\\" + currentUserName.toUpperCase() + "-" + currentRegistrationNumber + "\\");
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }

        }

    }

    /*
        the home page an admin sees whenever they log in
    */
    public void adminHomePage() {

        System.out.println("Welcome admin, " + currentAdminName + "-" + currentRegistrationNumber);
        int choice = 0;
        while(choice != 7) {
            System.out.print("\033[H\033[2J"); // clears the terminal
            choice = promptAdmin();
            switch (choice) {
                case 1:
                    DataVisualization.displayStudents(currentAdminName + "-" + currentRegistrationNumber);
                    break;
                case 2:
                    registerStudent();
                    break;
                case 3:
                    removeStudent();
                    break;
                case 5:
                    DataVisualization.displayStudentFolderFromAdmin(currentAdminName + "-" + currentRegistrationNumber);
                case 7:
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }

    }

    // to prompt an admin for input as to what they want to do
    public static int promptAdmin() {

        int choice;
        System.out.println("1. View Students");
        System.out.println("2. Register Student");
        System.out.println("3. Remove Student");
        System.out.println("4. Notify Students");
        System.out.println("5. View Student Directory");
        System.out.println("6. Pending Requests");
        System.out.println("7. Exit");

        Scanner choiceScanner = new Scanner(System.in);
        choice = choiceScanner.nextInt();

        return choice;

    }

    // to prompt a user for input as to what they want to do
    public static int promptUser() {

        int choice = 0;
        System.out.println("1. View Your Directory");
        System.out.println("2. Upload to Your Directory");
        System.out.println("3. Look at Notifications");
        System.out.println("4. Exit");

        Scanner choiceScanner = new Scanner(System.in);
        choice = choiceScanner.nextInt();

        return choice;

    }

    /*
        function to register a student
        i.e. to make a separate folder for them in the database
     */
    private static void registerStudent() {

        // get name and registration number of the student
        String nameOfStudent, registrationNumberOfStudent, password;

        Scanner studentScanner = new Scanner(System.in);
        System.out.print("Enter student's name: ");
        nameOfStudent = studentScanner.nextLine();
        System.out.println("Enter the student's registration number: ");
        registrationNumberOfStudent = studentScanner.nextLine();
        System.out.println("Enter the password for the student: ");
        password = studentScanner.nextLine();

        // the name of the folder of the student will contain the name of the student and their registration number
        AgiDB studentDB = new AgiDB(CRM.localDatabasePath, currentAdminName + "-" + currentRegistrationNumber + "\\students\\" + nameOfStudent.toUpperCase() + "-" + registrationNumberOfStudent);

        /*
            we need to make a JSON document for the student's authentication and store it in
            userAuthentication collection. A user authentication JSON file will have the following:
            username, registration_number, password, path to their personal directory, name of their admin,
            registration number of their admin and their role, which is "user"
        */
        JSONDocument studentDetails = new JSONDocument("username", nameOfStudent.toUpperCase());
        studentDetails.append("registration_number", registrationNumberOfStudent);
        studentDetails.append("password", password);
        String escapedPath = CRM.localDatabasePath + "\\" + currentAdminName + "-" + currentRegistrationNumber + "\\students\\" + nameOfStudent.toUpperCase() + "-" + registrationNumberOfStudent;
        escapedPath = escapedPath.replace("\\", "\\\\");
        studentDetails.append("personal_directory", escapedPath);
        studentDetails.append("admin_name", currentAdminName);
        studentDetails.append("admin_registration_number", currentRegistrationNumber);
        studentDetails.append("role", "user");

        AgiDB authenticationDB = new AgiDB(CRM.localDatabasePath, "userAuthentication");
        authenticationDB.write(studentDetails); // write the JSON into the userAuthentication collection

        // create the folder
        boolean studentRegistered = studentDB.createDirectory();
        if(studentRegistered)
            System.out.println("Student registered!");
        else
            System.out.println("Student already exists or error registering student!");

    }

    /*
        function to remove a student
        i.e. to delete their separate folder in the database
    */
    private static void removeStudent() {

        // get name and registration number of the student
        String nameOfStudent, registrationNumberOfStudent;

        Scanner studentScanner = new Scanner(System.in);
        System.out.print("Enter student's name: ");
        nameOfStudent = studentScanner.nextLine();
        System.out.println("Enter the student's registration number: ");
        registrationNumberOfStudent = studentScanner.nextLine();

        // the name of the folder of the student will contain the name of the student and their registration number
        AgiDB studentDB = new AgiDB(CRM.localDatabasePath, currentAdminName + "-" + currentRegistrationNumber + "\\students\\" + nameOfStudent.toUpperCase() + "-" + registrationNumberOfStudent);

        // delete the folder
        boolean studentRemoved = studentDB.deleteDirectory();
        if(studentRemoved)
            System.out.println("Student removed!");
        else
            System.out.println("Error while removing student!");

        // delete the JSON that exists for authentication of the deleted user
        AgiDB authenticationDB = new AgiDB(CRM.localDatabasePath, "userAuthentication");
        JSONDocument studentAuthenticationDetail = new JSONDocument("name", nameOfStudent.toUpperCase());
        studentAuthenticationDetail.append("registration_number", registrationNumberOfStudent);
        authenticationDB.delete(studentAuthenticationDetail);


    }


}
