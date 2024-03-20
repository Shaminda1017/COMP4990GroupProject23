package com.example.a1;

public class HelperClass {

    // Default constructor
    public HelperClass() {
        // Initialize any default values if needed
    }

    private String fullName;
    private String email;
    private String phone;
    private String userName;
    private String password;

    private String reportType;
    private String hospital;
    private String fileUrl;
    private String fileName;

    private String specialization;
    private String licenseNumber;

    private boolean selected; // Indicates whether the doctor is selected
    private String doctorId;
    private String patientName;


    // Constructors
    public HelperClass(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    public HelperClass(String fullName, String email, String phone, String userName, String password) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.userName = userName;
        this.password = password;
    }

    public HelperClass(String fullName, String reportType, String hospital, String fileUrl) {
        this.fullName = fullName;
        this.reportType = reportType;
        this.hospital = hospital;
        this.fileUrl = fileUrl;
    }
    public HelperClass(String patientName, String fileName, String fileUrl) {
        this.patientName = patientName;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

    public String getPatientName() {
        return patientName;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    // Setter for doctorId
    public void setId(String doctorId) {
        this.doctorId = doctorId;
    }

    // Getter for doctorId
    public String getId() {
        return doctorId;
    }

    // Method to toggle the selection state of the doctor
    public void toggleSelection() {
        selected = !selected;
    }

    // Setter for file name
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    // Getter for file name
    public String getFileName() {
        return fileName;
    }

}
