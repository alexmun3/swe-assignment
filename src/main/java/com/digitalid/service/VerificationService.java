package com.digitalid.service;

import com.digitalid.logging.AuditLogger;
import com.digitalid.model.DigitalID;
import com.digitalid.model.OrganisationType;
import com.digitalid.model.Status;

public class VerificationService {

    private final DigitalIDService digitalIDService;

    private final AuditLogger auditLogger =
            new AuditLogger();

    public VerificationService(DigitalIDService digitalIDService) {

        this.digitalIDService = digitalIDService;
    }

    public String verifyIdentity(String id,
                                 OrganisationType organisationType) {

        auditLogger.log(
                "Verification request for ID "
                        + id
                        + " by "
                        + organisationType
        );

        DigitalID digitalID = digitalIDService.getID(id);

        if (digitalID == null) {
            return "IDENTITY NOT FOUND";
        }

        if (digitalID.hasFraudFlag()) {
            return "IDENTITY FLAGGED FOR FRAUD";
        }

        return switch (organisationType) {

            case BANK, EMPLOYER ->
                    verifyForBasicOrganisation(digitalID);

            case TAX_AUTHORITY ->
                    verifyForTaxAuthority(digitalID);

            case DRIVING_LICENCE_AUTHORITY ->
                    verifyForDrivingAuthority(digitalID);

            case CENTRAL_AUTHORITY ->
                    verifyForCentralAuthority(digitalID);
        };
    }

    private String verifyForBasicOrganisation(DigitalID digitalID) {

        if (digitalID.getStatus() == Status.ACTIVE) {
            return "VALID";
        }

        return "INVALID";
    }

    private String verifyForTaxAuthority(DigitalID digitalID) {

        if (digitalID.getStatus() != Status.ACTIVE) {
            return "TAX VERIFICATION FAILED";
        }

        if (digitalID.isTaxRestricted()) {
            return "TAX RESTRICTION ACTIVE";
        }

        return "TAX VERIFICATION PASSED";
    }

    private String verifyForDrivingAuthority(DigitalID digitalID) {

        if (digitalID.getStatus() != Status.ACTIVE) {
            return "LICENCE NOT ELIGIBLE";
        }

        if (!digitalID.hasPassedDrivingTest()) {
            return "DRIVING TEST NOT PASSED";
        }

        if (digitalID.isDrivingRestricted()) {
            return "DRIVING RESTRICTION ACTIVE";
        }

        return "LICENCE ELIGIBLE";
    }

    private String verifyForCentralAuthority(DigitalID digitalID) {

        return """
                FULL IDENTITY DETAILS
                
                ID: %s
                NAME: %s
                DOB: %s
                ADDRESS: %s
                STATUS: %s
                PASSED DRIVING TEST: %s
                DRIVING RESTRICTED: %s
                TAX RESTRICTED: %s
                FRAUD FLAG: %s
                """.formatted(
                digitalID.getIdNumber(),
                digitalID.getFullName(),
                digitalID.getDateOfBirth(),
                digitalID.getAddress(),
                digitalID.getStatus(),
                digitalID.hasPassedDrivingTest(),
                digitalID.isDrivingRestricted(),
                digitalID.isTaxRestricted(),
                digitalID.hasFraudFlag()
        );
    }
}