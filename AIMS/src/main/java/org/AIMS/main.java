package org.AIMS;

import java.io.IOException;
import java.util.Scanner;
import org.junit.Test;

public class main {

    public static void main(String[] args) throws IOException {

        String className = main.class.getName();
        System.out.println(className);

        Scanner in = new Scanner(System.in);
        int option;

        System.out.println("1. Student");
        System.out.println("2. Faculty");
        System.out.println("3. Academics Office");
        System.out.print("Enter an option: ");

        option = in.nextInt();

        if (option == 1)
            student.flow();
        else if (option == 3) {
            admin.flow();
        } else if(option==2)
        {
            instructor.flow();
        }
        else 
        {
            System.out.println("Enter correct options");
            main.main(new String[0]);
        }

    }
}