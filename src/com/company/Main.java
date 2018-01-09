package com.company;

import java.io.*;
import java.lang.String;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    private static Scanner sc = new Scanner(System.in);
    private static Vector<String> currentWishlist = new Vector();
    private static String currentWishlistName = "wishlist";
    private static HashMap<String, Vector<String>> wishlistMap = new HashMap();

    public static void main(String[] args) {

        load();

        while (true)
        {
            System.out.print("\nEnter your command: (currently using " + currentWishlistName + ") ");

            String input = sc.next();
            char cmd = input.charAt(0);

            System.out.println();

            switch (cmd) //TODO function cmd map?
            {
                case 'q':
                    break;
                case 'c':
                    choose();
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
                    delete();
                    break;
                default:
                    System.out.println("Unrecognized command!\n");
                    break;
            }

            if (cmd == 'q')
            {
                System.out.print("Done.");
                return;
            }
        }
    }

    private static void load() {

        //Load all txt files in src/lists
        try (DirectoryStream<Path> files = Files.newDirectoryStream(Paths.get("src/lists"))) {
            for (Path path : files)
            {
                try {
                    //System.out.println(path.toString());

                    currentWishlistName = path.toString().substring(10);
                    currentWishlistName = currentWishlistName.substring(0, currentWishlistName.length()-4);

                    //System.out.print(currentWishlistName);

                    InputStream is = new FileInputStream("src/lists/" + currentWishlistName + ".txt");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                    String line = reader.readLine();
                    //StringBuilder sb = new StringBuilder();

                    while (line != null)
                    {
                        currentWishlist.add(line);
                        line = reader.readLine();
                    }

                    System.out.println(currentWishlistName + " loaded.");
                    wishlistMap.put(currentWishlistName, currentWishlist);
                    currentWishlist = new Vector();

                } catch (FileNotFoundException e) {
                    System.out.println("File not found!\n");
                } catch (IOException e) {
                    System.out.println("IO exception!\n");
                }
            }
        } catch (IOException e) {
            System.out.println("IO exception!\n");
        }
    }

    private static void choose() {

        System.out.print("Enter the name of the wishlist you want to use: ");

        String input = sc.next();
        if (wishlistMap.containsKey(input))
        {
            save();
            currentWishlistName = input;
            currentWishlist = wishlistMap.get(input); //TODO s2x

            System.out.println("Loaded " + currentWishlistName + ".");        }
        else
        {
            System.out.print("Wishlist not found, create a new list? (y/n): ");

            String secondInput = sc.next();
            System.out.println();

            if (secondInput.equals("y") || secondInput.equals("yes"))
            {
                save();
                currentWishlistName = input;
                currentWishlist = new Vector();
                wishlistMap.put(input, currentWishlist);

                System.out.println("Wishlist created.\n");
            }
            else System.out.println("Nothing selected.");
        }
    }

    private static void save() {

        //TODO file locks?

        System.out.println("Saving " + currentWishlistName + ".");

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/lists/" + currentWishlistName + ".txt"));

            for (int i = 0; i < currentWishlist.size(); ++i)
            {
                writer.write(currentWishlist.elementAt(i));
                writer.newLine();
            }

            writer.close();

        } catch (IOException e) {
            System.out.println("IO exception!\n");
        }
    }

    private static void view() {

        System.out.println("Viewing " + currentWishlistName + ": ");

        for (int i = 0; i < currentWishlist.size(); ++i)
        {
            System.out.println(currentWishlist.elementAt(i));
        }
    }

    private static void add() {

        System.out.print("Enter your item: ");
        String input = sc.next();

        currentWishlist.add(input);

        System.out.println(input + " added.");
    }

    private static void delete() { //TODO only clears current wishlist, nothing on disk

        System.out.println("Deleting " + currentWishlistName + ".");
        currentWishlist.clear();
    }
}
