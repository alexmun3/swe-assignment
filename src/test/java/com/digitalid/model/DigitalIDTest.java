package com.digitalid.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DigitalIDTest {

    @Test
    void shouldCreateActiveDigitalId() {

        DigitalID digitalID = new DigitalID(
                "ID123",
                "Alex Munden",
                "2005-01-01",
                "London"
        );

        assertEquals("ID123", digitalID.getIdNumber());
        assertEquals("Alex Munden", digitalID.getFullName());
        assertEquals("2005-01-01", digitalID.getDateOfBirth());
        assertEquals("London", digitalID.getAddress());

        assertEquals(Status.ACTIVE, digitalID.getStatus());
        assertTrue(digitalID.isActive());
    }

    @Test
    void shouldSuspendDigitalId() {

        DigitalID digitalID = new DigitalID(
                "ID123",
                "Alex Munden",
                "2005-01-01",
                "London"
        );

        digitalID.suspend();

        assertEquals(Status.SUSPENDED, digitalID.getStatus());
        assertFalse(digitalID.isActive());
    }

    @Test
    void shouldRevokeDigitalId() {

        DigitalID digitalID = new DigitalID(
                "ID123",
                "Alex Munden",
                "2005-01-01",
                "London"
        );

        digitalID.revoke();

        assertEquals(Status.REVOKED, digitalID.getStatus());
        assertFalse(digitalID.isActive());
    }

    @Test
    void shouldUpdateAddress() {

        DigitalID digitalID = new DigitalID(
                "ID123",
                "Alex Munden",
                "2005-01-01",
                "London"
        );

        digitalID.updateAddress("Manchester");

        assertEquals("Manchester", digitalID.getAddress());
    }
}