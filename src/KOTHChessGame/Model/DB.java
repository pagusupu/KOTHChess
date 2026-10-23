package KOTHChessGame.Model;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {

    private static final String username = "KOTH";
    private static final String password = "chess";
    private static final String url = "jdbc:derby:db; create=true";
    private Statement stmnt;

    Connection con;

    public DB() {
        connect();
    }

    public Connection getConnection() {
        return this.con;
    }

    public void connect() {
        if (this.con == null) {
            try {
                con = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                System.out.println("DB: Error: " + e.getMessage());
            }
        }
    }

    public void close() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("DB: Error: " + e.getMessage());
            }
        }
    }

    public ResultSet queryDB(String sql) {

        Connection con2 = this.con;
        Statement stmnt2 = null;
        ResultSet rs = null;

        try {
            stmnt2 = con2.createStatement();
            rs = stmnt2.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("DB: Error: " + e.getMessage());
        }
        return rs;
    }

    public void createTables() {
        // create moveHistory table, stores the step number, source and target positions
        // as well as if move creates a promotion
        try {
            stmnt = con.createStatement();
            DatabaseMetaData dbmd = con.getMetaData();
            ResultSet rs = dbmd.getTables(null, null, "MOVEHISTORY", null);
            if (!rs.next()) {
                stmnt.executeUpdate("CREATE TABLE MOVEHISTORY (move int, source varchar(2), target varchar(2), promotion varchar(6))");
            } else {
                System.out.println("Table already exists.");
            }
        } catch (SQLException e) {
            System.out.println("DB: Error: " + e.getMessage());
        }
    }

    public void dropTables() {
        try {
            stmnt = con.createStatement();
            stmnt.executeUpdate("DROP TABLE MOVEHISTORY");
        } catch (SQLException e) {
            System.out.println("DB: Error: " + e.getMessage());
        }
    }
}
