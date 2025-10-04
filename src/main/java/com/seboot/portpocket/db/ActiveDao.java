package com.seboot.portpocket.db;

import com.seboot.portpocket.model.Active;
import com.seboot.portpocket.model.ActiveType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActiveDao {
    String sql = "INSERT INTO active(name, type, price, quantity) VALUES(?, ?, ?, ?)";

    public void insert(Active active) {
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, active.name());
            pstmt.setString(2, active.type().name());
            pstmt.setDouble(3, active.currentPrice());
            pstmt.setInt(4, active.quantity());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Active active) {
        String sql = "DELETE FROM active WHERE name = ? AND type = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, active.name());
            stmt.setString(2, active.type().name());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Active> getAll() {
        List<Active> list = new ArrayList<>();
        String sql = "SELECT * FROM active";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Active active = new Active(
                        rs.getString("name"),
                        ActiveType.valueOf(rs.getString("type")),
                        rs.getDouble("price"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                );
                list.add(active);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
