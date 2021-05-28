package com.example.dsspotify.utils;

import android.service.autofill.FieldClassification;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TranscriptService {


    public static Map<Integer, String> pretraitement(String input){

        //Pattern pattern = Pattern.compile("[^\\W\\d](\\w|[-']{1,2}(?=\\w))*");
        //
       // Pattern pattern = Pattern.compile("\\w*");
        Pattern pattern = Pattern.compile("^[a-zA-Z]*");

        Map<Integer, String> mp = new HashMap<>();
        int c = 0;
        for(String split : input.split(" ")){
            if(pattern.matcher(split).matches()){

                mp.put(c,split.toLowerCase());
                Log.wtf("machess : "+mp.keySet().size(), split);
            }
            c++;
        }
        return mp;

    }


    public static Commande analyser(String input) {
        Map<Integer, String> mappre =  pretraitement(input);
        int c = 0;
        for (Map.Entry<Integer,String> entry : mappre.entrySet()) {
            Log.wtf("analyser", "key = " + entry.getKey() + ", split value = " + entry.getValue());
            if (AnalyseurSemantique.DictionnaireDesSynonymes().contains(entry.getValue())) {
                Log.wtf("gotch"," -> "+entry.getValue()+"->"+mappre.get(c + 1));
                if (mappre.get(entry.getKey() + 1) != null) {
                    return new Commande(entry.getValue(), mappre.get(entry.getKey() + 1));
                }
                c++;
            }
            Log.wtf("counter analyser"," - - > "+c);
        }
        return new Commande("nan","nan");
    }
}

