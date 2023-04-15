package org.AIMS;

import java.io.BufferedWriter;
import java.sql.*;
import java.util.Scanner;
import java.sql.Connection;
import java.io.FileWriter;
import java.io.IOException;

public class transcript {

    static admin admin = new admin();
    static DBConnector DBConnector = new DBConnector();


    public static boolean flow() throws IOException {

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
        String option;

        System.out.println("Enter username ");
        System.out.println("PRESS 0 FOR GO BACK");
        System.out.print("Enter an option: ");
        option = in.nextLine();

        if(option.equals("0")) admin.flow();
        else
        {
            reciept(option);
        }
        return true;
    }

    public static boolean reciept(String option) throws IOException {

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


        String filename = "src/main/resources/"+option+"_transcript.txt";


            String username = option;
            String responseQuery="";

            String details = "SELECT * FROM user_details WHERE username = '"+username+"'";
            String grades = "SELECT * FROM grades WHERE username = '"+username+"'";

            try {
                try {
                    stmt= conn.createStatement();
                    ResultSet rs=stmt.executeQuery(details);
                    ResultSetMetaData rsmd;
                    rsmd=rs.getMetaData();
                    int columnsNumber = rsmd.getColumnCount();
                    while (rs.next()) {
                        for (int i = 2; i <= columnsNumber; i++) {

                            if (i == 2)
                                responseQuery += "username ---> ";
                            if (i == 3)
                                responseQuery += "      semester ---> ";
                            if (i == 4)
                                responseQuery += "      joining_date ---> ";
                            if (i == 5)
                                responseQuery += "      batch ---> ";
                            if (i == 6)
                                responseQuery += "      contact ---> ";
                            if (i == 8)
                                responseQuery += "      current_sem_credits ---> ";
                            if (i==9)
                                responseQuery += "      department ---> ";

                            String columnValue = rs.getString(i);
                            responseQuery += columnValue+" ";
//                            System.out.println(responseQuery);
                        }
                        responseQuery+="\n";
                    }

                    // write separator line
                    responseQuery += "-----------------------------------\n";
                    // grades query
                    rs=stmt.executeQuery(grades);
                    rsmd=rs.getMetaData();
                    columnsNumber = rsmd.getColumnCount();
                    while (rs.next()) {
                        for (int i = 2; i <= columnsNumber; i++) {
                            if (i == 2)
                                responseQuery += "username ---> ";
                            if (i == 3)
                                responseQuery += "      course_id ---> ";
                            if (i == 4)
                                responseQuery += "      grade ---> ";
                            String columnValue = rs.getString(i);
                            responseQuery += columnValue+" ";
                            System.out.println(responseQuery);
                        }
                        responseQuery+="\n";
                    }

                    Boolean isGraduated = student.graduationCheck(username);
                    responseQuery += "      Graduation Status :  ---> "+isGraduated;
                    responseQuery+="\n";


                    int sem = student.getsem(username);
                    Double cgpa1 = cgpa.flow(username,sem);
                    String s = Double.toString(cgpa1);
                    responseQuery += "      Current CGPA is  ---> "+s;
                    responseQuery+="\n";

                } catch (SQLException e) {
                    System.out.println(e);
                    return false;
                }

                // write responseQuery to file
                BufferedWriter out = new BufferedWriter(new FileWriter("src/main/resources/"+username+"_transcript.txt", true));
                out.write(responseQuery);
                out.close();
            }
            // Catch block to handle the exceptions
            catch (IOException e) {

                // Display message when exception occurs
                System.out.println("exception occurred" + e);
                return false;
            }

        return true;
    }

}
