package com.lxs;
import java.io.*;
import java.net.*;

public class Html2Doc {
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        URL oracle = new URL("https://docs.oracle.com/javase/tutorial/networking/urls/readingURL.html");
        BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

        String inputLine;
        while((inputLine = in.readLine()) != null) {
            System.out.println(inputLine);
        }
        in.close();
    }
}
