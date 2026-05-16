package com.digitalid.service;

import com.digitalid.model.OperationResult;
import com.digitalid.model.OrganisationType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VerificationServiceTest {

    @Test
    void shouldReturnValidForBankWhenActive() {

        DigitalIDService digitalIDService =
                new DigitalIDService();

        digitalIDService.createID(
                "ID1",
                "Alex",
                "2006-01-01",
                "London"
        );

        VerificationService verificationService =
                new VerificationService(
                        digitalIDService
                );

        OperationResult result =
                verificationService.verifyIdentity(
                        "ID1",
                        OrganisationType.BANK
                );

        assertTrue(result.isSuccess());

        assertEquals(
                "VALID",
                result.getMessage()
        );
    }

    @Test
    void shouldReturnInvalidForRevokedID() {

        DigitalIDService digitalIDService =
                new DigitalIDService();

        digitalIDService.createID(
                "ID1",
                "Alex",
                "2006-01-01",
                "London"
        );

        digitalIDService.revokeID("ID1");

        VerificationService verificationService =
                new VerificationService(
                        digitalIDService
                );

        OperationResult result =
                verificationService.verifyIdentity(
                        "ID1",
                        OrganisationType.EMPLOYER
                );

        assertFalse(result.isSuccess());

        assertEquals(
                "INVALID",
                result.getMessage()
        );
    }

    @Test
    void shouldReturnFullDetailsForCentralAuthority() {

        DigitalIDService digitalIDService =
                new DigitalIDService();

        digitalIDService.createID(
                "ID1",
                "Alex",
                "2006-01-01",
                "London"
        );

        VerificationService verificationService =
                new VerificationService(
                        digitalIDService
                );

        OperationResult result =
                verificationService.verifyIdentity(
                        "ID1",
                        OrganisationType.CENTRAL_AUTHORITY
                );

        assertTrue(result.isSuccess());

        assertTrue(
                result.getMessage().contains("Alex")
        );

        assertTrue(
                result.getMessage().contains("ACTIVE")
        );
    }

    @Test
    void shouldReturnIdentityNotFound() {

        DigitalIDService digitalIDService =
                new DigitalIDService();

        VerificationService verificationService =
                new VerificationService(
                        digitalIDService
                );

        OperationResult result =
                verificationService.verifyIdentity(
                        "UNKNOWN",
                        OrganisationType.BANK
                );

        assertFalse(result.isSuccess());

        assertEquals(
                "IDENTITY NOT FOUND",
                result.getMessage()
        );
    }

    @Test
    void shouldFailDrivingVerificationIfTestNotPassed() {

        DigitalIDService digitalIDService =
                new DigitalIDService();

        digitalIDService.createID(
                "ID1",
                "Alex",
                "2006-01-01",
                "London"
        );

        VerificationService verificationService =
                new VerificationService(
                        digitalIDService
                );

        OperationResult result =
                verificationService.verifyIdentity(
                        "ID1",
                        OrganisationType.DRIVING_LICENCE_AUTHORITY
                );

        assertFalse(result.isSuccess());

        assertEquals(
                "DRIVING TEST NOT PASSED",
                result.getMessage()
        );
    }

    @Test
    void shouldReturnLicenceEligibleWhenDrivingTestPassed() {

        DigitalIDService digitalIDService =
                new DigitalIDService();

        digitalIDService.createID(
                "ID1",
                "Alex",
                "2006-01-01",
                "London"
        );

        digitalIDService.markDrivingTestPassed(
                "ID1"
        );

        VerificationService verificationService =
                new VerificationService(
                        digitalIDService
                );

        OperationResult result =
                verificationService.verifyIdentity(
                        "ID1",
                        OrganisationType.DRIVING_LICENCE_AUTHORITY
                );

        assertTrue(result.isSuccess());

        assertEquals(
                "LICENCE ELIGIBLE",
                result.getMessage()
        );
    }

    @Test
    void shouldBlockDrivingEligibilityWhenRestricted() {

        DigitalIDService digitalIDService =
                new DigitalIDService();

        digitalIDService.createID(
                "ID1",
                "Alex",
                "2006-01-01",
                "London"
        );

        digitalIDService.markDrivingTestPassed(
                "ID1"
        );

        digitalIDService.setDrivingRestriction(
                "ID1",
                true
        );

        VerificationService verificationService =
                new VerificationService(
                        digitalIDService
                );

        OperationResult result =
                verificationService.verifyIdentity(
                        "ID1",
                        OrganisationType.DRIVING_LICENCE_AUTHORITY
                );

        assertFalse(result.isSuccess());

        assertEquals(
                "DRIVING RESTRICTION ACTIVE",
                result.getMessage()
        );
    }

    @Test
    void shouldRejectFraudulentIdentity() {

        DigitalIDService digitalIDService =
                new DigitalIDService();

        digitalIDService.createID(
                "ID1",
                "Alex",
                "2006-01-01",
                "London"
        );

        digitalIDService.setFraudFlag(
                "ID1",
                true
        );

        VerificationService verificationService =
                new VerificationService(
                        digitalIDService
                );

        OperationResult result =
                verificationService.verifyIdentity(
                        "ID1",
                        OrganisationType.BANK
                );

        assertFalse(result.isSuccess());

        assertEquals(
                "IDENTITY FLAGGED FOR FRAUD",
                result.getMessage()
        );
    }

    @Test
    void shouldRejectFraudAcrossAllVerificationTypes() {

        DigitalIDService digitalIDService =
                new DigitalIDService();

        digitalIDService.createID(
                "ID1",
                "Alex",
                "2006-01-01",
                "London"
        );

        digitalIDService.setFraudFlag("ID1", true);

        VerificationService verificationService =
                new VerificationService(digitalIDService);

        OperationResult result =
                verificationService.verifyIdentity(
                        "ID1",
                        OrganisationType.DRIVING_LICENCE_AUTHORITY
                );

        assertFalse(result.isSuccess());

        assertEquals(
                "IDENTITY FLAGGED FOR FRAUD",
                result.getMessage()
        );
    }

    @Test
    void shouldFailVerificationForSuspendedID() {

        DigitalIDService digitalIDService =
                new DigitalIDService();

        digitalIDService.createID(
                "ID1",
                "Alex",
                "2006-01-01",
                "London"
        );

        digitalIDService.suspendID("ID1");

        VerificationService verificationService =
                new VerificationService(digitalIDService);

        OperationResult result =
                verificationService.verifyIdentity(
                        "ID1",
                        OrganisationType.BANK
                );

        assertFalse(result.isSuccess());

        assertEquals(
                "INVALID",
                result.getMessage()
        );
    }
}