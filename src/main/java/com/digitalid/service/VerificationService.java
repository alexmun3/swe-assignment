package com.digitalid.service;

import com.digitalid.logging.AuditLogger;
import com.digitalid.model.DigitalID;
import com.digitalid.model.OperationResult;
import com.digitalid.model.OrganisationType;
import com.digitalid.policy.OrganisationPolicyService;

public class VerificationService {

    private final DigitalIDService digitalIDService;

    private final OrganisationPolicyService policyService =
            new OrganisationPolicyService();

    private final AuditLogger auditLogger =
            new AuditLogger();

    public VerificationService(
            DigitalIDService digitalIDService
    ) {

        this.digitalIDService = digitalIDService;
    }

    public OperationResult verifyIdentity(
            String id,
            OrganisationType organisationType
    ) {

        auditLogger.log(
                "Verification request for ID "
                        + id
                        + " by "
                        + organisationType
        );

        DigitalID digitalID =
                digitalIDService.getID(id);

        if (digitalID == null) {

            return new OperationResult(
                    false,
                    "IDENTITY NOT FOUND"
            );
        }

        if (digitalID.hasFraudFlag()) {

            return new OperationResult(
                    false,
                    "IDENTITY FLAGGED FOR FRAUD"
            );
        }

        return switch (organisationType) {

            case BANK ->
                    policyService.verifyForBank(
                            digitalID
                    );

            case EMPLOYER ->
                    policyService.verifyForEmployer(
                            digitalID
                    );

            case TAX_AUTHORITY ->
                    policyService.verifyForTaxAuthority(
                            digitalID
                    );

            case DRIVING_LICENCE_AUTHORITY ->
                    policyService.verifyForDrivingAuthority(
                            digitalID
                    );

            case CENTRAL_AUTHORITY ->
                    new OperationResult(
                            true,
                            buildFullIdentityDetails(
                                    digitalID
                            )
                    );
        };
    }

    private String buildFullIdentityDetails(
            DigitalID digitalID
    ) {

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