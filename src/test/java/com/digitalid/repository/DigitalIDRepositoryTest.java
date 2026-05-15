package com.digitalid.repository;

import com.digitalid.model.DigitalID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DigitalIDRepositoryTest {

    @Test
    void shouldSaveAndRetrieveDigitalID() {

        DigitalIDRepository repository =
                new DigitalIDRepository();

        DigitalID id = new DigitalID(
                "ID1",
                "Alex",
                "2006-01-01",
                "London"
        );

        repository.save(id);

        DigitalID result =
                repository.findById("ID1");

        assertNotNull(result);
        assertEquals("Alex", result.getFullName());
    }

    @Test
    void shouldReturnNullWhenIDDoesNotExist() {

        DigitalIDRepository repository =
                new DigitalIDRepository();

        DigitalID result =
                repository.findById("UNKNOWN");

        assertNull(result);
    }

    @Test
    void shouldConfirmIDExistsAfterSaving() {

        DigitalIDRepository repository =
                new DigitalIDRepository();

        DigitalID id = new DigitalID(
                "ID2",
                "Bob",
                "2005-05-05",
                "Manchester"
        );

        repository.save(id);

        assertTrue(repository.existsById("ID2"));
    }

    @Test
    void shouldReturnFalseWhenIDDoesNotExist() {

        DigitalIDRepository repository =
                new DigitalIDRepository();

        assertFalse(repository.existsById("NOPE"));
    }
}