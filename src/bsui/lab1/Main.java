package bsui.lab1;


import java.io.*;

import static java.lang.Math.round;

import java.util.LinkedHashSet;

/**
 * @author Lukasz Broll 225972
 */
public class Main {

    public static void main(String[] args) {
        try {
            CaesarsEncryptionWithBitInversion.doTask();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //compress();
    }



    public static void compress() {
        StringBuilder result = new StringBuilder();
        String text, temp;
        LinkedHashSet<String> dict = new LinkedHashSet<>();
        double n = 0;
        int x = 0, inc = 0;
        try {
            BufferedReader file = new BufferedReader(new FileReader("test.txt"));
            while ((text = file.readLine()) != null) {
                for (int i = 0; i < text.length(); i++) {
                    dict.add(String.valueOf(text.charAt(i)));
                    inc++;
                }
            }
            x = dict.size();
            n = round(Math.log(x) / Math.log(2));
            // zapis do pliku
            PrintWriter sfile = new PrintWriter("compressed.txt");
            //sfile.print(Integer.toBinaryString(x));
            //sfile.print(Integer.toBinaryString((8-(3+text.length()*(int)n)%8)%8));
            sfile.close();
            // long t = round((8 - (3 + inc.length() * n) % 8 ) % 8);

            // System.out.println(p);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(dict.toString());
        //System.out.println(x);
        System.out.println(n);

    }
}
