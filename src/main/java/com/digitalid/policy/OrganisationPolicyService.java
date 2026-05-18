package com.digitalid.policy;

import com.digitalid.model.DigitalID;
import com.digitalid.model.OperationResult;
import com.digitalid.model.Status;

/**
 * Contains organisation-specific verification
 * and access control rules.
 */

public class OrganisationPolicyService {

    public OperationResult verifyForBank(DigitalID digitalID) {

        if (digitalID.getStatus() == Status.ACTIVE) {
            return new OperationResult(true, "VALID");
        }

        return new OperationResult(false, "INVALID");
    }

    public OperationResult verifyForEmployer(DigitalID digitalID) {

        if (digitalID.getStatus() == Status.ACTIVE) {
            return new OperationResult(true, "VALID");
        }

        return new OperationResult(false, "INVALID");
    }

    public OperationResult verifyForTaxAuthority(
            DigitalID digitalID
    ) {

        if (digitalID.getStatus() != Status.ACTIVE) {
            return new OperationResult(
                    false,
                    "TAX VERIFICATION FAILED"
            );
        }

        if (digitalID.isTaxRestricted()) {
            return new OperationResult(
                    false,
                    "TAX RESTRICTION ACTIVE"
            );
        }

        return new OperationResult(
                true,
                "TAX VERIFICATION PASSED"
        );
    }

    public OperationResult verifyForDrivingAuthority(
            DigitalID digitalID
    ) {

        if (digitalID.getStatus() != Status.ACTIVE) {
            return new OperationResult(
                    false,
                    "LICENCE NOT ELIGIBLE"
            );
        }

        if (!digitalID.hasPassedDrivingTest()) {
            return new OperationResult(
                    false,
                    "DRIVING TEST NOT PASSED"
            );
        }

        if (digitalID.isDrivingRestricted()) {
            return new OperationResult(
                    false,
                    "DRIVING RESTRICTION ACTIVE"
            );
        }

        return new OperationResult(
                true,
                "LICENCE ELIGIBLE"
        );
    }
}