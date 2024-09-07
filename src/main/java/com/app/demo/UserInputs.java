package com.app.demo;

import org.springframework.scheduling.annotation.Async;

public class UserInputs {
    /*
        function to register a student
        i.e. to make a separate folder for them in the database
     */
    @Async
    public static boolean registerStudent(String nameOfStudent, String registrationNumberOfStudent, String password, String adminName, String adminRegistrationNumber) {

        // the name of the folder of the student will contain the name of the student and their registration number
        AgiDB studentDB = new AgiDB(CRM.localDatabasePath, adminName.toUpperCase() + "-" + adminRegistrationNumber + "\\students\\" + nameOfStudent.toUpperCase() + "-" + registrationNumberOfStudent);

        /*
            we need to make a JSON document for the student's authentication and store it in
            userAuthentication collection. A user authentication JSON file will have the following:
            username, registration_number, password, path to their personal directory, name of their admin,
            registration number of their admin and their role, which is "user"
        */
        JSONDocument studentDetails = new JSONDocument("username", nameOfStudent.toUpperCase());
        studentDetails.append("registration_number", registrationNumberOfStudent);
        studentDetails.append("password", password);
        String escapedPath = CRM.localDatabasePath + "\\" + adminName.toUpperCase() + "-" + adminRegistrationNumber + "\\students\\" + nameOfStudent.toUpperCase() + "-" + registrationNumberOfStudent;
        escapedPath = escapedPath.replace("\\", "\\\\");
        studentDetails.append("personal_directory", escapedPath);
        studentDetails.append("admin_name", adminName.toUpperCase());
        studentDetails.append("admin_registration_number", adminRegistrationNumber);
        studentDetails.append("role", "user");

        AgiDB authenticationDB = new AgiDB(CRM.localDatabasePath, "userAuthentication");
        boolean studentAccountMade = authenticationDB.write(studentDetails); // write the JSON into the userAuthentication collection

        // create the folder
        studentDB.createDirectory();

        return studentAccountMade;

    }

    /*
        function to remove a student
        i.e. to delete their separate folder in the database
        and to delete their authentication details
    */
    @Async
    public static boolean removeStudent(String nameOfStudent, String registrationNumberOfStudent, String adminName, String adminRegistrationNumber) {

        // the name of the folder of the student will contain the name of the student and their registration number
        AgiDB studentDB = new AgiDB(CRM.localDatabasePath, adminName.toUpperCase() + "-" + adminRegistrationNumber + "\\students\\" + nameOfStudent.toUpperCase() + "-" + registrationNumberOfStudent);

        // delete the folder
        boolean studentRemoved = studentDB.deleteDirectory();

        // delete the JSON that exists for authentication of the deleted user
        AgiDB authenticationDB = new AgiDB(CRM.localDatabasePath, "userAuthentication");
        JSONDocument studentAuthenticationDetail = new JSONDocument("name", nameOfStudent.toUpperCase());
        studentAuthenticationDetail.append("registration_number", registrationNumberOfStudent);
        authenticationDB.delete(studentAuthenticationDetail);

        return studentRemoved;

    }

    @Async
    public static boolean uploadToDirectory(String nameOfStudent, String registrationNumberOfStudent, String adminName, String adminRegistrationNumber,
                                            String fileName, byte[] fileContent) {

        AgiDB studentDB = new AgiDB(CRM.localDatabasePath, adminName.toUpperCase() + "-" + adminRegistrationNumber + "\\students\\" + nameOfStudent.toUpperCase() + "-" + registrationNumberOfStudent);
        FileEntity fileEntity = new FileEntity(fileName, fileContent);
        return studentDB.storeFile(fileEntity);

    }

    @Async
    public static String getFileFromDirectory(String nameOfStudent, String registrationNumberOfStudent, String adminName, String adminRegistrationNumber,
                                              String fileName) {

        AgiDB studentDB = new AgiDB(CRM.localDatabasePath, adminName.toUpperCase() + "-" + adminRegistrationNumber + "\\students\\" + nameOfStudent.toUpperCase() + "-" + registrationNumberOfStudent);
        FileEntity fileEntity = studentDB.getFileContent(fileName);
        return fileEntity.getBase64Content();

    }

}
