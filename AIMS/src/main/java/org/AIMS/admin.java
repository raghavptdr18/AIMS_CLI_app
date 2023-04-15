package org.AIMS;

import java.io.IOException;
import java.sql.*;
import java.util.Scanner;
import java.sql.Connection;

public class admin {
    public static void flow() throws IOException {

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
        Scanner in = new Scanner(System.in);
        int option;

        System.out.println("1. Login");
        System.out.println("PRESS 0 FOR GO BACK");
        System.out.print("Enter an option: ");
        option = in.nextInt();

        if (option == 1) {
            System.out.print("Enter username: ");
            String username = in.next();
            System.out.print("Enter password: ");
            String password = in.next();
            if (login.loginadmin(username, password)) {
                System.out.println("Login successful!");
                afterLogin(username);
                System.out.println("PRESS 0 FOR GO BACK");
                int back = in.nextInt();
                if (back == 0) admin.flow();

            } else {
                System.out.println("Login failed.");
                System.out.println("PRESS 0 FOR GO BACK");
                int back = in.nextInt();
                if (back == 0) student.flow();
            }
        }
        else if (option == 0) main.main(new String[0]);

    }

    public static boolean adminFlow(int option , int opt) throws IOException {

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


        if(option==1)
        {

            try {
                String Query = "UPDATE flags SET sem = ? ";
                PreparedStatement pstmt = conn.prepareStatement(Query);
                pstmt.setInt(1, opt);
                int rowsAffected = pstmt.executeUpdate();
                System.out.println("Updated Succesfully");

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed Updation");
            }

            if(opt==0)
            {
                // delete entries in course offerings

                try {
                    String deleteSql = "DELETE FROM course_offerings";
                    PreparedStatement pstmt = conn.prepareStatement(deleteSql);
                    pstmt.executeUpdate();
                    System.out.println("Deleted course_offering");
                } catch (SQLException e) {
                    System.out.println("Error deleting course_offering: " + e.getMessage());
                    return false;
                }

                // running status and grade check

                try {
                    String Query = "UPDATE student_registration SET stat = 'completed' FROM grades WHERE student_registration.course_id = grades.course_id AND student_registration.stat = 'running' AND grades.grade <> 'E';";
                    PreparedStatement pstmt = conn.prepareStatement(Query);
                    int rowsAffected = pstmt.executeUpdate();
                    System.out.println("Updated Succesfully");

                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Failed Updation");
                    return false;
                }

                // update sem of user in user_detail

                try {
                    String Query = "UPDATE user_details SET semester = semester + ?";
                    PreparedStatement pstmt = conn.prepareStatement(Query);
                    pstmt.setInt(1, 1);
                    int rowsAffected = pstmt.executeUpdate();
                    System.out.println("Updated Succesfully");

                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Failed Updation");
                    return false;
                }

                // update Current Sem credit to 0

                try {
                    String Query = "UPDATE user_details SET current_sem_credits = ?";
                    PreparedStatement pstmt = conn.prepareStatement(Query);
                    pstmt.setDouble(1, 0.0);
                    int rowsAffected = pstmt.executeUpdate();
                    System.out.println("Updated Succesfully");

                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Failed Updation");
                    return false;
                }

            }
            else return true;
            return true;
        }
        else if(option==2)
        {

            try {
                String Query = "UPDATE flags SET course_registration_instructor = ? ";
                PreparedStatement pstmt = conn.prepareStatement(Query);
                pstmt.setInt(1, opt);
                int rowsAffected = pstmt.executeUpdate();
                System.out.println("Updated Succesfully");

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed Updation");
                return false;
            }
            return true;
        }
        else if(option==3)
        {

            try {
                String Query = "UPDATE flags SET course_registration_student = ? ";
                PreparedStatement pstmt = conn.prepareStatement(Query);
                pstmt.setInt(1, opt);
                int rowsAffected = pstmt.executeUpdate();
                System.out.println("Updated Succesfully");

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed Updation");
                return false;
            }
            return true;
        }
        else if(option==4)
        {

            try {
                String Query = "UPDATE flags SET upload_grade = ? ";
                PreparedStatement pstmt = conn.prepareStatement(Query);
                pstmt.setInt(1, opt);
                int rowsAffected = pstmt.executeUpdate();
                System.out.println("Updated Succesfully");

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed Updation");
                return false;
            }
            return true;
        }
        else
        {
            admin.flow();
        }
        return true;
    }

