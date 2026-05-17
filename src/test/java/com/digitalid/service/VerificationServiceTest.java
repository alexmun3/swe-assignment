package com.digitalid.service;

import com.digitalid.model.DigitalID;
import com.digitalid.model.OperationResult;
import com.digitalid.model.OrganisationType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VerificationServiceTest {

    @Test
    void shouldReturnValidForBankWhenActive() {

        DigitalIDService digitalIDService =
                new DigitalIDService();

        DigitalID digitalID =
                digitalIDService.createID(
                        "Alex",
                        "2006-01-01",
                        "London"
                );

        String id = digitalID.getIdNumber();

        VerificationService verificationService =
                new VerificationService(digitalIDService);

        OperationResult result =
                verificationService.verifyIdentity(
                        id,
                        OrganisationType.BANK
                );

        assertTrue(result.isSuccess());
        assertTrue(result.getMessage().contains("BANK VERIFICATION"));
        assertTrue(result.getMessage().contains("RESULT"));
    }

    @Test
    void shouldReturnInvalidForRevokedID() {

        DigitalIDService digitalIDService =
                new DigitalIDService();

        DigitalID digitalID =
                digitalIDService.createID(
                        "Alex",
                        "2006-01-01",
                        "London"
                );

        String id = digitalID.getIdNumber();

        digitalIDService.revokeID(id);

        VerificationService verificationService =
                new VerificationService(digitalIDService);

        OperationResult result =
                verificationService.verifyIdentity(
                        id,
                        OrganisationType.EMPLOYER
                );

        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("INVALID"));
    }

    @Test
    void shouldReturnFullDetailsForCentralAuthority() {

        DigitalIDService digitalIDService =
                new DigitalIDService();

        DigitalID digitalID =
                digitalIDService.createID(
                        "Alex",
                        "2006-01-01",
                        "London"
                );

        String id = digitalID.getIdNumber();

        VerificationService verificationService =
                new VerificationService(digitalIDService);

        OperationResult result =
                verificationService.verifyIdentity(
                        id,
                        OrganisationType.CENTRAL_AUTHORITY
                );

        assertTrue(result.isSuccess());
        assertTrue(result.getMessage().contains("Alex"));
        assertTrue(result.getMessage().contains("ACTIVE"));
        assertTrue(result.getMessage().contains("EXPIRY"));
    }

    @Test
    void shouldReturnIdentityNotFound() {

        DigitalIDService digitalIDService =
                new DigitalIDService();

        VerificationService verificationService =
                new VerificationService(digitalIDService);

        OperationResult result =
                verificationService.verifyIdentity(
                        "UNKNOWN",
                        OrganisationType.BANK
                );

        assertFalse(result.isSuccess());
        assertEquals("IDENTITY NOT FOUND", result.getMessage());
    }

    @Test
    void shouldFailDrivingVerificationIfTestNotPassed() {

        DigitalIDService digitalIDService =
                new DigitalIDService();

        DigitalID digitalID =
                digitalIDService.createID(
                        "Alex",
                        "2006-01-01",
                        "London"
                );

        String id = digitalID.getIdNumber();

        VerificationService verificationService =
                new VerificationService(digitalIDService);

        OperationResult result =
                verificationService.verifyIdentity(
                        id,
                        OrganisationType.DRIVING_LICENCE_AUTHORITY
                );

        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("DRIVING TEST NOT PASSED"));
    }

    @Test
    void shouldReturnLicenceEligibleWhenDrivingTestPassed() {

        DigitalIDService digitalIDService =
                new DigitalIDService();

        DigitalID digitalID =
                digitalIDService.createID(
                        "Alex",
                        "2006-01-01",
                        "London"
                );

        String id = digitalID.getIdNumber();

        digitalIDService.markDrivingTestPassed(id);

        VerificationService verificationService =
                new VerificationService(digitalIDService);

        OperationResult result =
                verificationService.verifyIdentity(
                        id,
                        OrganisationType.DRIVING_LICENCE_AUTHORITY
                );

        assertTrue(result.isSuccess());
        assertTrue(result.getMessage().contains("LICENCE ELIGIBLE"));
    }

    @Test
    void shouldBlockDrivingEligibilityWhenRestricted() {

        DigitalIDService digitalIDService =
                new DigitalIDService();

        DigitalID digitalID =
                digitalIDService.createID(
                        "Alex",
                        "2006-01-01",
                        "London"
                );

        String id = digitalID.getIdNumber();

        digitalIDService.markDrivingTestPassed(id);
        digitalIDService.setDrivingRestriction(id, true);

        VerificationService verificationService =
                new VerificationService(digitalIDService);

        OperationResult result =
                verificationService.verifyIdentity(
                        id,
                        OrganisationType.DRIVING_LICENCE_AUTHORITY
                );

        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("DRIVING RESTRICTION ACTIVE"));
    }

    @Test
    void shouldRejectFraudulentIdentity() {

        DigitalIDService digitalIDService =
                new DigitalIDService();

        DigitalID digitalID =
                digitalIDService.createID(
                        "Alex",
                        "2006-01-01",
                        "London"
                );

        String id = digitalID.getIdNumber();

        digitalIDService.setFraudFlag(id, true);

        VerificationService verificationService =
                new VerificationService(digitalIDService);

        OperationResult result =
                verificationService.verifyIdentity(
                        id,
                        OrganisationType.BANK
                );

        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("FRAUD"));
    }

    @Test
    void shouldRejectFraudAcrossAllVerificationTypes() {

        DigitalIDService digitalIDService =
                new DigitalIDService();

        DigitalID digitalID =
                digitalIDService.createID(
                        "Alex",
                        "2006-01-01",
                        "London"
                );

        String id = digitalID.getIdNumber();

        digitalIDService.setFraudFlag(id, true);

        VerificationService verificationService =
                new VerificationService(digitalIDService);

        OperationResult result =
                verificationService.verifyIdentity(
                        id,
                        OrganisationType.DRIVING_LICENCE_AUTHORITY
                );

        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("FRAUD"));
    }
}