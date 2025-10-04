package com.seboot.portpocket.db;

import java.sql.Connection;
import java.sql.Statement;

public class Migration {
    public static void init() {
        try(Connection conn = Database.getConnection();
            Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS active (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    type TEXT NOT NULL,
                    price REAL NOT NULL,
                    quantity INTEGER NOT NULL
                )
            """);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