    public static boolean adm(int option ,String cid,String cname,int batch,String dep,int sem,String ce,Double credit,String preq) throws IOException {

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

        Scanner in = new Scanner(System.in);


        if (option == 1) {

                    try {
                        String sql = "INSERT INTO course_catalog_admin (course_id,course_name,batch,department,semester,ce,credit,prerequisites) values ('" + cid+ "','" + cname + "', '" + batch + "', '" + dep + "', '" + sem + "', '" + ce + "', '" + credit + "', '" + preq + "')";
                        int result = stmt.executeUpdate(sql);
                        if (result == 1) {
                            System.out.println("Inserted Succesfully");
                            return true;
                        }
                        else{
                            System.out.println("Insertion Failed");
                            return false;
                        }

                    } catch (SQLException se) {
                        se.printStackTrace();
                        System.out.println("Insertion Failed");
                        return false;
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Insertion Failed");
                        return false;
                    }
        } else if (option == 2) {
            try {
                String query = "SELECT * FROM course_catalog_admin";
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                        System.out.print(resultSet.getString(i) + " ");
                    }
                    System.out.println();
                    conn.close();
                }
                return  true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        if (option == 0) {
            main.main(new String[0]);
            return true;
        }
        return true;
    }

    public static boolean afterLogin(String username) throws IOException {

        Connection conn = null;
        Statement stmt = null;

        try {
            conn=DBConnector.getConnection();
            stmt = conn.createStatement();
        }catch (Exception err)
        {
            err.printStackTrace();
        }

         Scanner in = new Scanner(System.in);
        int acadsoption;

        System.out.println("1. Edit / View course list");
        System.out.println("2. View Grades");
        System.out.println("3. Generate transcript");
        System.out.println("4. Allow and disallow controls");
        System.out.println("Press 0 for LogOut");
        System.out.print("Enter an option: ");
        acadsoption = in.nextInt();

        if (acadsoption == 1) {
            int opt;
            System.out.println("1. Enter courses Details");
            System.out.println("2. List of all existing Courses");
            System.out.println("PRESS 0 FOR GO BACK");
            System.out.println("Enter a Option");
            opt = in.nextInt();

            if(opt==1)
            {
                boolean x = true;

                while (x) {
                    int opt11 = 1;
                    System.out.println("To stop Entering Courses Press 0 Otherwise Press 1");
                    opt11 = in.nextInt();
                    if (opt11 == 0) {
                        x = false;
                    } else {

                        System.out.println("Enter course id");
                        String cid = in.next();
                        System.out.println("Enter course name");
                        String cname = in.next();
                        System.out.println("Enter Batch");
                        int batch = in.nextInt();
                        System.out.println("Enter Department");
                        String dep = in.next();
                        System.out.println("Enter sem");
                        int sem = in.nextInt();
                        System.out.println("Enter c = Core / e = Elective");
                        String ce = in.next();
                        System.out.println("Enter credits");
                        Double credit = (double) in.nextInt();
                        System.out.println("Enter course prerequisites");
                        String preq = in.next();
                        adm(opt,cid,cname,batch,dep,sem,ce,credit,preq);
                    }
                }
                System.out.println("PRESS 0 FOR GO BACK");
                int back = in.nextInt();
                if (back == 0) afterLogin(username);
            }
            else if(opt==2)
            {
                adm(opt,"0","0",0,"0",0,"0",0.0,"0");
                System.out.println("PRESS 0 FOR GO BACK");
                int back = in.nextInt();
                if (back == 0) afterLogin(username);
            }
            else if(opt==0)
            {
                admin.afterLogin(username);
            }
        }
        else if (acadsoption == 2) {
            grades.allgradesadmin();
            System.out.println("PRESS 0 FOR GO BACK");
            int back = in.nextInt();
            if (back == 0) afterLogin(username);
        }
        else if (acadsoption == 3) {

            transcript.flow();
            System.out.println("PRESS 0 FOR GO BACK");
            int back = in.nextInt();
            if (back == 0) afterLogin(username);
        }
        else if(acadsoption == 4)
        {
            int opt=-1;

            System.out.println("1. sem start=1 end = 0");
            System.out.println("2. course_registration_instructor start=1 end = 0");
            System.out.println("3. course_registration_student start=1 end = 0 Office");
            System.out.println("4. upload_grade start=1 end = 0 Office");
            System.out.println("Press 0 for back");

            System.out.print("Enter an option: ");

            opt = in.nextInt();
            int opt11 =-1;
            if(opt==1)
            {
                System.out.println("sem start=1 end = 0");
                opt11 = in.nextInt();
            }
            else if(opt==2)
            {
                System.out.println("course_registration_instructor start=1 end = 0");
                opt11 = in.nextInt();
            }
            else if(opt==3)
            {
                System.out.println("course_registration_student start=1 end = 0 Office");
                opt11 = in.nextInt();
            }
            else if(opt==4)
            {
                System.out.println("upload_grade start=1 end = 0 Office");
                opt11 = in.nextInt();
            }
            else if(opt==0)
            {
                afterLogin(username);
            }

            adminFlow(opt,opt11);

            System.out.println("PRESS 0 FOR GO BACK");
            int back = in.nextInt();
            if (back == 0) afterLogin(username);
        }
        else
        {
            main.main(new String[0]);
        }

        return true;
    }

}