package com.digitalid.service;

import com.digitalid.model.DigitalID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DigitalIDServiceTest {

    @Test
    void shouldCreateAndRetrieveDigitalID() {

        DigitalIDService service = new DigitalIDService();

        DigitalID created =
                service.createID("Alex", "2005-01-01", "London");

        String id = created.getIdNumber();

        DigitalID result = service.getID(id);

        assertNotNull(result);
        assertEquals("Alex", result.getFullName());
    }

    @Test
    void shouldValidateActiveID() {

        DigitalIDService service = new DigitalIDService();

        DigitalID created =
                service.createID("Alex", "2005-01-01", "London");

        String id = created.getIdNumber();

        assertTrue(service.isValid(id));
    }

    @Test
    void shouldInvalidateRevokedID() {

        DigitalIDService service = new DigitalIDService();

        DigitalID created =
                service.createID("Alex", "2005-01-01", "London");

        String id = created.getIdNumber();

        service.revokeID(id);

        assertFalse(service.isValid(id));
    }

    @Test
    void shouldSuspendIDAndMakeInvalid() {

        DigitalIDService service = new DigitalIDService();

        DigitalID created =
                service.createID("Alex", "2005-01-01", "London");

        String id = created.getIdNumber();

        service.suspendID(id);

        assertFalse(service.isValid(id));
    }

    @Test
    void shouldReturnFalseForUnknownID() {

        DigitalIDService service = new DigitalIDService();

        assertFalse(service.isValid("UNKNOWN"));
    }

    @Test
    void shouldActivateSuspendedId() {

        DigitalIDService service = new DigitalIDService();

        DigitalID created =
                service.createID("Alex", "2006-01-01", "London");

        String id = created.getIdNumber();

        service.suspendID(id);

        boolean result = service.activateID(id);

        assertTrue(result);

        assertTrue(service.getID(id).isActive());
    }

    @Test
    void shouldHandleDoubleRevokeGracefully() {

        DigitalIDService service = new DigitalIDService();

        DigitalID created =
                service.createID("Alex", "2005-01-01", "London");

        String id = created.getIdNumber();

        service.revokeID(id);
        service.revokeID(id);

        assertFalse(service.isValid(id));
    }

    @Test
    void shouldReturnFalseWhenActivatingUnknownId() {

        DigitalIDService service = new DigitalIDService();

        boolean result =
                service.activateID("UNKNOWN");

        assertFalse(result);
    }
}