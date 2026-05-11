package com.digitalid.cli;

import com.digitalid.service.DigitalIDService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        DigitalIDService service = new DigitalIDService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digital ID System Started");

        while (true) {
            System.out.println("\n1. Create ID");
            System.out.println("2. Revoke ID");
            System.out.println("3. Check Validity");
            System.out.println("4. Exit");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {

                case 1 -> {
                    System.out.print("ID: ");
                    String id = scanner.nextLine();

                    System.out.print("Name: ");
                    String name = scanner.nextLine();

                    System.out.print("DOB: ");
                    String dob = scanner.nextLine();

                    System.out.print("Address: ");
                    String address = scanner.nextLine();

                    service.createID(id, name, dob, address);
                    System.out.println("ID created");
                }

                case 2 -> {
                    System.out.print("ID: ");
                    String id = scanner.nextLine();

                    System.out.println(
                            service.revokeID(id) ? "Revoked" : "Not found"
                    );
                }

                case 3 -> {
                    System.out.print("ID: ");
                    String id = scanner.nextLine();

                    System.out.println(
                            service.isValid(id) ? "VALID" : "INVALID"
                    );
                }

                case 4 -> {
                    System.out.println("Exiting...");
                    return;
                }
            }
        }
    }
}
