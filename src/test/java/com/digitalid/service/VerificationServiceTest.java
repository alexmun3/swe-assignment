package com.digitalid.service;

import com.digitalid.model.OrganisationType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VerificationServiceTest {

    @Test
    void shouldReturnValidForBankWhenActive() {

        DigitalIDService digitalIDService = new DigitalIDService();

        digitalIDService.createID(
                "ID1",
                "Alex",
                "2006-01-01",
                "London"
        );

        VerificationService verificationService =
                new VerificationService(digitalIDService);

        String result = verificationService.verifyIdentity(
                "ID1",
                OrganisationType.BANK
        );

        assertEquals("VALID", result);
    }

    @Test
    void shouldReturnInvalidForRevokedID() {

        DigitalIDService digitalIDService = new DigitalIDService();

        digitalIDService.createID(
                "ID1",
                "Alex",
                "2006-01-01",
                "London"
        );

        digitalIDService.revokeID("ID1");

        VerificationService verificationService =
                new VerificationService(digitalIDService);

        String result = verificationService.verifyIdentity(
                "ID1",
                OrganisationType.EMPLOYER
        );

        assertEquals("INVALID", result);
    }

    @Test
    void shouldReturnFullDetailsForCentralAuthority() {

        DigitalIDService digitalIDService = new DigitalIDService();

        digitalIDService.createID(
                "ID1",
                "Alex",
                "2006-01-01",
                "London"
        );

        VerificationService verificationService =
                new VerificationService(digitalIDService);

        String result = verificationService.verifyIdentity(
                "ID1",
                OrganisationType.CENTRAL_AUTHORITY
        );

        assertTrue(result.contains("Alex"));
        assertTrue(result.contains("ACTIVE"));
    }

    @Test
    void shouldReturnIdentityNotFound() {

        DigitalIDService digitalIDService = new DigitalIDService();

        VerificationService verificationService =
                new VerificationService(digitalIDService);

        String result = verificationService.verifyIdentity(
                "UNKNOWN",
                OrganisationType.BANK
        );

        assertEquals("IDENTITY NOT FOUND", result);
    }
}