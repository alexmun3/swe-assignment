package com.digitalid.validation;

import com.digitalid.model.DigitalID;
import com.digitalid.model.Status;

public class IdentityValidator {

    public boolean canUpdate(DigitalID digitalID) {

        return digitalID.getStatus() != Status.REVOKED;
    }

    public boolean canBeSuspended(DigitalID digitalID) {

        return digitalID.getStatus() == Status.ACTIVE;
    }

    public boolean canBeRevoked(DigitalID digitalID) {

        return digitalID.getStatus() != Status.REVOKED;
    }

    public boolean isValidForVerification(DigitalID digitalID) {

        return digitalID.getStatus() == Status.ACTIVE;
    }

    public boolean canBeActivated(DigitalID digitalID) {

        return digitalID.getStatus() == Status.SUSPENDED;
    }
}