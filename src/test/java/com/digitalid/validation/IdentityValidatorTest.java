package com.digitalid.validation;

import com.digitalid.model.DigitalID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IdentityValidatorTest {

    private final IdentityValidator validator = new IdentityValidator();

    @Test
    void shouldAllowUpdateForActiveID() {

        DigitalID digitalID = new DigitalID(
                "ID1",
                "Alex",
                "2005-01-01",
                "London"
        );

        assertTrue(validator.canUpdate(digitalID));
    }

    @Test
    void shouldRejectUpdateForRevokedID() {

        DigitalID digitalID = new DigitalID(
                "ID1",
                "Alex",
                "2005-01-01",
                "London"
        );

        digitalID.revoke();

        assertFalse(validator.canUpdate(digitalID));
    }

    @Test
    void shouldAllowSuspensionForActiveID() {

        DigitalID digitalID = new DigitalID(
                "ID1",
                "Alex",
                "2005-01-01",
                "London"
        );

        assertTrue(validator.canBeSuspended(digitalID));
    }

    @Test
    void shouldRejectSuspensionForRevokedID() {

        DigitalID digitalID = new DigitalID(
                "ID1",
                "Alex",
                "2005-01-01",
                "London"
        );

        digitalID.revoke();

        assertFalse(validator.canBeSuspended(digitalID));
    }
}