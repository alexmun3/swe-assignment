package com.digitalid.service;

import com.digitalid.logging.AuditLogger;
import com.digitalid.model.DigitalID;
import com.digitalid.model.Status;
import com.digitalid.repository.DigitalIDRepository;
import com.digitalid.validation.IdentityValidator;

public class DigitalIDService {

    private final DigitalIDRepository repository =
            new DigitalIDRepository();

    private final IdentityValidator validator =
            new IdentityValidator();

    private final AuditLogger auditLogger =
            new AuditLogger();

    public DigitalID createID(String id,
                              String name,
                              String dob,
                              String address) {

        if (repository.existsById(id)) {
            return null;
        }

        DigitalID digitalID = new DigitalID(
                id,
                name,
                dob,
                address
        );

        repository.save(digitalID);

        auditLogger.log("Created Digital ID: " + id);

        return digitalID;
    }

    public DigitalID getID(String id) {

        return repository.findById(id);
    }

    public boolean revokeID(String id) {

        DigitalID digitalID =
                repository.findById(id);

        if (digitalID == null) {
            return false;
        }

        if (!validator.canBeRevoked(digitalID)) {
            return false;
        }

        digitalID.revoke();

        auditLogger.log("Revoked Digital ID: " + id);

        return true;
    }

    public boolean suspendID(String id) {

        DigitalID digitalID =
                repository.findById(id);

        if (digitalID == null) {
            return false;
        }

        if (!validator.canBeSuspended(digitalID)) {
            return false;
        }

        digitalID.suspend();

        auditLogger.log("Suspended Digital ID: " + id);

        return true;
    }

    public boolean updateAddress(String id,
                                 String newAddress) {

        DigitalID digitalID =
                repository.findById(id);

        if (digitalID == null) {
            return false;
        }

        if (!validator.canUpdate(digitalID)) {
            return false;
        }

        digitalID.updateAddress(newAddress);

        auditLogger.log(
                "Updated address for Digital ID: " + id
        );

        return true;
    }

    public boolean markDrivingTestPassed(String id) {

        DigitalID digitalID =
                repository.findById(id);

        if (digitalID == null) {
            return false;
        }

        digitalID.markDrivingTestPassed();

        auditLogger.log(
                "Driving test passed for Digital ID: " + id
        );

        return true;
    }

    public boolean setDrivingRestriction(String id,
                                         boolean restricted) {

        DigitalID digitalID =
                repository.findById(id);

        if (digitalID == null) {
            return false;
        }

        digitalID.setDrivingRestriction(restricted);

        auditLogger.log(
                "Driving restriction updated for Digital ID: " + id
        );

        return true;
    }

    public boolean setTaxRestriction(String id,
                                     boolean restricted) {

        DigitalID digitalID =
                repository.findById(id);

        if (digitalID == null) {
            return false;
        }

        digitalID.setTaxRestriction(restricted);

        auditLogger.log(
                "Tax restriction updated for Digital ID: " + id
        );

        return true;
    }

    public boolean setFraudFlag(String id,
                                boolean fraudFlag) {

        DigitalID digitalID =
                repository.findById(id);

        if (digitalID == null) {
            return false;
        }

        digitalID.setFraudFlag(fraudFlag);

        auditLogger.log(
                "Fraud flag updated for Digital ID: " + id
        );

        return true;
    }

    public boolean isValid(String id) {

        DigitalID digitalID =
                repository.findById(id);

        return digitalID != null
                && validator.isValidForVerification(digitalID)
                && digitalID.getStatus() == Status.ACTIVE;
    }

    public boolean activateID(String id) {

        DigitalID digitalID = getID(id);

        if (digitalID == null) {
            return false;
        }

        if (!validator.canBeActivated(digitalID)) {
            return false;
        }

        digitalID.activate();

        auditLogger.log("Activated Digital ID: " + id);

        return true;
    }
}