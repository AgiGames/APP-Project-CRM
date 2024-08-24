package com.app.demo;

public class UserInputs {
    /*
        function to register a student
        i.e. to make a separate folder for them in the database
     */
    public static boolean registerStudent(String nameOfStudent, String registrationNumberOfStudent, String password, String adminName, String adminRegistrationNumber) {

        // the name of the folder of the student will contain the name of the student and their registration number
        AgiDB studentDB = new AgiDB(CRM.localDatabasePath, adminName + "-" + adminRegistrationNumber + "\\students\\" + nameOfStudent.toUpperCase() + "-" + registrationNumberOfStudent);

        /*
            we need to make a JSON document for the student's authentication and store it in
            userAuthentication collection. A user authentication JSON file will have the following:
            username, registration_number, password, path to their personal directory, name of their admin,
            registration number of their admin and their role, which is "user"
        */
        JSONDocument studentDetails = new JSONDocument("username", nameOfStudent.toUpperCase());
        studentDetails.append("registration_number", registrationNumberOfStudent);
        studentDetails.append("password", password);
        String escapedPath = CRM.localDatabasePath + "\\" + adminName + "-" + adminRegistrationNumber + "\\students\\" + nameOfStudent.toUpperCase() + "-" + registrationNumberOfStudent;
        escapedPath = escapedPath.replace("\\", "\\\\");
        studentDetails.append("personal_directory", escapedPath);
        studentDetails.append("admin_name", adminName);
        studentDetails.append("admin_registration_number", adminRegistrationNumber);
        studentDetails.append("role", "user");

        AgiDB authenticationDB = new AgiDB(CRM.localDatabasePath, "userAuthentication");
        boolean studentAccountMade = authenticationDB.write(studentDetails); // write the JSON into the userAuthentication collection

        // create the folder
        boolean studentRegistered = studentDB.createDirectory();

        return studentAccountMade && studentRegistered;

    }

    /*
        function to remove a student
        i.e. to delete their separate folder in the database
        and to delete their authentication details
    */
    public static boolean removeStudent(String nameOfStudent, String registrationNumberOfStudent, String adminName, String adminRegistrationNumber) {

        // the name of the folder of the student will contain the name of the student and their registration number
        AgiDB studentDB = new AgiDB(CRM.localDatabasePath, adminName + "-" + adminRegistrationNumber + "\\students\\" + nameOfStudent.toUpperCase() + "-" + registrationNumberOfStudent);

        // delete the folder
        boolean studentRemoved = studentDB.deleteDirectory();

        // delete the JSON that exists for authentication of the deleted user
        AgiDB authenticationDB = new AgiDB(CRM.localDatabasePath, "userAuthentication");
        JSONDocument studentAuthenticationDetail = new JSONDocument("name", nameOfStudent.toUpperCase());
        studentAuthenticationDetail.append("registration_number", registrationNumberOfStudent);
        authenticationDB.delete(studentAuthenticationDetail);

        return studentRemoved;

    }


}