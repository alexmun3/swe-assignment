package com.digitalid.service;

import com.digitalid.model.DigitalID;
import com.digitalid.model.OrganisationType;
import com.digitalid.model.Status;

public class VerificationService {

    private final DigitalIDService digitalIDService;

    public VerificationService(DigitalIDService digitalIDService) {

        this.digitalIDService = digitalIDService;
    }

    public String verifyIdentity(String id,
                                 OrganisationType organisationType) {

        DigitalID digitalID = digitalIDService.getID(id);

        if (digitalID == null) {
            return "IDENTITY NOT FOUND";
        }

        return switch (organisationType) {

            case BANK, EMPLOYER -> verifyForBasicOrganisation(digitalID);

            case TAX_AUTHORITY -> verifyForTaxAuthority(digitalID);

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

        if (digitalID.getStatus() == Status.ACTIVE) {
            return "TAX VERIFICATION PASSED";
        }

        return "TAX VERIFICATION FAILED";
    }

    private String verifyForDrivingAuthority(DigitalID digitalID) {

        if (digitalID.getStatus() == Status.ACTIVE) {
            return "LICENCE ELIGIBLE";
        }

        return "LICENCE NOT ELIGIBLE";
    }

    private String verifyForCentralAuthority(DigitalID digitalID) {

        return """
                FULL IDENTITY DETAILS
                ID: %s
                NAME: %s
                DOB: %s
                ADDRESS: %s
                STATUS: %s
                """.formatted(
                digitalID.getIdNumber(),
                digitalID.getFullName(),
                digitalID.getDateOfBirth(),
                digitalID.getAddress(),
                digitalID.getStatus()
        );
    }
}