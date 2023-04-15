package org.AIMS;

import java.sql.*;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class cgpa {
    public static double flow(String username, int sem) {

        // JDBC
        Connection conn = null;
        Statement stmt = null;

        try {
            conn=DBConnector.getConnection();
            stmt = conn.createStatement();
        }catch (Exception err)
        {
            err.printStackTrace();
        }

        // incorrect username

        try {
            String query = "SELECT * FROM grades WHERE username = '"+username+"'";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if(!resultSet.next())
            {
                System.out.println("Invalid User or Grades Not assigned Yet");
                return -1.0;
            }
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed Check");
        }



        if(sem==1) return 0.0;

        // getting student cgpa

        Double cgpa=(double) 0;
        Double allcredits=(double) 0;

        // System.out.println("111111111111111");

        for(int i=1;i<sem;i++)
        {
            double sum=0;
            double total_credits=0;
            double sgpa=0;

            try {
                String query = "SELECT grades.grade, course_catalog_admin.credit FROM student_registration INNER JOIN course_catalog_admin ON student_registration.course_id = course_catalog_admin.course_id INNER JOIN grades ON student_registration.course_id = grades.course_id AND grades.username = student_registration.username WHERE student_registration.username = '"+username+"' AND student_registration.semester='"+i+"'";
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                // System.out.println("22222222222222");
                // System.out.println(cgpa);

                int ck=0;

                while (resultSet.next()) {
                    ck=1;
                    String grade = resultSet.getString(1);
                    Double credit = resultSet.getDouble(2);

                    total_credits=total_credits+credit;
//                    System.out.println(total_credits);

                    if(grade.equals("A")) sum=sum+(credit*10);
                    if(grade.equals("A-")) sum=sum+(credit*9);
                    if(grade.equals("B")) sum=sum+(credit*8);
                    if(grade.equals("B-")) sum=sum+(credit*7);
                    if(grade.equals("C")) sum=sum+(credit*6);
                    if(grade.equals("C-")) sum=sum+(credit*5);
                    if(grade.equals("D")) sum=sum+(credit*4);
                    if(grade.equals("E")) sum=sum+(credit*0);

                }
                if(ck==0) break;

                sgpa = sum/total_credits;
                allcredits = allcredits + total_credits;
                
                // System.out.println("sgpa");
                // System.out.println(sgpa);
                resultSet.close();
                statement.close();
//                conn.close();
    
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed Updation");
            }

            cgpa = cgpa + (sgpa*total_credits);

        }

        cgpa = cgpa/allcredits;
        // System.out.println("cgpa:");

        return cgpa;

    }
}