package com.digitalid.cli;

import com.digitalid.model.OrganisationType;
import com.digitalid.service.DigitalIDService;
import com.digitalid.service.VerificationService;

import java.util.Scanner;

public class CLIHandler {

    private final DigitalIDService digitalIDService =
            new DigitalIDService();

    private final VerificationService verificationService =
            new VerificationService(digitalIDService);

    private final Scanner scanner = new Scanner(System.in);

    public void start() {

        System.out.println("=== DIGITAL ID SYSTEM ===");

        while (true) {

            displayMenu();

            int choice = readIntInput();

            switch (choice) {

                case 1 -> createDigitalID();

                case 2 -> updateAddress();

                case 3 -> suspendDigitalID();

                case 4 -> revokeDigitalID();

                case 5 -> verifyIdentity();

                case 6 -> viewIdentity();

                case 7 -> exitSystem();

                default -> System.out.println("Invalid option");
            }
        }
    }

    private void displayMenu() {

        System.out.println("""
                
                1. Create Digital ID
                2. Update Address
                3. Suspend Digital ID
                4. Revoke Digital ID
                5. Verify Identity
                6. View Identity
                7. Exit
                """);
    }

    private int readIntInput() {

        try {
            return Integer.parseInt(scanner.nextLine());

        } catch (NumberFormatException e) {

            return -1;
        }
    }

    private void createDigitalID() {

        System.out.print("ID: ");
        String id = scanner.nextLine();

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("DOB: ");
        String dob = scanner.nextLine();

        System.out.print("Address: ");
        String address = scanner.nextLine();

        digitalIDService.createID(id, name, dob, address);

        System.out.println("Digital ID created successfully");
    }

    private void updateAddress() {

        System.out.print("ID: ");
        String id = scanner.nextLine();

        System.out.print("New Address: ");
        String address = scanner.nextLine();

        boolean updated =
                digitalIDService.updateAddress(id, address);

        System.out.println(
                updated
                        ? "Address updated"
                        : "Update failed"
        );
    }

    private void suspendDigitalID() {

        System.out.print("ID: ");
        String id = scanner.nextLine();

        boolean suspended =
                digitalIDService.suspendID(id);

        System.out.println(
                suspended
                        ? "Digital ID suspended"
                        : "Suspension failed"
        );
    }

    private void revokeDigitalID() {

        System.out.print("ID: ");
        String id = scanner.nextLine();

        boolean revoked =
                digitalIDService.revokeID(id);

        System.out.println(
                revoked
                        ? "Digital ID revoked"
                        : "Revocation failed"
        );
    }

    private void verifyIdentity() {

        System.out.print("ID: ");
        String id = scanner.nextLine();

        OrganisationType organisationType =
                selectOrganisation();

        String result =
                verificationService.verifyIdentity(
                        id,
                        organisationType
                );

        System.out.println(result);
    }

    private OrganisationType selectOrganisation() {

        System.out.println("""
                
                Select Organisation:
                
                1. Bank
                2. Employer
                3. Tax Authority
                4. Driving Licence Authority
                5. Central Authority
                """);

        int choice = readIntInput();

        return switch (choice) {

            case 1 -> OrganisationType.BANK;

            case 2 -> OrganisationType.EMPLOYER;

            case 3 -> OrganisationType.TAX_AUTHORITY;

            case 4 -> OrganisationType.DRIVING_LICENCE_AUTHORITY;

            default -> OrganisationType.CENTRAL_AUTHORITY;
        };
    }

    private void viewIdentity() {

        System.out.print("ID: ");
        String id = scanner.nextLine();

        System.out.println(
                verificationService.verifyIdentity(
                        id,
                        OrganisationType.CENTRAL_AUTHORITY
                )
        );
    }

    private void exitSystem() {

        System.out.println("Exiting system...");
        System.exit(0);
    }
}