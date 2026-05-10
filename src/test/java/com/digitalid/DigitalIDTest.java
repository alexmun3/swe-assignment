package com.digitalid;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DigitalIDTest {

    @Test
    void shouldCreateActiveDigitalId() {

        DigitalID digitalID = new DigitalID("ABC123");

        assertEquals("ABC123", digitalID.getIdNumber());
        assertTrue(digitalID.isActive());
    }

    @Test
    void shouldRevokeDigitalId() {

        DigitalID digitalID = new DigitalID("ABC123");

        digitalID.revoke();

        assertFalse(digitalID.isActive());
    }
}