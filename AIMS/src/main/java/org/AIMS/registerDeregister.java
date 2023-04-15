package org.AIMS;

import java.sql.*;

public class registerDeregister {

    public static boolean registerDeregisterInstructor(int op , String username , String id , Double constraint) {
        // JDBC
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DBConnector.getConnection();
            stmt = conn.createStatement();
        } catch (Exception err) {
            err.printStackTrace();
        }

        if(op==1) // register course
        {

            // check for availability from courseCatalog

            try {
                String query = "SELECT * FROM course_catalog_admin WHERE course_id = '"+id+"'";
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if(!resultSet.next())
                {
                    System.out.println("Not available in Course Catalog Provided by Admin");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }

            // check if it is already offered in same sem or not

            try {
                String query = "SELECT * FROM course_offerings WHERE course_id = '"+id+"'";
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if(resultSet.next())
                {
                    System.out.println("This Course in Already offered you Can't it offer again");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }

            // insert now

            try {
                String sql = "INSERT INTO course_offerings (course_id, instructor , cgpa_constraint) values ('"
                        + id + "', '" + username + "' , '" + constraint + "')";
                int result = stmt.executeUpdate(sql);
                if (result == 1)
                {
                    System.out.println("Inserted Succesfully");
                }
                else
                {
                    System.out.println("Insertion Failed");
                    return false;
                }
            } catch (SQLException se) {
                se.printStackTrace();
                System.out.println("Insertion Failed");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Insertion Failed");
                return false;
            }
        }
        else if(op==2)  // deregister course
        {

            // check if it was offered

            try {
                String query = "SELECT * FROM course_offerings WHERE course_id = '"+id+"' AND instructor = '"+username+"'";
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if(!resultSet.next())
                {
                    System.out.println("This Course is not Offered By you , So you Can't Deregister");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }

            try {
                String deleteSql = "DELETE FROM course_offerings WHERE course_id = '"+id+"' AND instructor = '"+username+"'";
                PreparedStatement pstmt = conn.prepareStatement(deleteSql);
                pstmt.executeUpdate();
                System.out.println("Deleted course_offering with course_id: " + id);
            } catch (SQLException e) {
                System.out.println("Error deleting course_offering: " + e.getMessage());
                return false;
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    System.out.println("Error closing database connection: " + e.getMessage());
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean registerDeregisterStudent(int opt , String id , String username) {

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

        // get student sem
        int sem = studentCourse.getsem(username);

        // get student batch
        int batch = studentCourse.getbatch(username);

        double creditLimit = 0;
        if (sem == 1 || sem == 2)
            creditLimit = 18;

        if(opt==1)
        {
            // check credit limit

            if (creditLimit != 18) creditLimit = studentCourse.creditlimit(username, sem); // allowed credit limit in this sem // done

            // Checking current sem total credits earned
            Double credits = (double) 0;
            credits = studentCourse.getcurrentsemCredits(username);

            // current course credit ccc
            Double ccc = (double) 0;
            ccc= studentCourse.getccc(id);


            // checking credit eligibility

            Boolean credit_check = false;
            if (credits + ccc <= creditLimit)
                credit_check = true;
            else
                credit_check = false;

            // prerequisite eligibility
            Boolean requisite_chk = false;
            requisite_chk = studentCourse.requisite_check(id, username);

            // cgpa check
            boolean cg = false;
            if(sem==1) cg=true;
            else cg = studentCourse.cgCheck(username, id , sem);


            // Availability Check for that branch and sem

            boolean available = studentCourse.checkAvailability(username,id);

//            System.out.println(credit_check);
//            System.out.println(requisite_chk);
//            System.out.println(cg);

            if (credit_check && requisite_chk && cg && available) {
                // register now
                try {
                    String sql = "INSERT INTO student_registration (username, course_id, semester, stat) values ('" + username + "','" + id + "', '" + sem + "' , 'running')";
                    int result = stmt.executeUpdate(sql);
                    if (result == 1)
                    {
                        System.out.println("Inserted Succesfully");
                        // update into user details

                        PreparedStatement pstmt = null;

                        try {
                            // Prepare the update statement
                            String sql1 = "UPDATE user_details SET current_sem_credits = current_sem_credits + ? WHERE username='"+username+"'";
                            pstmt = conn.prepareStatement(sql1);

                            // Set the value of the parameter
                            pstmt.setDouble(1, ccc);

                            // Execute the update statement
                            int rowsUpdated = pstmt.executeUpdate();
                            System.out.println(rowsUpdated + " rows updated.");
                            return  true;

                        } catch (SQLException e) {
                            e.printStackTrace();
                            return false;
                        } finally {
                            try {
                                // Close the statement and connection
                                if (pstmt != null) pstmt.close();
                                // if (conn != null) conn.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else {
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
                } finally {
                    try {
                        if (stmt != null) {
                            stmt.close();
                        }
                    } catch (SQLException se2) {
                    }
                }
            } else {
                System.out.println("Not Eligible to register Course");
                return false;
            }
        }
        else if(opt==2)
        {
            // check if it is registered by user or not

            try {
                String query = "SELECT * FROM student_registration WHERE course_id= '"+id+"' AND username='"+username+"'";
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if(!resultSet.next())
                {
                    System.out.println("This is Course is not Registered By you");
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Double ccc = (double) 0;
            ccc= studentCourse.getccc(id);

            try {
                String deleteSql = "DELETE FROM student_registration WHERE course_id = '" + id + "' AND username = '"+username +"' ";
                PreparedStatement pstmt = conn.prepareStatement(deleteSql);
                pstmt.executeUpdate();
                System.out.println("Deleted course_offering with course_id: " + id);

                // update current_course_credit

                PreparedStatement pstmt1 = null;

                try {
                    // Prepare the update statement
                    String sql1 = "UPDATE user_details SET current_sem_credits = current_sem_credits - ? WHERE username='"+username+"'";
                    pstmt1 = conn.prepareStatement(sql1);

                    // Set the value of the parameter
                    pstmt1.setDouble(1, ccc);

                    // Execute the update statement
                    int rowsUpdated = pstmt1.executeUpdate();
                    System.out.println(rowsUpdated + " rows updated.");
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        // Close the statement and connection
                        if (pstmt1 != null) pstmt1.close();
                        // if (conn != null) conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }


            } catch (SQLException e) {
                System.out.println("Error deleting course_offering: " + e.getMessage());
                return false;
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    System.out.println("Error closing database connection: " + e.getMessage());
                    return false;
                }
            }
        }

        return true;
    }

}
