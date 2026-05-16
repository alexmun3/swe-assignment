package com.digitalid.service;

import com.digitalid.model.DigitalID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DigitalIDServiceTest {

    @Test
    void shouldCreateAndRetrieveDigitalID() {

        DigitalIDService service = new DigitalIDService();

        service.createID("ID1", "Alex", "2005-01-01", "London");

        DigitalID id = service.getID("ID1");

        assertNotNull(id);
        assertEquals("Alex", id.getFullName());
    }

    @Test
    void shouldValidateActiveID() {

        DigitalIDService service = new DigitalIDService();

        service.createID("ID1", "Alex", "2005-01-01", "London");

        assertTrue(service.isValid("ID1"));
    }

    @Test
    void shouldInvalidateRevokedID() {

        DigitalIDService service = new DigitalIDService();

        service.createID("ID1", "Alex", "2005-01-01", "London");

        service.revokeID("ID1");

        assertFalse(service.isValid("ID1"));
    }

    @Test
    void shouldSuspendIDAndMakeInvalid() {

        DigitalIDService service = new DigitalIDService();

        service.createID("ID1", "Alex", "2005-01-01", "London");

        service.suspendID("ID1");

        assertFalse(service.isValid("ID1"));
    }

    @Test
    void shouldReturnFalseForUnknownID() {

        DigitalIDService service = new DigitalIDService();

        assertFalse(service.isValid("UNKNOWN"));
    }

    @Test
    void shouldActivateSuspendedId() {

        DigitalIDService service = new DigitalIDService();

        service.createID(
                "ID1",
                "Alex",
                "2006-01-01",
                "London"
        );

        service.suspendID("ID1");

        boolean result = service.activateID("ID1");

        assertTrue(result);

        assertTrue(service.getID("ID1").isActive());
    }

    @Test
    void shouldHandleDoubleRevokeGracefully() {

        DigitalIDService service = new DigitalIDService();

        service.createID("ID1", "Alex", "2005-01-01", "London");

        service.revokeID("ID1");
        service.revokeID("ID1"); // second call

        assertFalse(service.isValid("ID1"));
    }

    @Test
    void shouldReturnFalseWhenActivatingUnknownId() {

        DigitalIDService service = new DigitalIDService();

        boolean result = service.activateID("UNKNOWN");

        assertFalse(result);
    }
}