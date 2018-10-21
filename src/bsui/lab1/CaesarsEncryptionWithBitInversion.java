package bsui.lab1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class CaesarsEncryptionWithBitInversion {

    public static void doTask() throws IOException {
        encrypt();
        decrypt();
        original();
    }

    private static void original() throws IOException {
        char text;
        int temp;

        BufferedReader file = new BufferedReader(new FileReader("test.txt"));
        System.out.println("\noriginal:");
        while ((temp = file.read()) != -1) {
            text = (char) (temp);
            System.out.print(text);
        }
    }

    private static void decrypt() throws IOException {
        char text;
        int temp;

        PrintWriter save = new PrintWriter("decrypted.txt");
        BufferedReader file = new BufferedReader(new FileReader("encrypted.txt"));
        System.out.println("\ndecrypted:");
        while ((temp = file.read()) != -1) {
            temp = 255 - temp;
            if (temp >= 65 && temp <= 90)
                text = (char) (65 + (temp - 42) % 26);
            else text = (char) temp;
            System.out.print(text);
            save.print(text);
        }
        save.close();
    }

    private static void encrypt() throws IOException {

        char text;
        int temp;

        PrintWriter save = new PrintWriter("encrypted.txt");
        BufferedReader file = new BufferedReader(new FileReader("test.txt"));
        System.out.println("encrypted:");
        while ((temp = file.read()) != -1) {
            if (temp >= 65 && temp <= 90)
                temp = 65 + (temp - 62) % 26;
            text = (char) (255 - temp);
            System.out.print(text);
            save.print(text);
        }
        save.close();
    }
}
