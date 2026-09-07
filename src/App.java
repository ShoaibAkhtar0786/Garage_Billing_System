
import entity.Customer;
import entity.Service;
import entity.Vehicle;
import service.BillingService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args)
            throws SQLException {

        Scanner sc = new Scanner(System.in);

        BillingService service =
                new BillingService();

        while (true) {

            System.out.println();
            System.out.println("========== GARAGE MANAGEMENT SYSTEM ==========");
            System.out.println("1. Add Customer with Vehicle");
            System.out.println("2. Generate Invoice");
            System.out.println("3. Show Invoice");
            System.out.println("4. Exit");
            System.out.println("==============================================");

            System.out.print("Enter your choice: ");

            int ch = sc.nextInt();

            switch (ch) {

                case 1:

                    System.out.println();
                    System.out.println("----- Add Customer -----");

                    System.out.print("Customer Name: ");
                    String name = sc.next();

                    System.out.print("Phone: ");
                    String phone = sc.next();

                    // Add customer
                    Customer customer =
                            new Customer(0, name, phone);

                    service.customerService
                            .addCustomer(customer);

                    // Get customer from database
                    Customer savedCustomer =
                            service.customerService
                                    .getCustomerByPhone(phone);

                    if (savedCustomer == null) {
                        System.out.println(
                                "Customer could not be found."
                        );
                        break;
                    }

                    System.out.println(
                            "Customer added successfully!"
                    );

                    System.out.println(
                            "Customer ID: "
                                    + savedCustomer.getId()
                    );



 // Add vehicle
                    System.out.print(
                            "Enter vehicle number: "
                    );

                    String vehicleNumber =
                            sc.next();

                    System.out.print(
                            "Enter vehicle model: "
                    );

                    String model =
                            sc.next();

                    Vehicle vehicle =
                            new Vehicle(
                                    0,
                                    savedCustomer.getId(),
                                    vehicleNumber,
                                    model
                            );

                    service.vehicleService
                            .addVehicle(vehicle);

                    System.out.println(
                            "Vehicle added successfully!"
                    );

                    break;


                case 2:

                    System.out.println();
                    System.out.println("----- Generate Invoice -----");

                    System.out.print(
                            "Enter Customer ID: "
                    );

                    int cid =
                            sc.nextInt();

                    // Show customer's vehicles
                    List<Vehicle> vehicles =
                            service.vehicleService
                                    .getVehiclesByCustomerId(cid);

                    if (vehicles.isEmpty()) {

                        System.out.println(
                                "No vehicle found for this customer."
                        );

                        break;
                    }

                    System.out.println();
                    System.out.println(
                            "Customer Vehicles:"
                    );

                    for (Vehicle v : vehicles) {

                        System.out.println(
                                "ID: " + v.getId()
                                        + " | Number: "
                                        + v.getVehicleNumber()
                                        + " | Model: "
                                        + v.getModel()
                        );
                    }

                    System.out.print(
                            "Enter Vehicle ID: "
                    );

                    int vid =
                            sc.nextInt();


                    // Show available services

                    System.out.println();
                    System.out.println(
                            "Available Services:"
                    );

                    List<Service> services =
                            service.serviceService
                                    .getAllServices();

                    for (Service s : services) {

                        System.out.println(
                                "ID: " + s.getId()
                                        + " | "
                                        + s.getDescription()
                                        + " | ₹"
                                        + s.getCost()
                        );
                    }


                    System.out.print(
                            "Enter number of services: "
                    );

                    int n =
                            sc.nextInt();

                    List<Integer> serviceIds =
                            new ArrayList<>();

                    for (int i = 0; i < n; i++) {

                        System.out.print(
                                "Enter service ID: "
                        );

                        serviceIds.add(
                                sc.nextInt()
                        );
                    }


                    service.createInvoice(
                            cid,
                            vid,
                            serviceIds
                    );

                    break;


                case 3:

                    System.out.println();
                    System.out.println(
                            "----- All Invoices -----"
                    );

                    service.showAllInvoices();

                    break;


                case 4:

                    System.out.println(
                            "Thank you for using Garage Management System!"
                    );

                    sc.close();

                    System.exit(0);

                    break;


                default:

                    System.out.println(
                            "Invalid choice!");
            }
        }
    }
}