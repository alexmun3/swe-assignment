package com.digitalid;

public class DigitalID {

    private final String idNumber;
    private boolean active;

    public DigitalID(String idNumber) {
        this.idNumber = idNumber;
        this.active = true;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void revoke() {
        this.active = false;
    }
}