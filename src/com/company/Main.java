package com.company;

import java.io.*;
import java.lang.String;
import java.util.*;

public class Main {

    private static Scanner sc = new Scanner(System.in);
    private static Vector<String> wishlist = new Vector();

    public static void main(String[] args) {

        load();

        while (true)
        {
            System.out.println("Enter your command");
            String input = sc.next();

            char cmd = input.charAt(0);

            switch (cmd)
            {
                case 'q':
                    break;
                case 's':
                    save();
                    break;
                case 'v':
                    view();
                    break;
                case 'a':
                    add();
                    break;
                case 'd':
                    break;
                default:
                    break; //TODO throw er
            }

            if (cmd == 'q') return;
        }
    }

    private static void load() {

        try {
            InputStream is = new FileInputStream("wishlist.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line = reader.readLine();
            //StringBuilder sb = new StringBuilder();

            while(line != null)
            {
                wishlist.add(line);
                line = reader.readLine();
            }

        }
        catch(FileNotFoundException e) {
            //Err
        }
        catch(IOException e) {
            //Err
        }
    }

    private static void save() {

        //TODO file locks?

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("wishlist.txt"));

            for (int i = 0; i < wishlist.size(); ++i) {
                writer.write(wishlist.elementAt(i));
                writer.newLine();
            }

            writer.close();
        }
        catch (IOException e) {
            //Err
        }
    }

    private static void view() {

        for (int i = 0; i < wishlist.size(); ++i)
        {
            System.out.println(wishlist.elementAt(i));
        }
    }

    private static void add() {

        System.out.println("Enter your item");
        String input = sc.next();

        wishlist.add(input);
    }
}

