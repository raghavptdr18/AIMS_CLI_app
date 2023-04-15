package org.AIMS;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class updateGrade {
    private static final String URL = "jdbc:postgresql://localhost:5432/aims";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    public static boolean flow() {
        Connection conn = null;
        PreparedStatement pstmt = null;
//        BufferedReader br = null;

        try {
            // Step 1: Load and register JDBC driver
            Class.forName("org.postgresql.Driver");

            // Step 2: Establish connection to database
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            if (conn != null) {
                System.out.println("connection OK");
            } else {
                System.out.println("connection FAILED");
            }


            // Step 3: Create a prepared statement
            String sql = "INSERT INTO grades (grade , course_id, username ) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);

            // Step 4: Read the .csv file and update grades
            try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/f.csv"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    String grade = data[0];
                    String course_id = data[1];
                    String username = data[2];
                    pstmt.setString(1, grade);
                    pstmt.setString(2, course_id);
                    pstmt.setString(3, username);
                    int rowsUpdated = pstmt.executeUpdate();
                    if (rowsUpdated == 0) {
                        System.out.println("No rows were updated for course_id = " + course_id + " and student_id = " + username);
                    } else {
                        System.out.println(rowsUpdated + " rows were updated for course_id = " + course_id + " and student_id = " + username);
                    }
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            // Step 5: Close database connections and resources
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }
}
