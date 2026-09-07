


package service;

import config.DbConfig;
import entity.Invoice;
import entity.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceService {

    public int addInvoice(Invoice invoice, List<Integer> serviceIds)
            throws SQLException {

        Connection conn = DbConfig.getConnection();

        try {

            String invoiceSql =
                    "INSERT INTO invoices(customer_id, vehicle_id) VALUES (?, ?)";

            PreparedStatement ps =
                    conn.prepareStatement(
                            invoiceSql,
                            Statement.RETURN_GENERATED_KEYS
                    );
//            Statement.RETURN_GENERATED_KEYS is used when you insert a row into a database
//            and want to get back the ID that the database automatically generated.-an auto-increment id.


            ps.setInt(1, invoice.getCustomerId());
            ps.setInt(2, invoice.getVehicleId());

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();

            int invoiceId = 0;

            if (keys.next()) {
                invoiceId = keys.getInt(1);
            }

            keys.close();
            ps.close();


            // Add services to invoice_services table

            String serviceSql =
                    "INSERT INTO invoice_services(invoice_id, service_id) " +
                            "VALUES (?, ?)";

            PreparedStatement servicePs =
                    conn.prepareStatement(serviceSql);

            for (int serviceId : serviceIds) {

                servicePs.setInt(1, invoiceId);
                servicePs.setInt(2, serviceId);

                servicePs.addBatch();
            }

            servicePs.executeBatch();

            servicePs.close();
            conn.close();

            return invoiceId;

        } catch (SQLException e) {

            conn.close();
            throw e;
        }
    }


    public List<Invoice> getAllInvoices() throws SQLException {

        Connection conn = DbConfig.getConnection();

        List<Invoice> list = new ArrayList<>();

        String sql = "SELECT * FROM invoices";

        Statement st = conn.createStatement();

        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {

            Invoice invoice = new Invoice(
                    rs.getInt("id"),
                    rs.getInt("customer_id"),
                    rs.getInt("vehicle_id")
            );

            list.add(invoice);
        }

        rs.close();
        st.close();
        conn.close();

        return list;
    }


    // Get all services belonging to one invoice
    public List<Service> getServicesByInvoiceId(int invoiceId)
            throws SQLException {

        Connection conn = DbConfig.getConnection();

        List<Service> list = new ArrayList<>();

        String sql =
                "SELECT s.id, s.description, s.cost " +
                        "FROM service s " +
                        "JOIN invoice_services isv " +
                        "ON s.id = isv.service_id " +
                        "WHERE isv.invoice_id = ?";

        PreparedStatement ps =
                conn.prepareStatement(sql);

        ps.setInt(1, invoiceId);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            Service service = new Service(
                    rs.getInt("id"),
                    rs.getString("description"),
                    rs.getDouble("cost")
            );

            list.add(service);
        }

        rs.close();
        ps.close();
        conn.close();

        return list;
    }
}