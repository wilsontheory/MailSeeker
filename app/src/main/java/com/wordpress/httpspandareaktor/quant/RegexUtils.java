package com.wordpress.httpspandareaktor.quant;

import android.util.Log;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexUtils {


    public static String[] purify(String input, String searchTerm) {
        //for testing
        searchTerm = "";
        //reverse email dodging tactics
        String fixedInput = input.replaceAll("[\\s*\\[?\\(?]+[dD][oO][tT][\\)?\\]?\\s*]+", "\\.").replaceAll("[\\s*\\[?\\(?]+[aA][tT][\\)?\\]?\\s*]+", "@");

        //take out everything except the emails within the string, return as array
        //split input into array using delimiter of unlimited whitespace to capture everything
        String[] splitWordArray = fixedInput.split("\\s+");
        Log.v("RegexUtils.purify", "length of this array: " + splitWordArray.length);

        //HashSet will hold the emails for this purification
        HashSet<String> emailsFound = new HashSet<String>();

        for (String word : splitWordArray) {

            if (word != null) {
                //if the word fits the format of an email (e.g. john.doe@email.com), consider it for HashSet

                    //strip out only the text between potential HTML tags
                    String regex = "[^>=\"\\/@]+@\\w+\\.[\\w&&[^<\"]]+";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(word);
                    if (matcher.find() && word.toLowerCase().contains(searchTerm.toLowerCase())) {
                        //if the word contains searchTerm ("" by default), add it in
                        word = matcher.group();
                        word = word.replaceAll("mailto:", "");
                        emailsFound.add(word);
                        Log.v("RegexUtils.purify", " found an email: " + word);
                    }
            }
        }

        //turn the hash set into an array of strings
        return emailsFound.toArray(new String[emailsFound.size()]);

    }


    public static boolean urlDomainNameMatch(String urlA, String urlB) {
        //check if the host names of two urls match, regardless of www in front of them or not
        //this currently checks if the url pulled by Jsoup matches the base URL input by the user


        //build a url to make string for A, then B
        String hostA = "";
        try {
            URL builtUrlA = new URL(urlA);
            hostA = builtUrlA.getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String hostB = "";
        try {
            URL builtUrlB = new URL(urlB);
            hostB = builtUrlB.getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        //now extract substring from strings, domain name, to take care of www or not www in URLs
        String siteNameA = "";
        //search for the pattern I want, like "google.com" in http://www.google.com/maps using RegEx
        Pattern patternA = Pattern.compile("([^\\.]+)\\.(co.)?([^\\.]+)$");
        Matcher matcherA = patternA.matcher(hostA);
        if (matcherA.find()) {
            siteNameA = matcherA.group();
        }

        String siteNameB = "";
        //search for the pattern I want, like "google.com/" in http://www.google.com/maps
        Pattern patternB = Pattern.compile("([^\\.]+)\\.(co.)?([^\\.]+)$");
        Matcher matcherB = patternB.matcher(hostB);
        if (matcherB.find()) {
            siteNameB = matcherB.group();
        }

        return siteNameA.equals(siteNameB);

    }


}
