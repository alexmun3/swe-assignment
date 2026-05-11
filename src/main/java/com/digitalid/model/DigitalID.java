package com.digitalid.model;

public class DigitalID {

    private final String idNumber;
    private final String fullName;
    private final String dateOfBirth;

    private String address;

    private Status status;

    public DigitalID(String idNumber,
                     String fullName,
                     String dateOfBirth,
                     String address) {

        this.idNumber = idNumber;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.status = Status.ACTIVE;
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
}