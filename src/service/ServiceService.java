package service;

import config.DbConfig;
import entity.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceService {

    public List<Service> getAllServices() throws SQLException {

        Connection conn = DbConfig.getConnection();

        List<Service> list = new ArrayList<>();

        String sql = "SELECT * FROM service";

        Statement st = conn.createStatement();

        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {

            Service service = new Service(
                    rs.getInt("id"),
                    rs.getString("description"),
                    rs.getDouble("cost")
            );

            list.add(service);
        }

        rs.close();
        st.close();
        conn.close();

        return list;
    }


    public Service getServiceById(int id) throws SQLException {

        Connection conn = DbConfig.getConnection();

        String sql =
                "SELECT * FROM service WHERE id = ?";

        PreparedStatement ps =
                conn.prepareStatement(sql);

        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        Service service = null;

        if (rs.next()) {

            service = new Service(
                    rs.getInt("id"),
                    rs.getString("description"),
                    rs.getDouble("cost")
            );
        }

        rs.close();
        ps.close();
        conn.close();

        return service;
    }
}