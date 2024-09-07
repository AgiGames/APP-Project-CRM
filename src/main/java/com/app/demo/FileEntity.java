package com.app.demo;

import java.util.Base64;

public class FileEntity {
    private final String fileName;
    private final String base64Content; // This will hold the base64-encoded content
    private final long fileSize;

    // Constructor
    public FileEntity(String fileName, byte[] fileContent) {
        this.fileName = fileName;
        this.fileSize = fileContent.length;
        this.base64Content = Base64.getEncoder().encodeToString(fileContent); // Encode file content to base64
    }

    // Getters
    public String getFileName() { return fileName; }
    public String getBase64Content() { return base64Content; }
    public long getFileSize() { return fileSize; }

    // Method to convert to JSONDocument for AgiDB
    public JSONDocument toJSONDocument() {
        JSONDocument document = new JSONDocument();
        document.append("fileName", fileName);
        document.append("base64Content", base64Content);
        document.append("fileSize", fileSize);
        return document;
    }

    // Static method to reconstruct FileEntity from JSONDocument
    public static FileEntity fromJSONDocument(JSONDocument document) {
        String fileName = (String) document.getValue("fileName");
        String base64Content = (String) document.getValue("base64Content");

        // Decode base64 content back to byte array
        byte[] fileContent = Base64.getDecoder().decode(base64Content);

        return new FileEntity(fileName, fileContent);
    }
}
