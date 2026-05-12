package com.digitalid.logging;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class AuditLogger {

    private static final String LOG_FILE = "audit.log";

    public void log(String action) {

        String entry =
                "[AUDIT] "
                        + LocalDateTime.now()
                        + " | "
                        + action;

        System.out.println(entry);

        try (PrintWriter writer =
                     new PrintWriter(
                             new FileWriter(LOG_FILE, true))) {

            writer.println(entry);

        } catch (IOException e) {

            System.out.println("Failed to write audit log");
        }
    }
}