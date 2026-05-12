package com.digitalid.service;

import com.digitalid.model.DigitalID;
import com.digitalid.model.Status;
import com.digitalid.validation.IdentityValidator;

import java.util.HashMap;
import java.util.Map;

public class DigitalIDService {

    private final Map<String, DigitalID> database = new HashMap<>();

    private final IdentityValidator validator = new IdentityValidator();

    public DigitalID createID(String id,
                              String name,
                              String dob,
                              String address) {

        DigitalID digitalID = new DigitalID(
                id,
                name,
                dob,
                address
        );

        database.put(id, digitalID);

        return digitalID;
    }

    public DigitalID getID(String id) {

        return database.get(id);
    }

    public boolean revokeID(String id) {

        DigitalID digitalID = database.get(id);

        if (digitalID == null) {
            return false;
        }

        if (!validator.canBeRevoked(digitalID)) {
            return false;
        }

        digitalID.revoke();

        return true;
    }

    public boolean suspendID(String id) {

        DigitalID digitalID = database.get(id);

        if (digitalID == null) {
            return false;
        }

        if (!validator.canBeSuspended(digitalID)) {
            return false;
        }

        digitalID.suspend();

        return true;
    }

    public boolean updateAddress(String id, String newAddress) {

        DigitalID digitalID = database.get(id);

        if (digitalID == null) {
            return false;
        }

        if (!validator.canUpdate(digitalID)) {
            return false;
        }

        digitalID.updateAddress(newAddress);

        return true;
    }

    public boolean isValid(String id) {

        DigitalID digitalID = database.get(id);

        return digitalID != null
                && validator.isValidForVerification(digitalID)
                && digitalID.getStatus() == Status.ACTIVE;
    }
}