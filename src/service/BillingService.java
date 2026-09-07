


package service;

import entity.Customer;
import entity.Invoice;
import entity.Service;
import entity.Vehicle;

import java.sql.SQLException;
import java.util.List;

public class BillingService {

    public CustomerService customerService =
            new CustomerService();

    public VehicleService vehicleService =
            new VehicleService();

    public InvoiceService invoiceService =
            new InvoiceService();

    public ServiceService serviceService =
            new ServiceService();


    // ================================
    // CREATE INVOICE
    // ================================

    public void createInvoice(
            int customerId,
            int vehicleId,
            List<Integer> serviceIds) throws SQLException {

        // Create invoice object
        Invoice invoice =
                new Invoice(0, customerId, vehicleId);

        // Save invoice and services
        int invoiceId =
                invoiceService.addInvoice(
                        invoice,
                        serviceIds
                );

        System.out.println();
        System.out.println(
                "=========================================="
        );

        System.out.println(
                "     Invoice generated successfully!"
        );

        System.out.println(
                "Invoice ID: " + invoiceId
        );

        System.out.println(
                "=========================================="
        );
    }


    // ================================
    // SHOW ALL INVOICES
    // ================================

    public void showAllInvoices() throws SQLException {

        // Get all invoices
        List<Invoice> invoices =
                invoiceService.getAllInvoices();


        // Check if there are no invoices
        if (invoices.isEmpty()) {

            System.out.println();
            System.out.println("No invoices found.");

            return;
        }


        // Loop through all invoices
        for (Invoice invoice : invoices) {

            System.out.println();
            System.out.println(
                    "=========================================="
            );

            System.out.println(
                    "              GARAGE INVOICE"
            );

            System.out.println(
                    "=========================================="
            );


            // -------------------------
            // Invoice ID
            // -------------------------

            System.out.println(
                    "Invoice ID     : "
                            + invoice.getId()
            );


            // -------------------------
            // Get Customer
            // -------------------------

            Customer customer =
                    customerService.getCustomerById(
                            invoice.getCustomerId()
                    );


            // -------------------------
            // Get Vehicle
            // -------------------------

            Vehicle vehicle =
                    vehicleService.getVehicleById(
                            invoice.getVehicleId()
                    );


            // -------------------------
            // Customer Details
            // -------------------------

            if (customer != null) {

                System.out.println(
                        "Customer Name  : "
                                + customer.getName()
                );

                System.out.println(
                        "Phone          : "
                                + customer.getPhone()
                );

            } else {

                System.out.println(
                        "Customer       : Not Found"
                );
            }


            // -------------------------
            // Vehicle Details
            // -------------------------

            if (vehicle != null) {

                System.out.println(
                        "Vehicle Number : "
                                + vehicle.getVehicleNumber()
                );

                System.out.println(
                        "Vehicle Model  : "
                                + vehicle.getModel()
                );

            } else {

                System.out.println(
                        "Vehicle        : Not Found"
                );
            }


            // -------------------------
            // Services
            // -------------------------

            System.out.println(
                    "------------------------------------------"
            );

            System.out.println("Services:");

            System.out.println(
                    "------------------------------------------"
            );


            // Get services for this invoice
            List<Service> services =
                    invoiceService.getServicesByInvoiceId(
                            invoice.getId()
                    );


            double total = 0;


            // Display every service
            for (Service service : services) {

                System.out.printf(
                        "%-25s ₹%.2f%n",
                        service.getDescription(),
                        service.getCost()
                );

                // Add service cost to total
                total += service.getCost();
            }


            // -------------------------
            // Total
            // -------------------------

            System.out.println(
                    "------------------------------------------"
            );

            System.out.printf(
                    "TOTAL          : ₹%.2f%n",
                    total
            );


            System.out.println(
                    "=========================================="
            );
        }
    }
}