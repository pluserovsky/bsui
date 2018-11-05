package bsui.lab1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashSet;

import static java.lang.Math.round;

class CompressionAlgorithm {

    private static void compress() {

        LinkedHashSet<Character> dictionary = new LinkedHashSet<>();
        double n;
        int x, temp, incDict = 0, incFrame = 2;
        char text, buff;
        StringBuilder frame;
        try {
            // slownik
            BufferedReader file = new BufferedReader(new FileReader("resources/compression/test.txt"));
            while ((temp = file.read()) != -1) {
                text = (char) temp;
                dictionary.add(text);
                incDict++;
            }
            file.close();
            System.out.println("dictionary:" + dictionary.toString());
            x = dictionary.size();
            n = round(Math.log(x) / Math.log(2));
            // zapis do pliku slownika
            PrintWriter sfile = new PrintWriter("resources/compression/compressed.txt");
            sfile.print((char) x);
            for (Character aDictionary : dictionary) {
                text = aDictionary;
                sfile.print(text);
            }
            // zapis dopelnienia do pliku
            frame = new StringBuilder(Integer.toBinaryString((8 - (3 + incDict * (int) n) % 8) % 8));
            System.out.println("frame:" + frame);
            System.out.println("code:" + Integer.parseInt(frame.toString(), 2));
            // zapis tekstu
            file = new BufferedReader(new FileReader("resources/compression/test.txt"));

            while ((temp = file.read()) != -1) {

                incDict = 0;
                text = (char) (temp);
                for (Character aDictionary : dictionary) {
                    if (text == aDictionary) {
                        frame.append(generateBytes(incDict, n));
                        break;
                    }
                    incDict++;
                }
                incFrame += n;
                if (incFrame > 7) {
                    buff = frame.charAt(frame.length() - 1);
                    frame = new StringBuilder(frame.substring(0, frame.length() - 1));
                    System.out.print("(char)" + Integer.parseInt(frame.toString(), 2));
                    sfile.print((char) Integer.parseInt(frame.substring(0, 7), 2));
                    frame = new StringBuilder(String.valueOf(buff));
                    incFrame = 1;
                }


            }
            while (frame.length() != 8) {
                frame.append("1");
            }
            sfile.print((char) Integer.parseInt(frame.substring(0, 8), 2));
            System.out.print("(char)" + Integer.parseInt(frame.toString(), 2));

            sfile.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }

    private static String generateBytes(int i, double n) {
        StringBuilder bytes = new StringBuilder(Integer.toBinaryString(i));
        if (bytes.length() < n) {
            for (int j = bytes.length(); j < n; j++) {
                bytes.insert(0, "0");
            }
        }
        return bytes.toString();
    }


    static void doTask() {
        compress();
        //decompress();
    }

    private static void decompress() {
        LinkedHashSet<Character> dictionary = new LinkedHashSet<>();
        double n;
        int x=0, temp, incDict = 0, incFrame = 2, toFill;
        char text, buff = 0;
        String frame = "";
        try {
            // slownik
            BufferedReader file = new BufferedReader(new FileReader("resources/compression/compressed.txt"));
            if((temp = file.read()) != -1){
                x = temp;
            }
            for(int i =0; i<x;i++){
                dictionary.add((char) file.read());
            }
            n = round(Math.log(x) / Math.log(2));
            System.out.println("n:"+n);
            System.out.println("x:"+x);
            System.out.println("dict:"+dictionary.toString());
            //System.out.println(file.read());
            frame += Integer.toBinaryString(file.read());
            System.out.println("frame:" + frame);
            toFill = Integer.parseInt(frame.substring(0,3), 2);
            System.out.println("toFill:" + toFill);
            // zapis do pliku
            PrintWriter sfile = new PrintWriter("resources/compression/uncompressed.txt");
/*
            // zapis dopelnienia do pliku
            while ((temp = file.read()) != -1 ) {
                if(frame)
                incDict = 0;
                text = (char) (temp);
                for (Character aDictionary : dictionary) {
                    if (text == aDictionary) {
                        frame.append(generateBytes(incDict, n));

                    }
                    incDict++;
                }
                incFrame += 2;
                if (incFrame > 7) {
                    buff = frame.charAt(frame.length() - 1);
                    frame = new StringBuilder(frame.substring(0, frame.length() - 1));
                    System.out.print("(char)" + Integer.parseInt(frame.toString(), 2));
                    sfile.print((char) Integer.parseInt(frame.substring(0, 8), 2));
                    frame = new StringBuilder(String.valueOf(buff));
                    incFrame = 1;
                }


            }
            while (frame.length() != 8) {
                frame.append("1");
            }
            sfile.print((char) Integer.parseInt(frame.substring(0, 8), 2));
            System.out.print("(char)" + Integer.parseInt(frame.toString(), 2));

            sfile.close();*/

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
