package com.digitalid.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OperationResultTest {

    @Test
    void shouldCreateSuccessfulResult() {

        OperationResult result =
                new OperationResult(
                        true,
                        "VALID"
                );

        assertTrue(result.isSuccess());

        assertEquals(
                "VALID",
                result.getMessage()
        );
    }

    @Test
    void shouldCreateFailureResult() {

        OperationResult result =
                new OperationResult(
                        false,
                        "INVALID"
                );

        assertFalse(result.isSuccess());

        assertEquals(
                "INVALID",
                result.getMessage()
        );
    }

    @Test
    void toStringShouldReturnMessage() {

        OperationResult result =
                new OperationResult(
                        true,
                        "TEST MESSAGE"
                );

        assertEquals(
                "TEST MESSAGE",
                result.toString()
        );
    }
}