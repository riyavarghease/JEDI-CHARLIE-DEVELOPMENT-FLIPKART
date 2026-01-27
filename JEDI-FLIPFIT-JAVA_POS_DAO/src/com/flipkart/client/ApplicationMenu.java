package com.flipkart.client;

import com.flipkart.business.CustomerService;
import com.flipkart.business.GymOwnerService;
import com.flipkart.business.AdminService;

import java.util.Scanner;

public class ApplicationMenu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to FlipFit Application");

        while (true) {
            System.out.println("\n1. Login");
            System.out.println("2. Register User");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    login(scanner);
                    break;
                case 2:
                    registerUserFlow(scanner);
                    break;
                case 3:
                    System.out.println("Exiting Application...");
                    System.exit(0);
                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }

    private static void registerUserFlow(Scanner scanner) {
        System.out.println("Select Role to Register:");
        System.out.println("1. Admin\n2. Customer\n3. Gym Owner");
        int roleChoice = scanner.nextInt();

        System.out.println("Enter Username:");
        String name = scanner.next();
        System.out.println("Enter email:");
        String email = scanner.next();
        System.out.println("Enter phoneNumber:");
        String phoneNumber = scanner.next();
        System.out.println("Enter Password:");
        String password = scanner.next();

        if (roleChoice == 1) {
            new AdminService().registerAdmin(name, email, phoneNumber, password);
        } else if (roleChoice == 2) {
            new CustomerService().registerCustomer(name, email, phoneNumber, password);
        } else if (roleChoice == 3) {
            new GymOwnerService().registerOwner(name, email, phoneNumber, password);
        } else {
            System.out.println("Invalid Role Selection!");
        }
    }

    private static void login(Scanner scanner) {
        System.out.println("Enter Username:");
        String user = scanner.next();
        System.out.println("Enter Password:");
        String pass = scanner.next();
        System.out.println("Enter Role (1: Admin, 2: Customer, 3: Owner):");
        int role = scanner.nextInt();

        if (role == 1) {
            new AdminMenu().showMenu();
        } else if (role == 2) {
            new CustomerMenu().showMenu(user);
        } else if (role == 3) {
            new GymOwnerMenu().showMenu(user);
        }
    }
}