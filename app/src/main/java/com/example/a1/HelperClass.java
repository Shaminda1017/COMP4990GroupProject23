package com.example.a1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HelperClass {

    private String fullName;
    private String email;
    private String phone;
    private String userName;
    private String password;

    // Medical report fields
    private String reportType;
    private String hospital;
    private String fileUrl;

    // Doctor fields
    private String specialization;
    private String licenseNumber;

    // Message fields
    private String messageId;
    private String senderId;
    private String receiverId;
    private String messageContent;
    private long timestamp;
    private String encryptedMessageContent;
    private String id;
    private String senderName;
    private String receiverName;

    // Default constructor
    public HelperClass() {
        // Initialize any default values if needed
    }

    // Constructors for patient registration, medical report, and message
    public HelperClass(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }
    public HelperClass(String senderName, String receiverName, String messageContent) {
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.messageContent = messageContent;
        this.timestamp = System.currentTimeMillis(); // Set the current timestamp
    }

    public HelperClass( String fileUrl, String senderName, String receiverName, String messageContent) {
        this.fileUrl = fileUrl;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.messageContent = messageContent;
        this.timestamp = System.currentTimeMillis(); // Set the current timestamp
    }
    public HelperClass(String fullName, String email,  String userName, String phone, String password) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.userName = userName;
        this.password = password;
    }
    public HelperClass(String fullName, String email, String phone, String hospital, String userName, String specialization, String password) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.userName = userName;
        this.password = password;
        this.hospital = hospital;
        this.specialization = specialization;
    }

    // Method to get formatted timestamp
    public String getFormattedTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }


    // Getters and setters for senderName and receiverName
    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }


    // Setter method for doctor's ID
    public void setId(String id) {
        this.id = id;
    }

    // Getter method for doctor's ID
    public String getId() {
        return id;
    }


    // Getters and setters for all fields
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    // Methods for encryption and decryption
    public void encryptMessageContent() {
        // Implement encryption algorithm (e.g., AES)
        // Store the encrypted message in encryptedMessageContent
    }

    public String decryptMessageContent() {
        // Implement decryption algorithm (e.g., AES)
        // Decrypt the encrypted message content
        return ""; // Return decrypted message
    }

    // Getter and setter for encryptedMessageContent
    public String getEncryptedMessageContent() {
        return encryptedMessageContent;
    }

    public void setEncryptedMessageContent(String encryptedMessageContent) {
        this.encryptedMessageContent = encryptedMessageContent;
    }

    public boolean isSelected() {
        return false;
    }

    public void setFileName(String fileName) {
    }
}
