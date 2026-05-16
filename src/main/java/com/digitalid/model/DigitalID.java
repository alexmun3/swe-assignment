package com.digitalid.model;

public class DigitalID {

    private final String idNumber;
    private final String fullName;
    private final String dateOfBirth;

    private String address;

    private Status status;

    private boolean fraudFlag;
    private boolean drivingTestPassed;
    private boolean drivingRestricted;
    private boolean taxRestricted;

    public DigitalID(
            String idNumber,
            String fullName,
            String dateOfBirth,
            String address
    ) {

        this.idNumber = idNumber;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;

        this.status = Status.ACTIVE;

        this.fraudFlag = false;
        this.drivingTestPassed = false;
        this.drivingRestricted = false;
        this.taxRestricted = false;
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

    public boolean hasFraudFlag() {
        return fraudFlag;
    }

    public void setFraudFlag(boolean fraudFlag) {
        this.fraudFlag = fraudFlag;
    }

    public boolean hasPassedDrivingTest() {
        return drivingTestPassed;
    }

    public void markDrivingTestPassed() {
        this.drivingTestPassed = true;
    }

    public boolean isDrivingRestricted() {
        return drivingRestricted;
    }

    public void setDrivingRestriction(boolean drivingRestricted) {
        this.drivingRestricted = drivingRestricted;
    }

    public boolean isTaxRestricted() {
        return taxRestricted;
    }

    public void setTaxRestriction(boolean taxRestricted) {
        this.taxRestricted = taxRestricted;
    }
}