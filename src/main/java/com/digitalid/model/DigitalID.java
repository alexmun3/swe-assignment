package com.digitalid.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.UUID;

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

    // === KEEP THIS (DO NOT BREAK EXISTING CODE) ===
    public DigitalID(String idNumber,
                     String fullName,
                     String dateOfBirth,
                     String address) {

        validateDateOfBirth(dateOfBirth);

        this.idNumber = idNumber;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.status = Status.ACTIVE;
    }

    // === DOMAIN-LEVEL ID GENERATION (NEW, SAFE) ===
    public static String generateId() {
        return "ID-" + UUID.randomUUID().toString().substring(0, 8);
    }

    // === OPTIONAL FACTORY METHOD (CLEANER CREATION) ===
    public static DigitalID create(String fullName,
                                   String dateOfBirth,
                                   String address) {

        return new DigitalID(
                generateId(),
                fullName,
                dateOfBirth,
                address
        );
    }

    private void validateDateOfBirth(String dateOfBirth) {

        try {
            LocalDate.parse(dateOfBirth);

        } catch (DateTimeParseException e) {

            throw new IllegalArgumentException(
                    "Invalid date format. Use YYYY-MM-DD"
            );
        }
    }

    // === GETTERS ===

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

    public boolean isActive() {
        return status == Status.ACTIVE;
    }

    // === MUTATIONS ===

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