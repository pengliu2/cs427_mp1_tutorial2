package edu.ncsu.csc326.coffeemaker.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class InventoryDB {
	
	public static boolean addInventory(String name, int amt) {
		boolean inventoryAdded = false;
		DBConnection db = new DBConnection();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = db.getConnection();
			stmt = conn.prepareStatement("INSERT INTO inventory (name, amt) VALUE (\"" + name + "\"," + amt +")");
			int updated = stmt.executeUpdate();
			if (updated == 0) {
				inventoryAdded = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(conn, stmt);
		}
		return inventoryAdded;
	}

	public static int checkInventory(String name) {
		int result = 0;
		DBConnection db = new DBConnection();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = db.getConnection();
			stmt = conn.prepareStatement("SELECT sum(amt) AS total FROM inventory WHERE name=\""+name+"\"");
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			result = 0; 
		} finally {
			DBConnection.closeConnection(conn, stmt);
		}

		return result;
	}
	
	public static boolean useInventory(String name, int amt) {
		boolean inventoryUpdated = false;
		DBConnection db = new DBConnection();
		Connection conn = null;
		PreparedStatement stmt = null;
		amt = 0 - amt;
		try {
			conn = db.getConnection();
			stmt = conn.prepareStatement("INSERT INTO inventory (name, amt) VALUE (\"" + name + "\"," + amt +")");
			int updated = stmt.executeUpdate();
			if (updated == 0) {
				inventoryUpdated = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(conn, stmt);
		}
		return inventoryUpdated;
	}
}
