package com.wordpress.httpspandareaktor.quant;

import android.util.Log;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Created by brian on 3/20/17.
 */

public class RegexUtils {


    public static String cleanText(String input, boolean filterMonth, boolean filterDay, boolean filterCommon){
        //booleans are filters that take month, day, very common words like "a" and "the"
        //split input into array to clean using delimiter of unlimited whitespace to capture everything
        String[] dirtyWordArray = input.split("\\s+");
        StringBuilder cleanString = new StringBuilder();

        //use enhanced for loop to clean out non-alpha
        //include words with comma, period, exclaimation, apostrophe at start/fin, etc...
        for (String word : dirtyWordArray) {
            if (word.matches("[a-z[A-Z]]+") && !word.matches("null")){
                if (passesFilter(word.toLowerCase(), filterMonth, filterDay, filterCommon)) {
                    cleanString.append(word + " ");
                }


            } else if ((word.matches("[a-z[A-Z]]+\\!?") || word.matches("[a-zA-Z]+\\.?") || word.matches("[a-zA-Z]+\\??"))
                    && !word.matches("null")){
                if (passesFilter(word.toLowerCase(), filterMonth, filterDay, filterCommon)) {
                    cleanString.append(word.substring(0, word.length() - 1) + " ");
                }
            } else {
                cleanString.append("");
            }
        }
        Log.v("RegexUtils.cleanText", "cleanString is: " + cleanString);
        return cleanString.toString();

    }


    private static boolean passesFilter(String word, boolean filterMonth, boolean filterDay, boolean filterCommon){
        String[] monthStrings = new String[]{"january", "february", "march", "april", "may", "june", "july",
                "august", "september", "october", "november", "december"};

        String[] dayStrings = new String[]{"monday", "tuesday", "wednesday", "thursday", "friday"};

        String[] commonStrings = new String[]{"the", "be", "to", "of", "and", "a", "in", "that", "is",
                "have", "i", "it", "for", "not", "on", "with", "he", "she", "as", "you", "do", "at",
                "or", "an", "will", "their", "there", "by", "Comment", "comment", "comments", "date",
        "how", "from", "et", "more", "are", "your", "am", "pm", "site", "why", "where"};

        if (Arrays.asList(monthStrings).contains(word)){
            return false;
        }

        if (Arrays.asList(dayStrings).contains(word)){
            return false;
        }

        if (Arrays.asList(commonStrings).contains(word)){
            return false;
        }
        return true;

    }



}
