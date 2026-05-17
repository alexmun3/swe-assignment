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

        if (digitalID.isExpired()) {

            return new OperationResult(
                    false,
                    "IDENTITY EXPIRED"
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
                    buildBankResponse(digitalID);

            case EMPLOYER ->
                    buildEmployerResponse(digitalID);

            case TAX_AUTHORITY ->
                    buildTaxAuthorityResponse(digitalID);

            case DRIVING_LICENCE_AUTHORITY ->
                    buildDrivingAuthorityResponse(digitalID);

            case CENTRAL_AUTHORITY ->
                    buildCentralAuthorityResponse(digitalID);
        };
    }

    private OperationResult buildBankResponse(
            DigitalID digitalID
    ) {

        OperationResult result =
                policyService.verifyForBank(
                        digitalID
                );

        return new OperationResult(
                result.isSuccess(),
                """
                BANK VERIFICATION
                
                NAME: %s
                DOB: %s
                STATUS: %s
                EXPIRY DATE: %s
                
                RESULT: %s
                """.formatted(
                        digitalID.getFullName(),
                        digitalID.getDateOfBirth(),
                        digitalID.getStatus(),
                        digitalID.getExpiryDate(),
                        result.getMessage()
                )
        );
    }

    private OperationResult buildEmployerResponse(
            DigitalID digitalID
    ) {

        OperationResult result =
                policyService.verifyForEmployer(
                        digitalID
                );

        return new OperationResult(
                result.isSuccess(),
                """
                EMPLOYER VERIFICATION
                
                NAME: %s
                STATUS: %s
                
                RESULT: %s
                """.formatted(
                        digitalID.getFullName(),
                        digitalID.getStatus(),
                        result.getMessage()
                )
        );
    }

    private OperationResult buildTaxAuthorityResponse(
            DigitalID digitalID
    ) {

        OperationResult result =
                policyService.verifyForTaxAuthority(
                        digitalID
                );

        return new OperationResult(
                result.isSuccess(),
                """
                TAX AUTHORITY VERIFICATION
                
                NAME: %s
                TAX RESTRICTED: %s
                
                RESULT: %s
                """.formatted(
                        digitalID.getFullName(),
                        digitalID.isTaxRestricted(),
                        result.getMessage()
                )
        );
    }

    private OperationResult buildDrivingAuthorityResponse(
            DigitalID digitalID
    ) {

        OperationResult result =
                policyService.verifyForDrivingAuthority(
                        digitalID
                );

        return new OperationResult(
                result.isSuccess(),
                """
                DRIVING AUTHORITY VERIFICATION
                
                NAME: %s
                PASSED DRIVING TEST: %s
                DRIVING RESTRICTED: %s
                
                RESULT: %s
                """.formatted(
                        digitalID.getFullName(),
                        digitalID.hasPassedDrivingTest(),
                        digitalID.isDrivingRestricted(),
                        result.getMessage()
                )
        );
    }

    private OperationResult buildCentralAuthorityResponse(
            DigitalID digitalID
    ) {

        return new OperationResult(
                true,
                """
                FULL IDENTITY DETAILS
                
                ID: %s
                NAME: %s
                DOB: %s
                ADDRESS: %s
                STATUS: %s
                EXPIRY DATE: %s
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
                        digitalID.getExpiryDate(),
                        digitalID.hasPassedDrivingTest(),
                        digitalID.isDrivingRestricted(),
                        digitalID.isTaxRestricted(),
                        digitalID.hasFraudFlag()
                )
        );
    }
}