package com.digitalid.model;

public class DigitalID {

    private final String idNumber;
    private final String fullName;
    private final String dateOfBirth;

    private String address;

    private Status status;

    private boolean passedDrivingTest;

    private boolean drivingRestricted;

    private boolean taxRestricted;

    private boolean fraudFlag;

    public DigitalID(String idNumber,
                     String fullName,
                     String dateOfBirth,
                     String address) {

        this.idNumber = idNumber;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;

        this.status = Status.ACTIVE;

        this.passedDrivingTest = false;
        this.drivingRestricted = false;
        this.taxRestricted = false;
        this.fraudFlag = false;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public Status getStatus() {
        return status;
    }

    public boolean hasPassedDrivingTest() {
        return passedDrivingTest;
    }

    public boolean isDrivingRestricted() {
        return drivingRestricted;
    }

    public boolean isTaxRestricted() {
        return taxRestricted;
    }

    public boolean hasFraudFlag() {
        return fraudFlag;
    }

    public void updateAddress(String address) {
        this.address = address;
    }

    public void suspend() {
        this.status = Status.SUSPENDED;
    }

    public void revoke() {
        this.status = Status.REVOKED;
    }

    public void activate() {
        this.status = Status.ACTIVE;
    }

    public boolean isActive() {
        return status == Status.ACTIVE;
    }

    public void markDrivingTestPassed() {
        this.passedDrivingTest = true;
    }

    public void setDrivingRestriction(boolean restricted) {
        this.drivingRestricted = restricted;
    }

    public void setTaxRestriction(boolean restricted) {
        this.taxRestricted = restricted;
    }

    public void setFraudFlag(boolean fraudFlag) {
        this.fraudFlag = fraudFlag;
    }
}