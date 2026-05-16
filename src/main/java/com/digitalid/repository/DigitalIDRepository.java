package com.digitalid.repository;

import com.digitalid.model.DigitalID;

import java.util.HashMap;
import java.util.Map;

public class DigitalIDRepository {

    private final Map<String, DigitalID> database =
            new HashMap<>();

    public void save(DigitalID digitalID) {

        if (digitalID == null) {
            throw new IllegalArgumentException("DigitalID cannot be null");
        }

        database.put(
                digitalID.getIdNumber(),
                digitalID
        );
    }

    public DigitalID findById(String id) {
        return database.get(id);
    }

    public boolean existsById(String id) {
        return database.containsKey(id);
    }
}