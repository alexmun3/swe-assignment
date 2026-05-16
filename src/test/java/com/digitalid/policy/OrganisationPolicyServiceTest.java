package com.digitalid.policy;

import com.digitalid.model.DigitalID;
import com.digitalid.model.OperationResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrganisationPolicyServiceTest {

    private final OrganisationPolicyService policyService =
            new OrganisationPolicyService();

    @Test
    void shouldReturnValidForActiveBankIdentity() {

        DigitalID digitalID =
                new DigitalID(
                        "ID1",
                        "Alex",
                        "2006-01-01",
                        "London"
                );

        OperationResult result =
                policyService.verifyForBank(
                        digitalID
                );

        assertTrue(result.isSuccess());

        assertEquals(
                "VALID",
                result.getMessage()
        );
    }

    @Test
    void shouldFailTaxVerificationWhenRestricted() {

        DigitalID digitalID =
                new DigitalID(
                        "ID2",
                        "Bob",
                        "2005-05-05",
                        "Manchester"
                );

        digitalID.setTaxRestriction(true);

        OperationResult result =
                policyService.verifyForTaxAuthority(
                        digitalID
                );

        assertFalse(result.isSuccess());

        assertEquals(
                "TAX RESTRICTION ACTIVE",
                result.getMessage()
        );
    }

    @Test
    void shouldFailDrivingVerificationIfTestNotPassed() {

        DigitalID digitalID =
                new DigitalID(
                        "ID3",
                        "Charlie",
                        "2004-03-03",
                        "Birmingham"
                );

        OperationResult result =
                policyService.verifyForDrivingAuthority(
                        digitalID
                );

        assertFalse(result.isSuccess());

        assertEquals(
                "DRIVING TEST NOT PASSED",
                result.getMessage()
        );
    }

    @Test
    void shouldReturnLicenceEligibleWhenConditionsMet() {

        DigitalID digitalID =
                new DigitalID(
                        "ID4",
                        "David",
                        "2000-10-10",
                        "Liverpool"
                );

        digitalID.markDrivingTestPassed();

        OperationResult result =
                policyService.verifyForDrivingAuthority(
                        digitalID
                );

        assertTrue(result.isSuccess());

        assertEquals(
                "LICENCE ELIGIBLE",
                result.getMessage()
        );
    }

    @Test
    void shouldFailDrivingVerificationWhenRestricted() {

        DigitalID digitalID =
                new DigitalID(
                        "ID5",
                        "Emma",
                        "1999-09-09",
                        "Leeds"
                );

        digitalID.markDrivingTestPassed();

        digitalID.setDrivingRestriction(true);

        OperationResult result =
                policyService.verifyForDrivingAuthority(
                        digitalID
                );

        assertFalse(result.isSuccess());

        assertEquals(
                "DRIVING RESTRICTION ACTIVE",
                result.getMessage()
        );
    }
}